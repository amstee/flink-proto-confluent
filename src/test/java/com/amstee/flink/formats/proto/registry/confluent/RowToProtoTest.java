package com.amstee.flink.formats.proto.registry.confluent;

import com.amstee.flink.formats.proto.registry.confluent.serialize.ProtoRowDataSerializationSchema;
import com.amstee.flink.formats.proto.test.v1.*;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.Message;
import com.amstee.flink.formats.proto.registry.confluent.util.ProtoToLogicalType;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.protobuf.ProtobufSchemaProvider;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
import org.apache.flink.table.data.GenericRowData;
import org.apache.flink.table.data.StringData;
import org.apache.flink.table.types.logical.RowType;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.util.Collections;
import java.util.Map;

@Testcontainers
public class RowToProtoTest {

    @Container
    public static DockerComposeContainer environment =
            new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"))
                    .withExposedService("karapace-registry", 8081, Wait.forHttp("/_health").forStatusCode(200));

    final String url = "http://" + environment.getServiceHost("karapace-registry", 8081) + ":" + environment.getServicePort("karapace-registry", 8081);

    final Map<String, String> config = Map.ofEntries(
            Map.entry("schema.registry.url", url),
            Map.entry("auto.register.schemas", "true")
    );

    final CachedSchemaRegistryClient schemaRegistryClient = new CachedSchemaRegistryClient(url, 100, Collections.singletonList(new ProtobufSchemaProvider()), config);

    final KafkaProtobufDeserializer<DynamicMessage> deserializer = new KafkaProtobufDeserializer<DynamicMessage>(schemaRegistryClient, config);

    private <T extends Message> byte[] serializeMessage(T message, String topic) throws Exception {
        var serializer = new KafkaProtobufSerializer<T>(schemaRegistryClient);
        serializer.configure(config, false);
        byte[] protoBytes = null;

        for (var i = 0; i < 10; i++) {
            try {
                protoBytes = serializer.serialize(topic, message);
                break;
            } catch (Exception e) {
                Thread.sleep(1000);
            }
        }
        if (protoBytes == null) {
            throw new Exception("failed to serialize");
        }
        return protoBytes;
    }

    @Test
    public void testRowToProtoWithSchemaRegistered() throws Exception {
        var serializer = new ProtoRowDataSerializationSchema((RowType) ProtoToLogicalType.toLogicalType(SimpleMessage.getDescriptor()), url, "simple_test", false, config);
        serializer.open(null);

        // Register schema
        serializeMessage(SimpleMessage.newBuilder().build(), "simple_test");

        GenericRowData row = new GenericRowData(2);
        row.setField(0, StringData.fromString("field1"));
        row.setField(1, StringData.fromString("field2"));

        byte[] serialized = serializer.serialize(row);
        DynamicMessage message = deserializer.deserialize("simple_test", serialized);
        assert message.getField(message.getDescriptorForType().findFieldByName("content")).equals("field1");
        assert message.getField(message.getDescriptorForType().findFieldByName("date_time")).equals("field2");
    }

    @Test
    public void testRowToProtoNoSchemaRegistered() throws Exception {
        var serializer = new ProtoRowDataSerializationSchema((RowType) ProtoToLogicalType.toLogicalType(SimpleMessage.getDescriptor()), url, "simple_test_not_registered", false, config);
        serializer.open(null);

        // Register schema
        serializeMessage(SimpleMessage.newBuilder().build(), "simple_test_not_registered");

        GenericRowData row = new GenericRowData(2);
        row.setField(0, StringData.fromString("field1"));
        row.setField(1, StringData.fromString("field2"));

        byte[] serialized = serializer.serialize(row);
        DynamicMessage message = deserializer.deserialize("simple_test_not_registered", serialized);
        assert message.getField(message.getDescriptorForType().findFieldByName("content")).equals("field1");
        assert message.getField(message.getDescriptorForType().findFieldByName("date_time")).equals("field2");
    }
}
