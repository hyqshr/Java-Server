import java.io.*;
import java.net.*;
import java.util.Date;

// Client class
class clientMultiThread {

    // driver code
    public static void main(String[] args)
    {
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;

        while (elapsedTime < 5000) {
            //perform db poll/check
            clientMultiThread.sendMsgToServer();
            elapsedTime = (new Date()).getTime() - startTime;
        }
    }

    public static void sendMsgToServer(){
        // establish a connection by providing host and port
        // number
        try (Socket socket = new Socket("localhost", 1234)) {

            // writing to server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Client: Please handle my request");

            // reading from server
            InputStreamReader in = new InputStreamReader(socket.getInputStream());
            BufferedReader bf = new BufferedReader(in);
            String str = bf.readLine();
            System.out.println("Server Response: " + str);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
