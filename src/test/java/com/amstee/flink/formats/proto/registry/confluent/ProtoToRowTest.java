package com.amstee.flink.formats.proto.registry.confluent;

import com.amstee.flink.formats.proto.registry.confluent.deserialize.ProtoRowDataDeserializationSchema;
import com.amstee.flink.formats.proto.test.v1.*;
import com.google.protobuf.*;
import com.amstee.flink.formats.proto.registry.confluent.util.ProtoToLogicalType;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.protobuf.ProtobufSchemaProvider;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
import org.apache.flink.table.data.ArrayData;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.data.StringData;
import org.apache.flink.table.types.logical.RowType;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.util.*;

@Testcontainers
public class ProtoToRowTest {

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
    public void testProtoToRow() throws Exception {
        var deserializer = new ProtoRowDataDeserializationSchema((RowType) ProtoToLogicalType.toLogicalType(SimpleMessage.getDescriptor()), null, url, config,"simple", false);
        deserializer.open(null);

        byte[] protoBytes = serializeMessage(SimpleMessage.newBuilder().setContent("test").setDateTime("date").build(), "simple");

        RowData data = deserializer.deserialize(protoBytes);
        StringData field1 = data.getString(0);
        StringData field2 = data.getString(1);

        assert Objects.equals(field1.toString(), "test");
        assert Objects.equals(field2.toString(), "date");
    }

    @Test
    public void testBigProtoToRow() throws Exception {
        var deserializer = new ProtoRowDataDeserializationSchema((RowType) ProtoToLogicalType.toLogicalType(BigPbMessage.getDescriptor()), null, url, config, "big_proto", false);
        deserializer.open(null);

        byte[] protoBytes = serializeMessage(
                BigPbMessage.newBuilder()
                    .setIntField1(1)
                    .setBoolField2(true)
                    .setStringField3("test")
                    .setBytesField4(ByteString.copyFromUtf8("4"))
                    .setDoubleField5(20)
                    .setFloatField6(21)
                    .setUint32Field7(40)
                    .setInt64Field8(60)
                    .setUint64Field9(80)
                    .setBytesField10(ByteString.copyFromUtf8("10"))
                    .setDoubleField11(11)
                    .setBytesField12(ByteString.copyFromUtf8("12"))
                    .setBoolField13(false)
                    .setStringField14("14string")
                    .setFloatField15(15)
                    .setInt32Field16(16)
                    .setBytesField17(ByteString.copyFromUtf8("17"))
                    .build(), "big_proto"
        );

        RowData data = deserializer.deserialize(protoBytes);
        assert data.getInt(0) == 1;
        assert data.getBoolean(1);
        assert Objects.equals(data.getString(2).toString(), "test");
        assert Arrays.equals(data.getBinary(3), "4".getBytes());
        assert data.getDouble(4) == 20;
        assert data.getFloat(5) == 21;
        assert data.getLong(6) == 40;
        assert data.getLong(7) == 60;
        assert data.getLong(8) == 80;
        assert Arrays.equals(data.getBinary(9), "10".getBytes());
        assert data.getDouble(10) == 11;
        assert Arrays.equals(data.getBinary(11), "12".getBytes());
        assert !data.getBoolean(12);
        assert Objects.equals(data.getString(13).toString(), "14string");
        assert data.getFloat(14) == 15;
        assert data.getInt(15) == 16;
        assert Arrays.equals(data.getBinary(16), "17".getBytes());
    }

    @Test
    public void testWrapperTypes() throws Exception {
        var topic = "wrapper";
        var deserializer = new ProtoRowDataDeserializationSchema((RowType) ProtoToLogicalType.toLogicalType(WrapperTypesTest.getDescriptor()), null, url, config, topic, false);
        deserializer.open(null);

        byte[] protoBytes = serializeMessage(
                WrapperTypesTest.newBuilder()
                        .setInt32Value(Int32Value.newBuilder().setValue(1).build())
                        .setInt64Value(Int64Value.newBuilder().setValue(2).build())
                        .setUint32Value(UInt32Value.newBuilder().setValue(3).build())
                        .setUint64Value(UInt64Value.newBuilder().setValue(4).build())
                        .setFloatValue(FloatValue.newBuilder().setValue(5).build())
                        .setDoubleValue(DoubleValue.newBuilder().setValue(6).build())
                        .setBoolValue(BoolValue.newBuilder().setValue(true).build())
                        .setStringValue(StringValue.newBuilder().setValue("test").build())
                        .setBytesValue(BytesValue.newBuilder().setValue(ByteString.copyFromUtf8("bytes")).build())
                    .build(), topic
        );

        RowData data = deserializer.deserialize(protoBytes);
        assert data.getInt(0) == 1;
        assert data.getLong(1) == 2;
        assert data.getLong(2) == 3;
        assert data.getLong(3) == 4;
        assert data.getFloat(4) == 5;
        assert data.getDouble(5) == 6;
        assert data.getBoolean(6);
        assert Objects.equals(data.getString(7).toString(), "test");
        assert Arrays.equals(data.getBinary(8), "bytes".getBytes());
    }

    @Test
    public void testMap() throws Exception {
        var topic = "map";
        var deserializer = new ProtoRowDataDeserializationSchema((RowType) ProtoToLogicalType.toLogicalType(MapTest.getDescriptor()), null, url, config, topic, false);
        deserializer.open(null);

        byte[] protoBytes = serializeMessage(
                MapTest.newBuilder()
                        .putAllSimpleStringMap(Map.of("key1", "value1"))
                        .putAllNestedMap(Map.of("key1", NestedMap.newBuilder().setId("bla").build()))
                        .build(), topic
        );

        RowData data = deserializer.deserialize(protoBytes);
        ArrayData keys = data.getMap(0).keyArray();
        ArrayData values = data.getMap(0).valueArray();

        assert Objects.equals(keys.getString(0).toString(), "key1");
        assert Objects.equals(values.getString(0).toString(), "value1");

        ArrayData keysNested = data.getMap(1).keyArray();
        ArrayData valuesNested = data.getMap(1).valueArray();

        assert Objects.equals(keysNested.getString(0).toString(), "key1");
        assert Objects.equals(valuesNested.getRow(0, 1).getString(0).toString(), "bla");
    }

    @Test
    public void testRepeated() throws Exception {
        var topic = "repeated";
        var deserializer = new ProtoRowDataDeserializationSchema(
                (RowType) ProtoToLogicalType.toLogicalType(RepeatedTest.getDescriptor()), null, url, config, topic, false
        );
        deserializer.open(null);

        byte[] protoBytes = serializeMessage(
                RepeatedTest.newBuilder()
                        .addAllValues(List.of("1", "2", "3"))
                        .addAllNumbers(List.of(1, 2, 3))
                        .addAllFloats(List.of(1f, 2f, 3f))
                        .addAllDoubles(List.of(1d, 2d, 3d))
                        .addAllBools(List.of(true, false, true))
                        .addAllBytes(List.of(ByteString.copyFromUtf8("1"), ByteString.copyFromUtf8("2"), ByteString.copyFromUtf8("3")))
                        .addAllStringValues(List.of(StringValue.newBuilder().setValue("1").build(), StringValue.newBuilder().setValue("2").build(), StringValue.newBuilder().setValue("3").build()))
                        .addAllTimestamps(List.of(Timestamp.newBuilder().setSeconds(1).setNanos(1).build(), Timestamp.newBuilder().setSeconds(2).setNanos(2).build(), Timestamp.newBuilder().setSeconds(3).setNanos(3).build()))
                        .addAllNestedRepeated(List.of(NestedRepeated.newBuilder().setValue("test").build(), NestedRepeated.newBuilder().setValue("test2").build()))
                        .build(), topic
        );

        RowData data = deserializer.deserialize(protoBytes);
        ArrayData values = data.getArray(0);
        assert Objects.equals(values.getString(0).toString(), "1");
        assert Objects.equals(values.getString(1).toString(), "2");
        assert Objects.equals(values.getString(2).toString(), "3");

        ArrayData numbers = data.getArray(1);
        assert numbers.getInt(0) == 1;
        assert numbers.getInt(1) == 2;
        assert numbers.getInt(2) == 3;

        ArrayData floats = data.getArray(2);
        assert floats.getFloat(0) == 1f;
        assert floats.getFloat(1) == 2f;
        assert floats.getFloat(2) == 3f;

        ArrayData doubles = data.getArray(3);
        assert doubles.getDouble(0) == 1d;
        assert doubles.getDouble(1) == 2d;
        assert doubles.getDouble(2) == 3d;

        ArrayData bools = data.getArray(4);
        assert bools.getBoolean(0);
        assert !bools.getBoolean(1);
        assert bools.getBoolean(2);

        ArrayData bytes = data.getArray(5);
        assert Arrays.equals(bytes.getBinary(0), "1".getBytes());
        assert Arrays.equals(bytes.getBinary(1), "2".getBytes());
        assert Arrays.equals(bytes.getBinary(2), "3".getBytes());

        ArrayData stringValues = data.getArray(6);
        assert Objects.equals(stringValues.getString(0).toString(), "1");
        assert Objects.equals(stringValues.getString(1).toString(), "2");
        assert Objects.equals(stringValues.getString(2).toString(), "3");

        ArrayData timestamps = data.getArray(7);
        assert timestamps.getTimestamp(0, 0).getMillisecond() == 1000;
        assert timestamps.getTimestamp(1, 0).getMillisecond() == 2000;
        assert timestamps.getTimestamp(2, 0).getMillisecond() == 3000;

        ArrayData nestedRepeated = data.getArray(8);
        assert Objects.equals(nestedRepeated.getRow(0, 1).getString(0).toString(), "test");
        assert Objects.equals(nestedRepeated.getRow(1, 1).getString(0).toString(), "test2");
    }

    @Test
    public void testRepeatedNested() throws Exception {
        var topic = "nested-repeated";
        var deserializer = new ProtoRowDataDeserializationSchema((RowType) ProtoToLogicalType.toLogicalType(RepeatedMessageTest.getDescriptor()), null, url, config, topic, false);
        deserializer.open(null);

        byte[] protoBytes = serializeMessage(
                RepeatedMessageTest.newBuilder()
                        .setD(RepeatedMessageTest.InnerMessageTest.newBuilder().setA(1).setB(2).build())
                        .build(), topic
        );

        RowData data = deserializer.deserialize(protoBytes);
        RowData innerMessage = data.getRow(0, 2);
        assert innerMessage.getInt(0) == 1;
        assert innerMessage.getLong(1) == 2;
    }

    @Test
    public void testEnums() throws Exception {
        var topic = "enums";
        var deserializer = new ProtoRowDataDeserializationSchema((RowType) ProtoToLogicalType.toLogicalType(TestEnum.getDescriptor()), null, url, config, topic, false);
        deserializer.open(null);

        byte[] protoBytes = serializeMessage(
                TestEnum.newBuilder()
                        .setType(MessageType.TEXT)
                        .addAllStatus(List.of(MessageStatus.READ, MessageStatus.RECEIVED))
                        .putStatusMap("key", MessageStatus.READ)
                        .build(), topic
        );

        RowData data = deserializer.deserialize(protoBytes);
        assert data.getString(0).toString().equals("TEXT");
        ArrayData status = data.getArray(1);
        assert status.getString(0).toString().equals("READ");
        assert status.getString(1).toString().equals("RECEIVED");
        ArrayData statusMapKeys = data.getMap(2).keyArray();
        ArrayData statusMapValues = data.getMap(2).valueArray();
        assert statusMapKeys.getString(0).toString().equals("key");
        assert statusMapValues.getString(0).toString().equals("READ");
    }
}
