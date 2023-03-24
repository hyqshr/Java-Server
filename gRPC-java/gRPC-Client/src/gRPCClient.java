import com.yiqiu.grpc.putRequest;
import com.yiqiu.grpc.putServiceGrpc;
import io.grpc.ManagedChannel;
import com.yiqiu.grpc.putServiceGrpc;
import io.grpc.ManagedChannelBuilder;

public class gRPCClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",8080).usePlaintext().build();

        putServiceGrpc.putServiceBlockingStub serviceStub = putServiceGrpc.newBlockingStub(channel);
        putRequest request = putRequest.newBuilder().setKey("123").setVal("234").build();
        System.out.println("Clinet send!");
        serviceStub.put(request);

    }
}
