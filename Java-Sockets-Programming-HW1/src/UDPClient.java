import java.io.IOException;
import java.net.*;
import java.util.Arrays;


public class UDPClient {
    public static void main(String[] args) {
        System.out.println("System time - "  + System.currentTimeMillis() + " " + "Client main is called");
        if (args.length < 2) {
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "Using: java TcpClientSock <Host Name> <Port Number>");
            System.exit(1);
        }

        String hostName = args[0].toString();
        int portNumber = Integer.valueOf(args[1]).intValue();

        // send 5 PUT request and count the processing time for each msg
        for (int i = 0; i < 5; i++) {
            Long timeWriteMessage = System.currentTimeMillis();
            PutTrans(hostName, portNumber, "PUT" + ",Key" + i + ",Value1");
            Long timeReveivedReply = System.currentTimeMillis();
            Long totalTime = timeReveivedReply - timeWriteMessage;
            System.out.println("System processing time - "  + totalTime);
        }
        // send 5 PUT request and count the processing time for each msg
        for (int i = 0; i < 5; i++) {
            Long timeWriteMessage = System.currentTimeMillis();
            GetTrans(hostName, portNumber, "GET,KEY" + i);
            Long timeReveivedReply = System.currentTimeMillis();
            Long totalTime = timeReveivedReply - timeWriteMessage;
            System.out.println("System processing time - "  + totalTime);
        }
        // send 5 PUT request and count the processing time for each msg
        for (int i = 0; i < 5; i++) {
            Long timeWriteMessage = System.currentTimeMillis();
            DeleteTrans(hostName, portNumber, "DEL" + ",Key" + i);
            Long timeReveivedReply = System.currentTimeMillis();
            Long totalTime = timeReveivedReply - timeWriteMessage;
            System.out.println("System processing time - "  + totalTime);
        }
    }

    private static void PutTrans(String hostName, int portNumber, String clientMsg) {
        try {
            DatagramSocket client = new DatagramSocket();
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "Write put request ");
            // get the ip address
            InetAddress host = InetAddress.getByName(hostName);
            DatagramPacket clientMsgPacket = new DatagramPacket(clientMsg.getBytes(),clientMsg.length(),host,portNumber);
            // send msg to server
            client.send(clientMsgPacket);
            ReplyFromServer(client);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void GetTrans(String hostName, int portNumber, String clientMsg) {
        try {
            DatagramSocket client = new DatagramSocket();
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "Write get request ");
            // get the ip address
            InetAddress host = InetAddress.getByName(hostName);
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "InetAddress of " + hostName + " = " + host);
            DatagramPacket clientMsgPacket = new DatagramPacket(clientMsg.getBytes(),clientMsg.length(),host,portNumber);
            // send msg to server
            client.send(clientMsgPacket);
            ReplyFromServer(client);
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void DeleteTrans(String hostName, int portNumber, String clientMsg) {
        try {
            DatagramSocket client = new DatagramSocket();
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "Write delete request ");
            // get the ip address
            InetAddress host = InetAddress.getByName(hostName);
            DatagramPacket clientMsgPacket = new DatagramPacket(clientMsg.getBytes(),clientMsg.length(),host,portNumber);
            // send msg to server
            client.send(clientMsgPacket);
            ReplyFromServer(client);
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ReplyFromServer(DatagramSocket client) {
        try {
            // set time out limit to 3s
            client.setSoTimeout(3000);
            byte[] ackMsgBuffer = new byte[500];

            // Constructs a DatagramPacket for receiving packets of length
            DatagramPacket returnMsgPacket = new DatagramPacket(ackMsgBuffer, ackMsgBuffer.length);
            client.receive(returnMsgPacket);
            String messageFromServer = new String(Arrays.copyOfRange(returnMsgPacket.getData(), 0, returnMsgPacket.getLength()));
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "Acknowledgement message: " + messageFromServer + "\n");
        } catch (SocketTimeoutException e) {
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "Server is not responding. Timeout error has occured.");
        } catch (IOException e) {
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "An exception has occured: " + e);
        } catch (Exception ex) {
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "Exception: " + ex);
        }
    }
}
