package com.amstee.flink.formats.proto.registry.confluent.deserialize;

import com.google.protobuf.DynamicMessage;
import io.confluent.kafka.schemaregistry.protobuf.ProtobufSchemaProvider;
import io.confluent.kafka.schemaregistry.utils.BoundedConcurrentHashMap;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.logical.RowType;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.*;

public class ProtoRowDataDeserializationSchema implements DeserializationSchema<RowData> {
    private final TypeInformation<RowData> resultTypeInfo;
    private final RowType rowType;
    private final String schemaRegistryURL;
    private final Map<String, ?> registryConfigs;
    private final String topic;
    private final Boolean is_key;
    private transient CachedSchemaRegistryClient client;
    private transient KafkaProtobufDeserializer<DynamicMessage> deserializer;
    private transient Map<Integer, ProtoToRowDataConverters.ProtoToRowDataConverter> converters;


    public ProtoRowDataDeserializationSchema(RowType rowType, TypeInformation<RowData> resultTypeInfo, String url,  Map<String, String> registryConfigs, String topic, Boolean is_key) {
        this.rowType = rowType;
        this.schemaRegistryURL = url;
        this.registryConfigs = registryConfigs;
        this.topic = topic;
        this.is_key = is_key;
        this.resultTypeInfo = resultTypeInfo;
    }

    @Override
    public void open(InitializationContext context) {
        if (this.client == null) {
            this.client = new CachedSchemaRegistryClient(schemaRegistryURL, 100, Collections.singletonList(new ProtobufSchemaProvider()), registryConfigs);
            this.deserializer = new KafkaProtobufDeserializer<DynamicMessage>(client);
            this.deserializer.configure(registryConfigs, is_key);
            this.converters = new BoundedConcurrentHashMap<>(100);
        }
    }

    @Override
    public RowData deserialize(byte[] message) throws IOException {
        if (message == null) {
            return null;
        }
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(message));
        inputStream.readByte(); // skip magic byte
        int id = inputStream.readInt();
        DynamicMessage dynamicMessage = deserializer.deserialize(topic, message);

        ProtoToRowDataConverters.ProtoToRowDataConverter converter = Optional.ofNullable(
                converters.get(id)
        ).orElseGet(() -> {
            var res = ProtoToRowDataConverters.createConverter(dynamicMessage.getDescriptorForType(), rowType);

            converters.put(id, res);
            return res;
        });
        return (RowData) converter.convert(dynamicMessage);
    }

    @Override
    public boolean isEndOfStream(RowData nextElement) {
        return false;
    }

    @Override
    public TypeInformation<RowData> getProducedType() {
        return resultTypeInfo;
    }
}
