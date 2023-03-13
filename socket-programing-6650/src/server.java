import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    public static String reverseCapitalizeString(String string){
        String reversedString = new StringBuilder(string).reverse().toString();
        return reversedString.toUpperCase();
    }
    public static void main(String[] args) throws IOException {
        // Socket and ServerSocket are used for the TCP protocol. The DatagramSocket and DatagramPacket are used for the UDP protocol.
        ServerSocket serverSocket = new ServerSocket(59990);
        System.out.println("Server running...");
        Socket s = serverSocket.accept();
        System.out.println("Client connected!");

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        String str = bf.readLine();

        System.out.println("Client: " + str);

        String result = reverseCapitalizeString(str);
        PrintWriter printWriter = new PrintWriter(s.getOutputStream());
        printWriter.println(result);
        printWriter.flush();

        try {
            serverSocket.close();
        }
        catch(Exception e){
            System.out.println("Server Close Error");
        }
    }
}
