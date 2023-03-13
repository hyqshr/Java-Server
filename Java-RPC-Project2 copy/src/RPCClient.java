import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class RPCClient implements AutoCloseable {

    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";
    private static Logger logger = Logger.getLogger(RPCClient.class.getName());

    public RPCClient() throws IOException, TimeoutException {
        // create the connection to RMQ
        logger.info(System.currentTimeMillis() + " create connection");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public static void main(String[] argv) {
        try (RPCClient fibonacciRpc = new RPCClient()) {
            // send 100 times of each request
            for (int i = 0; i < 100; i++) {
                String i_str = Integer.toString(i);
                String response = fibonacciRpc.call("PUT," + i_str + "-" + "value" + i_str);
                logger.info("System time: " + System.currentTimeMillis() + " - PUT response from server: " + response);
            }
            for (int i = 0; i < 100; i++) {
                String i_str = Integer.toString(i);
                String response = fibonacciRpc.call("GET," + i_str);
                logger.info("System time: " + System.currentTimeMillis() + " - GET response from server: " + response);
            }
            for (int i = 0; i < 100; i++) {
                String i_str = Integer.toString(i);
                String response = fibonacciRpc.call("DELETE," + i_str);
                logger.info("System time: " + System.currentTimeMillis() + " - DELETE response from server: " + response);
            }
        } catch (IOException | TimeoutException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public String call(String message) throws IOException, InterruptedException, ExecutionException {
        // get correlationID and set up the queue
        final String corrId = UUID.randomUUID().toString();
        String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));

        final CompletableFuture<String> response = new CompletableFuture<>();

        String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response.complete(new String(delivery.getBody(), "UTF-8"));
            }
        }, consumerTag -> {
        });

        String result = response.get();
        // cancel a consumer
        channel.basicCancel(ctag);
        return result;
    }

    public void close() throws IOException {
        connection.close();
    }
}
