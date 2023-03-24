package Service;

import com.yiqiu.grpc.putRequest;
import com.yiqiu.grpc.putResponse;
import com.yiqiu.grpc.putServiceGrpc;
import io.grpc.stub.StreamObserver;

public class putService extends putServiceGrpc.putServiceImplBase {
    @Override
    public void put(putRequest request, StreamObserver<putResponse> responseObserver) {
        System.out.println("Put service");
        System.out.println(request);

        putResponse.Builder response = putResponse.newBuilder();
        response.setResponseMsg("success!");
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
}
