// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: flink/formats/proto/test/v1/test_enums.proto

// Protobuf Java Version: 3.25.3
package com.amstee.flink.formats.proto.test.v1;

public final class TestEnumsProto {
  private TestEnumsProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_amstee_flink_formats_proto_test_v1_TestEnum_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_amstee_flink_formats_proto_test_v1_TestEnum_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_amstee_flink_formats_proto_test_v1_TestEnum_StatusMapEntry_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_amstee_flink_formats_proto_test_v1_TestEnum_StatusMapEntry_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n,flink/formats/proto/test/v1/test_enums" +
      ".proto\022\"amstee.flink.formats.proto.test." +
      "v1\"\346\002\n\010TestEnum\022C\n\004type\030\001 \001(\0162/.amstee.f" +
      "link.formats.proto.test.v1.MessageTypeR\004" +
      "type\022I\n\006status\030\002 \003(\01621.amstee.flink.form" +
      "ats.proto.test.v1.MessageStatusR\006status\022" +
      "Y\n\tstatusMap\030\003 \003(\0132;.amstee.flink.format" +
      "s.proto.test.v1.TestEnum.StatusMapEntryR" +
      "\tstatusMap\032o\n\016StatusMapEntry\022\020\n\003key\030\001 \001(" +
      "\tR\003key\022G\n\005value\030\002 \001(\01621.amstee.flink.for" +
      "mats.proto.test.v1.MessageStatusR\005value:" +
      "\0028\001*-\n\013MessageType\022\010\n\004TEXT\020\000\022\t\n\005IMAGE\020\001\022" +
      "\t\n\005VIDEO\020\002*1\n\rMessageStatus\022\010\n\004SENT\020\000\022\014\n" +
      "\010RECEIVED\020\001\022\010\n\004READ\020\002B\347\001\n&com.amstee.fli" +
      "nk.formats.proto.test.v1B\016TestEnumsProto" +
      "P\001\242\002\005AFFPT\252\002\"Amstee.Flink.Formats.Proto." +
      "Test.V1\312\002\"Amstee\\Flink\\Formats\\Proto\\Tes" +
      "t\\V1\342\002.Amstee\\Flink\\Formats\\Proto\\Test\\V" +
      "1\\GPBMetadata\352\002\'Amstee::Flink::Formats::" +
      "Proto::Test::V1b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_amstee_flink_formats_proto_test_v1_TestEnum_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_amstee_flink_formats_proto_test_v1_TestEnum_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_amstee_flink_formats_proto_test_v1_TestEnum_descriptor,
        new java.lang.String[] { "Type", "Status", "StatusMap", });
    internal_static_amstee_flink_formats_proto_test_v1_TestEnum_StatusMapEntry_descriptor =
      internal_static_amstee_flink_formats_proto_test_v1_TestEnum_descriptor.getNestedTypes().get(0);
    internal_static_amstee_flink_formats_proto_test_v1_TestEnum_StatusMapEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_amstee_flink_formats_proto_test_v1_TestEnum_StatusMapEntry_descriptor,
        new java.lang.String[] { "Key", "Value", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
