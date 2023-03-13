import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class TCPClient {
    public static void main(String[] args) {
        System.out.println("System time - "  + System.currentTimeMillis() + " " + "Client main is called");
        if (args.length < 2) {
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "Using: java TcpClientSock <Host Name> <Port Number>");
            System.exit(1);
        }
        String hostName = args[0].toString();
        int portNumber = Integer.valueOf(args[1]).intValue();

        // send 5 request of each method
        for (int i = 0; i < 5; i++) {
            Long timeWriteMessage = System.currentTimeMillis();
            PutTrans(hostName, portNumber, "PUT" + " Key" + i + " Value1");
            Long timeReveivedReply = System.currentTimeMillis();
            Long totalTime = timeReveivedReply - timeWriteMessage;
            System.out.println("System processing time - "  + totalTime);
        }
        for (int i = 0; i < 5; i++) {
            Long timeWriteMessage = System.currentTimeMillis();
            GetTrans(hostName, portNumber, "GET KEY" + i);
            Long timeReveivedReply = System.currentTimeMillis();
            Long totalTime = timeReveivedReply - timeWriteMessage;
            System.out.println("System processing time - "  + totalTime);
        }
        for (int i = 0; i < 5; i++) {
            Long timeWriteMessage = System.currentTimeMillis();
            DeleteTrans(hostName, portNumber, "DEL" + " Key" + i);
            Long timeReveivedReply = System.currentTimeMillis();
            Long totalTime = timeReveivedReply - timeWriteMessage;
            System.out.println("System processing time - "  + totalTime);
        }
    }

    private static void PutTrans(String hostName, int portNumber, String clientMsg) {
        try {
            // create socket with host name and portNumber
            Socket client = new Socket(hostName, portNumber);
            DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "Write put request ");
            // send msg to server
            outputStream.writeUTF(clientMsg);
            // get acknowledgement msg from server
            ReplyFromServer(client);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void GetTrans(String hostName, int portNumber, String clientMsg) {
        try {
            Socket client = new Socket(hostName, portNumber);
            DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "Write get request ");
            // send msg to server
            outputStream.writeUTF(clientMsg);
            // get acknowledgement msg from server
            ReplyFromServer(client);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void DeleteTrans(String hostName, int portNumber, String clientMsg) {
        try {
            Socket client = new Socket(hostName, portNumber);
            DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "Write delete request ");
            // send msg to server
            outputStream.writeUTF(clientMsg);
            // get acknowledgement msg from server
            ReplyFromServer(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ReplyFromServer(Socket client) {
        try {
            DataInputStream inputStream = new DataInputStream(client.getInputStream());
            // set time out to 3000
            client.setSoTimeout(3000);
            // read msg from server
            String messageFromServer = inputStream.readUTF();
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "Server: " + messageFromServer);
        } catch (SocketTimeoutException e) {
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "Server is not responding. Timeout error has occured.");
        } catch (Exception ex) {
            System.out.println("System time - "  + System.currentTimeMillis() + " " + "Exception2: " + ex);
        }
    }
}
