package com.amstee.flink.formats.proto.registry.confluent;

import com.amstee.flink.formats.proto.registry.confluent.deserialize.ProtoRowDataDeserializationSchema;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.table.connector.ChangelogMode;
import org.apache.flink.table.connector.format.DecodingFormat;
import org.apache.flink.table.connector.source.DynamicTableSource;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.types.logical.RowType;

import java.util.Map;

public class ProtoDecodingFormat implements DecodingFormat<DeserializationSchema<RowData>> {

    private final String schemaRegistryURL;

    private final Map<String, String> registryConfigs;
    private final String topic;
    private final Boolean is_key;

    public ProtoDecodingFormat(String schemaRegistryURL, String topic, Boolean is_key, Map<String, String> registryConfigs) {
        this.topic = topic;
        this.is_key = is_key;
        this.registryConfigs = registryConfigs;
        this.schemaRegistryURL = schemaRegistryURL;
    }

    @Override
    public DeserializationSchema<RowData> createRuntimeDecoder(DynamicTableSource.Context context, DataType producedDataType) {
        final RowType rowType = (RowType) producedDataType.getLogicalType();
        final TypeInformation<RowData> rowDataTypeInfo =
                context.createTypeInformation(producedDataType);

        return new ProtoRowDataDeserializationSchema(rowType, rowDataTypeInfo, schemaRegistryURL, registryConfigs, topic, is_key);
    }

    @Override
    public ChangelogMode getChangelogMode() {
        return ChangelogMode.insertOnly();
    }
}
