import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class TCPServer {

    public static void main(String[] args) {
        if (!(args.length == 1)) {
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "Using: enter java <Port Number>");
            System.exit(1);
        }

        int portNumber = Integer.valueOf(args[0]).intValue();
        Map<String, String> messageStoreMap = new HashMap<String, String>();

        try {
            ServerSocket server = new ServerSocket(portNumber);
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "port number: " + portNumber);
            while (true) {
                System.out.println("System time - "  + System.currentTimeMillis() + " " + "Listening ...");
                Socket client = server.accept();
                DataInputStream input = new DataInputStream(client.getInputStream());
                //read input with UTF
                String clientMessage = input.readUTF();
                System.out.println("System time - "  + System.currentTimeMillis() + " " + "Client Message Received!");
                if (!clientMessage.equals("")) {
                    String[] splitedClientMessage = clientMessage.split(" ");
                    String requestType = splitedClientMessage[0];
                    System.out.println("System time - "  + System.currentTimeMillis() + " " + "Client Message: " + clientMessage);
                    // handle each request if have the right method in the msg
                    if (requestType.equalsIgnoreCase("PUT")) {
                        PutRequest(client, clientMessage, messageStoreMap);
                    }else if (requestType.equalsIgnoreCase("GET")) {
                        GetRequest(client, clientMessage, messageStoreMap);
                    }else if (requestType.equalsIgnoreCase("DEL")) {
                        DeleteRequest(client, clientMessage, messageStoreMap);
                    }else{
                        System.out.println("System time - "  + System.currentTimeMillis() + " " + "Unknown request type: "+requestType+ " is received.");
                    }
                }else {
                    System.out.println("System time - "  + System.currentTimeMillis() + " " + "Error message receive");
                }
                System.out.println("System time - "  + System.currentTimeMillis() + " " + "current Map size is: " + messageStoreMap.size());
                System.out.println("System time - "  + System.currentTimeMillis() + " " + messageStoreMap);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void PutRequest(Socket client, String clientMessage, Map<String, String> messageStoreMap) {
        System.out.println("System time - "  + System.currentTimeMillis() + " " + "PUT request received from " + client.getInetAddress() + " at Port " + client.getPort());
        String[] splitedClientMessage = clientMessage.split(" ");
        if (splitedClientMessage.length == 3) {
            String key = splitedClientMessage[1];
            String value = splitedClientMessage[2];
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "The request is to store a message with key: " + key);
            messageStoreMap.put(key, value);
            ReplyToClient(client, "PUT", key, "", "SUCCESS");

        } else {
            String key = splitedClientMessage[1];
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "Received a wrong request of length: " + clientMessage.length() + " from: "
                    + client.getInetAddress() + " at Port: " + client.getPort());
            ReplyToClient(client, "PUT", key, "", "FAIL");

        }

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void GetRequest(Socket client, String clientMessage, Map<String, String> messageStoreMap) {
        System.out.println("System time - "  + System.currentTimeMillis() + " " + "GET request received from " + client.getInetAddress() + " at Port " + client.getPort());
        if (!Objects.equals(clientMessage, "")) {
            String[] splitedClientMessage = clientMessage.split(" ");
            String key = splitedClientMessage[1];
            System.out.println("System time - "  + System.currentTimeMillis() + " " + splitedClientMessage.length);
            if (!Objects.equals(key, "")) {
                System.out.println("System time - "  + System.currentTimeMillis() + " " + "The request is to store a message with key: " + key);
                String value = messageStoreMap.get(key);
                ReplyToClient(client, "GET", key, value, "Success");

            } else {
                System.out.println("System time - "  + System.currentTimeMillis() + " " + "Received a wrong request of length: " + clientMessage.length() + " from: "
                        + client.getInetAddress() + " at Port: " + client.getPort());
                ReplyToClient(client, "GET", key, null, "Fail");
            }

        } else {
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "The searched message content is not present.");
        }
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void DeleteRequest(Socket client, String clientMessage, Map<String, String> messageStoreMap) {
        System.out.println("System time - "  + System.currentTimeMillis() + " " + "DELETE request received from " + client.getInetAddress() + " at Port " + client.getPort());
        if (!Objects.equals(clientMessage, "")) {
            String[] splitedClientMessage = clientMessage.split(" ");
            String key = splitedClientMessage[1];
            if ((!Objects.equals(key, "")) && (messageStoreMap.containsKey(key))) {
                System.out.println("System time - "  + System.currentTimeMillis() + " " + "The request is to delete a message with key: " + key);
                messageStoreMap.remove(key);
                ReplyToClient(client, "DELETE", key, key, "Success");
            } else {
                System.out.println("System time - "  + System.currentTimeMillis() + " " + "Key does not exist");
                ReplyToClient(client, "DELETE", key, "Key does not exist", "Fail");
            }
        } else {
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "The searched message content is not present.");
        }
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ReplyToClient(Socket client, String requestType, String key, String returnMsg, String Success) {
        System.out.println("System time - "  + System.currentTimeMillis() + " " + "Sending acknowledgement to client...");
        try {
            DataOutputStream outStream = new DataOutputStream(client.getOutputStream());
            if (!Objects.equals(returnMsg, "") && requestType.equalsIgnoreCase("GET")) {
                outStream.writeUTF("Retrieved message with key: " + key + " is: " + returnMsg);
            } else {
                outStream.writeUTF(requestType + " with key: " + key + " " + Success);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}