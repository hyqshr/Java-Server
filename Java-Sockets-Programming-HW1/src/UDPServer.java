import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UDPServer {
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "Usage: enter java UpdServerSock <Port Number>");
            System.exit(1);
        }
        int portNumber = Integer.valueOf(args[0]).intValue();
        Map<String, String> messageStoreMap = new HashMap<String, String>();
        try {
            DatagramSocket socket = new DatagramSocket(portNumber);
            byte[] msgbuffer = new byte[500];
            while (true) {
                DatagramPacket dataPacket = new DatagramPacket(msgbuffer, msgbuffer.length);
                System.out.println("System time - "  + System.currentTimeMillis() + " " + "UDP server listening on port: " + portNumber);
                socket.receive(dataPacket);

                // trunc unreadable part of byte
                String messageFromClient = new String(Arrays.copyOfRange(dataPacket.getData(), 0, dataPacket.getLength()));
                System.out.println("System time - "  + System.currentTimeMillis() + " " + "Message from client: " + messageFromClient);
                if (!messageFromClient.equals("")) {
                    String requestType = messageFromClient.split(",")[0];
                    System.out.println("System time - "  + System.currentTimeMillis() + " " + "requestType: " + requestType);
                    if (!requestType.equals("") && requestType.equalsIgnoreCase("PUT")) {
                        PutRequest(socket, dataPacket, messageStoreMap);
                    } else if (!requestType.equals("") && requestType.equalsIgnoreCase("GET")) {
                        GetRequest(socket, dataPacket, messageStoreMap);
                    } else if (!requestType.equals("") && requestType.equalsIgnoreCase("DEL")) {
                        DeleteRequest(socket, dataPacket, messageStoreMap);
                    } else {
                        System.out.println("System time - "  + System.currentTimeMillis() + " " + "Server: Unknown request method received");
                    }
                } else{
                    System.out.println("System time - "  + System.currentTimeMillis() + " " + "Server: Empty message received");
                }
                System.out.println("System time - "  + System.currentTimeMillis() + " " + "current Map size is: " + messageStoreMap.size());
                System.out.println("System time - "  + System.currentTimeMillis() + " " + "current Map : " + messageStoreMap + "\n");

            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void PutRequest(DatagramSocket socket, DatagramPacket clientPacket, Map<String, String> messageStoreMap) {
        System.out.println("System time - "  + System.currentTimeMillis() + " " + "Received a PUT request from " + clientPacket.getAddress() + " at Port " + clientPacket.getPort());
        // get those byte with actual data
        String messageData = new String(Arrays.copyOfRange(clientPacket.getData(), 0, clientPacket.getLength()));
        if (!messageData.equals("")) {
            // the request will be seperate by ,
            String key = messageData.split(",")[1];
            String message = messageData.split(",")[2];
            if (!key.equals("")) {
                System.out.println("System time - "  + System.currentTimeMillis() + " " + "The request is to store a message with key: " + key + " and Message" + message);
                messageStoreMap.put(key.trim(), message);
                ReplyToClient(socket, clientPacket, "PUT", key, "");

            } else {
                String failureMsg = "Received a malformed request of length: " + clientPacket.getLength() + " from: "
                        + clientPacket.getAddress() + " at Port: " + clientPacket.getPort();
                System.out.println("System time - "  + System.currentTimeMillis() + " " + failureMsg);
                sendFailureAckToClient(socket, clientPacket, failureMsg);
            }

        } else {
            String failureMsg = "The message content is not present.";
            System.out.println("System time - "  + System.currentTimeMillis() + " " + failureMsg);
            sendFailureAckToClient(socket, clientPacket, failureMsg);
        }

    }

    private static void GetRequest(DatagramSocket socket, DatagramPacket clientPacket,
                                   Map<String, String> messageStoreMap) {
        System.out.println("System time - "  + System.currentTimeMillis() + " " + "Received a GET request from " + clientPacket.getAddress() + " at Port " + clientPacket.getPort());
        String messageData = new String(clientPacket.getData());
        if (!messageData.equals("")) {
            String key = messageData.split(",")[1];
            if (!key.equals("")) {
                System.out.println("System time - "  + System.currentTimeMillis() + " " + "The request is to get a message with key: " + key);
                if (messageStoreMap.containsKey(key.trim())) {
                    String retrievedMsg = messageStoreMap.get(key.trim());
                    ReplyToClient(socket, clientPacket, "GET", key, retrievedMsg);
                } else {
                    String failureMsg = "There is no key-value pair for key: " + key;
                    System.out.println("System time - "  + System.currentTimeMillis() + " " + failureMsg);
                    sendFailureAckToClient(socket, clientPacket, failureMsg);
                }

            } else {
                String failureMsg = "Received a malformed request of length: " + clientPacket.getLength() + " from: "
                        + clientPacket.getAddress() + " at Port: " + clientPacket.getPort();
                System.out.println("System time - "  + System.currentTimeMillis() + " " + failureMsg);
                sendFailureAckToClient(socket, clientPacket, failureMsg);
            }

        } else {
            String failureMsg = "The message content is not present.";
            System.out.println("System time - "  + System.currentTimeMillis() + " " + failureMsg);
            sendFailureAckToClient(socket, clientPacket, failureMsg);
        }

    }

    private static void DeleteRequest(DatagramSocket socket, DatagramPacket clientPacket,
                                      Map<String, String> messageStoreMap) {
        System.out.println("System time - "  + System.currentTimeMillis() + " " +
                "Received a DELETE request from " + clientPacket.getAddress() + " at Port " + clientPacket.getPort());
        String messageData = new String(clientPacket.getData());
        if (!messageData.equals("")) {
            String key = messageData.split(",")[1];
            if (!key.equals("")) {
                System.out.println("System time - "  + System.currentTimeMillis() + " " + "The request is to delete a message with key: " + key);
                if (messageStoreMap.containsKey(key.trim())) {
                    messageStoreMap.remove(key.trim());
                    ReplyToClient(socket, clientPacket, "DEL", key, "");
                } else {
                    String failureMsg = "There exist no such key-value pair for key: " + key;
                    System.out.println("System time - "  + System.currentTimeMillis() + " " + failureMsg);
                    sendFailureAckToClient(socket, clientPacket, failureMsg);
                }

            } else {
                String failureMsg = "Received a malformed request of length: " + clientPacket.getLength() + " from: "
                        + clientPacket.getAddress() + " at Port: " + clientPacket.getPort();
                System.out.println("System time - "  + System.currentTimeMillis() + " " + failureMsg);
                sendFailureAckToClient(socket, clientPacket, failureMsg);
            }

        } else {
            String failureMsg = "The message content is not present.";
            System.out.println("System time - "  + System.currentTimeMillis() + " " + failureMsg);
            sendFailureAckToClient(socket, clientPacket, failureMsg);
        }
    }

    private static void ReplyToClient(DatagramSocket socket, DatagramPacket request, String requestType, String key,
                                      String returnMsg) {
        System.out.println("System time - "  + System.currentTimeMillis() + " " + "Sending acknowledgement to client...");
        try {
            if (!returnMsg.equals("") && requestType.equalsIgnoreCase("GET")) {
                String ackGetMessage = "Retrieved value with key: " + key + " is: " + returnMsg;
                DatagramPacket ackMsgPacket = new DatagramPacket(ackGetMessage.getBytes(), ackGetMessage.length(), request.getAddress(), request.getPort());
                socket.send(ackMsgPacket);
            } else {
                String ackMessage = requestType + " with key: " + key + " SUCCESS";
                DatagramPacket ackMsgPacket = new DatagramPacket(ackMessage.getBytes(), ackMessage.length(), request.getAddress(), request.getPort());
                socket.send(ackMsgPacket);
            }
        } catch (IOException e) {
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "An exception has occured: " + e);
        }
    }

    private static void sendFailureAckToClient(DatagramSocket socket, DatagramPacket request, String returnMsg) {
        System.out.println("System time - "  + System.currentTimeMillis() + " " + "Sending acknowledgement to client for failure...");
        try {
            byte[] ackMessage = new byte[500];
            ackMessage = ("Request FAILED due to: " + returnMsg).getBytes();
            DatagramPacket ackMsgPacket = new DatagramPacket(ackMessage, ackMessage.length, request.getAddress(),
                    request.getPort());
            socket.send(ackMsgPacket);

        } catch (IOException e) {
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "An exception has occured: " + e);
        }

    }
}
