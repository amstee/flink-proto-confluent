package com.amstee.flink.formats.proto.registry.confluent.serialize;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.protobuf.ProtobufSchemaProvider;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.logical.RowType;

import java.util.Collections;
import java.util.Map;

public class ProtoRowDataSerializationSchema implements SerializationSchema<RowData> {
    private final RowType rowType;

    private final String schemaRegistryURL;

    private final Map<String, ?> registryConfigs;

    private final String topic;

    private final Boolean is_key;
    private transient CachedSchemaRegistryClient client;
    private transient RowDataProtoSerializer serializer;

    public ProtoRowDataSerializationSchema(RowType rowType, String schemaRegistryURL, String topic, Boolean is_key, Map<String, String> registryConfigs) {
        this.rowType = rowType;
        this.schemaRegistryURL = schemaRegistryURL;
        this.registryConfigs = registryConfigs;
        this.topic = topic;
        this.is_key = is_key;
    }

    @Override
    public void open(InitializationContext context) {
        if (this.client == null) {
            this.client = new CachedSchemaRegistryClient(schemaRegistryURL, 100, Collections.singletonList(new ProtobufSchemaProvider()), registryConfigs);
            this.serializer = new RowDataProtoSerializer(client);
            this.serializer.configure(registryConfigs, is_key);
        }
    }

    @Override
    public byte[] serialize(RowData element) {
        return this.serializer.serializeRowData(topic, rowType, element);
    }
}
