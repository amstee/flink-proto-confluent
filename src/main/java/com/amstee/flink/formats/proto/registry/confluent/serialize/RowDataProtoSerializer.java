package com.amstee.flink.formats.proto.registry.confluent.serialize;

import com.amstee.flink.formats.proto.registry.confluent.util.RowTypeToProto;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import io.confluent.kafka.schemaregistry.ParsedSchema;
import io.confluent.kafka.schemaregistry.client.SchemaMetadata;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import io.confluent.kafka.schemaregistry.protobuf.ProtobufSchema;
import io.confluent.kafka.schemaregistry.utils.BoundedConcurrentHashMap;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
import org.apache.flink.table.api.ValidationException;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.logical.RowType;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class RowDataProtoSerializer extends KafkaProtobufSerializer<DynamicMessage> {

    // TODO are we ever gonna have more than one converter?
    private final Map<String, RowDataToProtoConverters.RowDataToProtoConverter> converters;

    RowDataProtoSerializer(SchemaRegistryClient client) {
        super(client);
        this.converters = new BoundedConcurrentHashMap<>(100);
    }

    public byte[] serializeRowData(String topic, RowType rowType, RowData row) {
        Descriptors.Descriptor desc = null;
        String subject = getSubjectName(topic, isKey, null, null);

        try {
            // TODO caching, or find protected method that caches for us
            SchemaMetadata schemaMetadata = schemaRegistry.getLatestSchemaMetadata(subject);
            Optional<ParsedSchema> optSchema =
                    schemaRegistry.parseSchema(
                            new io.confluent.kafka.schemaregistry.client.rest.entities.Schema(
                                    null, schemaMetadata));
            ParsedSchema latestVersion = optSchema.orElseThrow(
                    () -> new IOException("Invalid schema " + schemaMetadata.getSchema()
                            + " with refs " + schemaMetadata.getReferences()
                            + " of type " + schemaMetadata.getSchemaType()));
            ProtobufSchema schema = (ProtobufSchema) latestVersion;
            desc = schema.toDescriptor();
        } catch (IOException | RestClientException e) {
            desc = RowTypeToProto.fromRowType(rowType, "Row", "com.amstee.flink.schema");
        }
        if (desc == null) {
            throw new ValidationException("Could not find schema for subject: " + subject);
        }

        Descriptors.Descriptor finalDesc = desc;
        RowDataToProtoConverters.RowDataToProtoConverter converter = Optional.ofNullable(this.converters.get(finalDesc.getFullName())).orElseGet(() -> {
            RowDataToProtoConverters.RowDataToProtoConverter newConverter = RowDataToProtoConverters.createConverter(rowType, finalDesc);
            this.converters.put(finalDesc.getFullName(), newConverter);
            return newConverter;
        });
        return serialize(topic, (DynamicMessage) converter.convert(row));
    }
}
