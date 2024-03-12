package com.amstee.flink.formats.proto.registry.confluent.util;

/**
 * Common constants used for converting Protobuf schema. Those are Confluent Schema Registry
 * specific.
 */
class CommonConstants {

    static final String CONNECT_TYPE_PROP = "connect.type";
    static final String CONNECT_TYPE_INT8 = "int8";
    static final String CONNECT_TYPE_INT16 = "int16";
    static final String PROTOBUF_DECIMAL_TYPE = "confluent.type.Decimal";
    static final String PROTOBUF_DECIMAL_LOCATION = "confluent/type/decimal.proto";
    static final String PROTOBUF_DATE_TYPE = "google.type.Date";
    static final String PROTOBUF_DATE_LOCATION = "google/type/date.proto";
    static final String PROTOBUF_TIME_TYPE = "google.type.TimeOfDay";
    static final String PROTOBUF_TIME_LOCATION = "google/type/timeofday.proto";
    static final String PROTOBUF_PRECISION_PROP = "precision";
    static final String PROTOBUF_SCALE_PROP = "scale";
    static final String PROTOBUF_TIMESTAMP_TYPE = "google.protobuf.Timestamp";
    static final String PROTOBUF_TIMESTAMP_LOCATION = "google/protobuf/timestamp.proto";
    static final String MAP_ENTRY_SUFFIX = "Entry"; // Suffix used by protoc
    static final String KEY_FIELD = "key";
    static final String VALUE_FIELD = "value";

    static final String PROTOBUF_DOUBLE_WRAPPER_TYPE = "google.protobuf.DoubleValue";
    static final String PROTOBUF_FLOAT_WRAPPER_TYPE = "google.protobuf.FloatValue";
    static final String PROTOBUF_INT64_WRAPPER_TYPE = "google.protobuf.Int64Value";
    static final String PROTOBUF_UINT64_WRAPPER_TYPE = "google.protobuf.UInt64Value";
    static final String PROTOBUF_INT32_WRAPPER_TYPE = "google.protobuf.Int32Value";
    static final String PROTOBUF_UINT32_WRAPPER_TYPE = "google.protobuf.UInt32Value";
    static final String PROTOBUF_BOOL_WRAPPER_TYPE = "google.protobuf.BoolValue";
    static final String PROTOBUF_STRING_WRAPPER_TYPE = "google.protobuf.StringValue";
    static final String PROTOBUF_BYTES_WRAPPER_TYPE = "google.protobuf.BytesValue";

    private CommonConstants() {}
}
