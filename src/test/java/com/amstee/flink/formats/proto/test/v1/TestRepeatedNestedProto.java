// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: flink/formats/proto/test/v1/test_repeated_nested.proto

// Protobuf Java Version: 3.25.3
package com.amstee.flink.formats.proto.test.v1;

public final class TestRepeatedNestedProto {
  private TestRepeatedNestedProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_amstee_flink_formats_proto_test_v1_RepeatedMessageTest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_amstee_flink_formats_proto_test_v1_RepeatedMessageTest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_amstee_flink_formats_proto_test_v1_RepeatedMessageTest_InnerMessageTest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_amstee_flink_formats_proto_test_v1_RepeatedMessageTest_InnerMessageTest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n6flink/formats/proto/test/v1/test_repea" +
      "ted_nested.proto\022\"amstee.flink.formats.p" +
      "roto.test.v1\"\263\001\n\023RepeatedMessageTest\022V\n\001" +
      "d\030\004 \001(\0132H.amstee.flink.formats.proto.tes" +
      "t.v1.RepeatedMessageTest.InnerMessageTes" +
      "tR\001d\032D\n\020InnerMessageTest\022\021\n\001a\030\001 \001(\005H\000R\001a" +
      "\210\001\001\022\021\n\001b\030\002 \001(\003H\001R\001b\210\001\001B\004\n\002_aB\004\n\002_bB\360\001\n&c" +
      "om.amstee.flink.formats.proto.test.v1B\027T" +
      "estRepeatedNestedProtoP\001\242\002\005AFFPT\252\002\"Amste" +
      "e.Flink.Formats.Proto.Test.V1\312\002\"Amstee\\F" +
      "link\\Formats\\Proto\\Test\\V1\342\002.Amstee\\Flin" +
      "k\\Formats\\Proto\\Test\\V1\\GPBMetadata\352\002\'Am" +
      "stee::Flink::Formats::Proto::Test::V1b\006p" +
      "roto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_amstee_flink_formats_proto_test_v1_RepeatedMessageTest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_amstee_flink_formats_proto_test_v1_RepeatedMessageTest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_amstee_flink_formats_proto_test_v1_RepeatedMessageTest_descriptor,
        new java.lang.String[] { "D", });
    internal_static_amstee_flink_formats_proto_test_v1_RepeatedMessageTest_InnerMessageTest_descriptor =
      internal_static_amstee_flink_formats_proto_test_v1_RepeatedMessageTest_descriptor.getNestedTypes().get(0);
    internal_static_amstee_flink_formats_proto_test_v1_RepeatedMessageTest_InnerMessageTest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_amstee_flink_formats_proto_test_v1_RepeatedMessageTest_InnerMessageTest_descriptor,
        new java.lang.String[] { "A", "B", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}