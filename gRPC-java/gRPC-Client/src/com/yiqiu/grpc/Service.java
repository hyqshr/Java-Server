// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: service.proto

package com.yiqiu.grpc;

public final class Service {
  private Service() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_yiqiu_grpc_putRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_yiqiu_grpc_putRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_yiqiu_grpc_putResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_yiqiu_grpc_putResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\rservice.proto\022\016com.yiqiu.grpc\"&\n\nputRe" +
      "quest\022\013\n\003key\030\001 \001(\t\022\013\n\003val\030\002 \001(\t\"\"\n\013putRe" +
      "sponse\022\023\n\013responseMsg\030\001 \001(\t2L\n\nputServic" +
      "e\022>\n\003put\022\032.com.yiqiu.grpc.putRequest\032\033.c" +
      "om.yiqiu.grpc.putResponseB\002P\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_com_yiqiu_grpc_putRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_yiqiu_grpc_putRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_yiqiu_grpc_putRequest_descriptor,
        new String[] { "Key", "Val", });
    internal_static_com_yiqiu_grpc_putResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_yiqiu_grpc_putResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_yiqiu_grpc_putResponse_descriptor,
        new String[] { "ResponseMsg", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
