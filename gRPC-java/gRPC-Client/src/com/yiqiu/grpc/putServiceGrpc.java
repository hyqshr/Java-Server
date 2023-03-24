package com.yiqiu.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: service.proto")
public final class putServiceGrpc {

  private putServiceGrpc() {}

  public static final String SERVICE_NAME = "com.yiqiu.grpc.putService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<putRequest,
          putResponse> getPutMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "put",
      requestType = putRequest.class,
      responseType = putResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<putRequest,
          putResponse> getPutMethod() {
    io.grpc.MethodDescriptor<putRequest, putResponse> getPutMethod;
    if ((getPutMethod = putServiceGrpc.getPutMethod) == null) {
      synchronized (putServiceGrpc.class) {
        if ((getPutMethod = putServiceGrpc.getPutMethod) == null) {
          putServiceGrpc.getPutMethod = getPutMethod = 
              io.grpc.MethodDescriptor.<putRequest, putResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.yiqiu.grpc.putService", "put"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  putRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  putResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new putServiceMethodDescriptorSupplier("put"))
                  .build();
          }
        }
     }
     return getPutMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static putServiceStub newStub(io.grpc.Channel channel) {
    return new putServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static putServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new putServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static putServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new putServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class putServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void put(putRequest request,
                    io.grpc.stub.StreamObserver<putResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPutMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPutMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                      putRequest,
                      putResponse>(
                  this, METHODID_PUT)))
          .build();
    }
  }

  /**
   */
  public static final class putServiceStub extends io.grpc.stub.AbstractStub<putServiceStub> {
    private putServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private putServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected putServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new putServiceStub(channel, callOptions);
    }

    /**
     */
    public void put(putRequest request,
                    io.grpc.stub.StreamObserver<putResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPutMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class putServiceBlockingStub extends io.grpc.stub.AbstractStub<putServiceBlockingStub> {
    private putServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private putServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected putServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new putServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public putResponse put(putRequest request) {
      return blockingUnaryCall(
          getChannel(), getPutMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class putServiceFutureStub extends io.grpc.stub.AbstractStub<putServiceFutureStub> {
    private putServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private putServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected putServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new putServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<putResponse> put(
        putRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPutMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PUT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final putServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(putServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PUT:
          serviceImpl.put((putRequest) request,
              (io.grpc.stub.StreamObserver<putResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class putServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    putServiceBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Service.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("putService");
    }
  }

  private static final class putServiceFileDescriptorSupplier
      extends putServiceBaseDescriptorSupplier {
    putServiceFileDescriptorSupplier() {}
  }

  private static final class putServiceMethodDescriptorSupplier
      extends putServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    putServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (putServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new putServiceFileDescriptorSupplier())
              .addMethod(getPutMethod())
              .build();
        }
      }
    }
    return result;
  }
}
