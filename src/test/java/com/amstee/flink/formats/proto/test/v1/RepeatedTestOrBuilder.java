// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: flink/formats/proto/test/v1/test_repeated.proto

// Protobuf Java Version: 3.25.3
package com.amstee.flink.formats.proto.test.v1;

public interface RepeatedTestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:amstee.flink.formats.proto.test.v1.RepeatedTest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated string values = 1 [json_name = "values"];</code>
   * @return A list containing the values.
   */
  java.util.List<java.lang.String>
      getValuesList();
  /**
   * <code>repeated string values = 1 [json_name = "values"];</code>
   * @return The count of values.
   */
  int getValuesCount();
  /**
   * <code>repeated string values = 1 [json_name = "values"];</code>
   * @param index The index of the element to return.
   * @return The values at the given index.
   */
  java.lang.String getValues(int index);
  /**
   * <code>repeated string values = 1 [json_name = "values"];</code>
   * @param index The index of the value to return.
   * @return The bytes of the values at the given index.
   */
  com.google.protobuf.ByteString
      getValuesBytes(int index);

  /**
   * <code>repeated int32 numbers = 2 [json_name = "numbers"];</code>
   * @return A list containing the numbers.
   */
  java.util.List<java.lang.Integer> getNumbersList();
  /**
   * <code>repeated int32 numbers = 2 [json_name = "numbers"];</code>
   * @return The count of numbers.
   */
  int getNumbersCount();
  /**
   * <code>repeated int32 numbers = 2 [json_name = "numbers"];</code>
   * @param index The index of the element to return.
   * @return The numbers at the given index.
   */
  int getNumbers(int index);

  /**
   * <code>repeated float floats = 3 [json_name = "floats"];</code>
   * @return A list containing the floats.
   */
  java.util.List<java.lang.Float> getFloatsList();
  /**
   * <code>repeated float floats = 3 [json_name = "floats"];</code>
   * @return The count of floats.
   */
  int getFloatsCount();
  /**
   * <code>repeated float floats = 3 [json_name = "floats"];</code>
   * @param index The index of the element to return.
   * @return The floats at the given index.
   */
  float getFloats(int index);

  /**
   * <code>repeated double doubles = 4 [json_name = "doubles"];</code>
   * @return A list containing the doubles.
   */
  java.util.List<java.lang.Double> getDoublesList();
  /**
   * <code>repeated double doubles = 4 [json_name = "doubles"];</code>
   * @return The count of doubles.
   */
  int getDoublesCount();
  /**
   * <code>repeated double doubles = 4 [json_name = "doubles"];</code>
   * @param index The index of the element to return.
   * @return The doubles at the given index.
   */
  double getDoubles(int index);

  /**
   * <code>repeated bool bools = 5 [json_name = "bools"];</code>
   * @return A list containing the bools.
   */
  java.util.List<java.lang.Boolean> getBoolsList();
  /**
   * <code>repeated bool bools = 5 [json_name = "bools"];</code>
   * @return The count of bools.
   */
  int getBoolsCount();
  /**
   * <code>repeated bool bools = 5 [json_name = "bools"];</code>
   * @param index The index of the element to return.
   * @return The bools at the given index.
   */
  boolean getBools(int index);

  /**
   * <code>repeated bytes bytes = 6 [json_name = "bytes"];</code>
   * @return A list containing the bytes.
   */
  java.util.List<com.google.protobuf.ByteString> getBytesList();
  /**
   * <code>repeated bytes bytes = 6 [json_name = "bytes"];</code>
   * @return The count of bytes.
   */
  int getBytesCount();
  /**
   * <code>repeated bytes bytes = 6 [json_name = "bytes"];</code>
   * @param index The index of the element to return.
   * @return The bytes at the given index.
   */
  com.google.protobuf.ByteString getBytes(int index);

  /**
   * <code>repeated .google.protobuf.StringValue string_values = 7 [json_name = "stringValues"];</code>
   */
  java.util.List<com.google.protobuf.StringValue> 
      getStringValuesList();
  /**
   * <code>repeated .google.protobuf.StringValue string_values = 7 [json_name = "stringValues"];</code>
   */
  com.google.protobuf.StringValue getStringValues(int index);
  /**
   * <code>repeated .google.protobuf.StringValue string_values = 7 [json_name = "stringValues"];</code>
   */
  int getStringValuesCount();
  /**
   * <code>repeated .google.protobuf.StringValue string_values = 7 [json_name = "stringValues"];</code>
   */
  java.util.List<? extends com.google.protobuf.StringValueOrBuilder> 
      getStringValuesOrBuilderList();
  /**
   * <code>repeated .google.protobuf.StringValue string_values = 7 [json_name = "stringValues"];</code>
   */
  com.google.protobuf.StringValueOrBuilder getStringValuesOrBuilder(
      int index);

  /**
   * <code>repeated .google.protobuf.Timestamp timestamps = 8 [json_name = "timestamps"];</code>
   */
  java.util.List<com.google.protobuf.Timestamp> 
      getTimestampsList();
  /**
   * <code>repeated .google.protobuf.Timestamp timestamps = 8 [json_name = "timestamps"];</code>
   */
  com.google.protobuf.Timestamp getTimestamps(int index);
  /**
   * <code>repeated .google.protobuf.Timestamp timestamps = 8 [json_name = "timestamps"];</code>
   */
  int getTimestampsCount();
  /**
   * <code>repeated .google.protobuf.Timestamp timestamps = 8 [json_name = "timestamps"];</code>
   */
  java.util.List<? extends com.google.protobuf.TimestampOrBuilder> 
      getTimestampsOrBuilderList();
  /**
   * <code>repeated .google.protobuf.Timestamp timestamps = 8 [json_name = "timestamps"];</code>
   */
  com.google.protobuf.TimestampOrBuilder getTimestampsOrBuilder(
      int index);

  /**
   * <code>repeated .amstee.flink.formats.proto.test.v1.NestedRepeated nested_repeated = 9 [json_name = "nestedRepeated"];</code>
   */
  java.util.List<com.amstee.flink.formats.proto.test.v1.NestedRepeated> 
      getNestedRepeatedList();
  /**
   * <code>repeated .amstee.flink.formats.proto.test.v1.NestedRepeated nested_repeated = 9 [json_name = "nestedRepeated"];</code>
   */
  com.amstee.flink.formats.proto.test.v1.NestedRepeated getNestedRepeated(int index);
  /**
   * <code>repeated .amstee.flink.formats.proto.test.v1.NestedRepeated nested_repeated = 9 [json_name = "nestedRepeated"];</code>
   */
  int getNestedRepeatedCount();
  /**
   * <code>repeated .amstee.flink.formats.proto.test.v1.NestedRepeated nested_repeated = 9 [json_name = "nestedRepeated"];</code>
   */
  java.util.List<? extends com.amstee.flink.formats.proto.test.v1.NestedRepeatedOrBuilder> 
      getNestedRepeatedOrBuilderList();
  /**
   * <code>repeated .amstee.flink.formats.proto.test.v1.NestedRepeated nested_repeated = 9 [json_name = "nestedRepeated"];</code>
   */
  com.amstee.flink.formats.proto.test.v1.NestedRepeatedOrBuilder getNestedRepeatedOrBuilder(
      int index);
}
