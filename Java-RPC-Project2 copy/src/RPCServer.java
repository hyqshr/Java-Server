import com.rabbitmq.client.*;

import java.util.Map;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class RPCServer {
    // declare the queue name
    private static final String RPC_QUEUE_NAME = "rpc_queue";
    private static Logger logger = Logger.getLogger(RPCClient.class.getName());
    // create a thread safe hashmap
    private static ExecutorService executorsService = Executors.newFixedThreadPool(10);

    public static void main(String[] argv) throws Exception {
        // server thread-safe map
        Map<String, String> map = new ConcurrentHashMap<String, String>();

        // create connection
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // declare a new queue
        channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
        channel.queuePurge(RPC_QUEUE_NAME);
        channel.basicQos(1);

        logger.info("System time: " + System.currentTimeMillis() + "- Awaiting RPC requests...");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(delivery.getProperties().getCorrelationId())
                    .build();

            String response = "";
            try {
                String message = new String(delivery.getBody(), "UTF-8");
                logger.info("System time: " + System.currentTimeMillis() + "- message received: "+ message);
                Callable<String> callable = new RequestHandler(message, map);
                Future<String> threadResult = executorsService.submit(callable);
                response += threadResult.get();
            } catch (RuntimeException e) {
                System.out.println(e);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };

        channel.basicConsume(RPC_QUEUE_NAME, false, deliverCallback, (consumerTag -> {}));
    }

}