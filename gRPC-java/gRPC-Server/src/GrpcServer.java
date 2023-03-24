import java.io.IOException;

import Service.putService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8080)
                .addService(new putService()).build();
        server.start();
        System.out.println("Server started!");
        server.awaitTermination();
    }
}