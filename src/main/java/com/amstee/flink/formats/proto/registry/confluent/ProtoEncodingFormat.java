package com.amstee.flink.formats.proto.registry.confluent;

import com.amstee.flink.formats.proto.registry.confluent.serialize.ProtoRowDataSerializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.table.connector.ChangelogMode;
import org.apache.flink.table.connector.format.EncodingFormat;
import org.apache.flink.table.connector.sink.DynamicTableSink;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.types.logical.RowType;

import java.util.Map;

public class ProtoEncodingFormat implements EncodingFormat<SerializationSchema<RowData>> {
    private final String schemaRegistryURL;

    private final Map<String, String> registryConfigs;
    private final String topic;
    private final Boolean is_key;

    public ProtoEncodingFormat(String schemaRegistryURL, String topic, Boolean is_key, Map<String, String> registryConfigs) {
        this.topic = topic;
        this.is_key = is_key;
        this.registryConfigs = registryConfigs;
        this.schemaRegistryURL = schemaRegistryURL;
    }

    @Override
    public SerializationSchema<RowData> createRuntimeEncoder(DynamicTableSink.Context context, DataType physicalDataType) {
        final RowType rowType = (RowType) physicalDataType.getLogicalType();

        return new ProtoRowDataSerializationSchema(rowType, schemaRegistryURL, topic, is_key, registryConfigs);
    }

    @Override
    public ChangelogMode getChangelogMode() {
        return ChangelogMode.insertOnly();
    }
}
