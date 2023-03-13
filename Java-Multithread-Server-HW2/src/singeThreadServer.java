import java.io.*;
import java.net.*;

// Server class
class singeThreadServer {
    public static void main(String[] args)
    {
        ServerSocket server = null;
        int totalHandledMsgCount = 0;
        try {
            // server is listening on port 1234
            server = new ServerSocket(1234);
            server.setReuseAddress(true);

            while (true) {
                // socket object to receive incoming client
                System.out.println("Server Listening");
                Socket client = server.accept();
                System.out.println("New client msg " + client.getInetAddress().getHostAddress());

                PrintWriter printWriter = new PrintWriter(client.getOutputStream());
                printWriter.println("Server receive the request");
                printWriter.flush();
                client.close();
                totalHandledMsgCount += 1;
                // create thread handle the client
                System.out.println("System time: " + System.currentTimeMillis() + " Total msg handled: " + totalHandledMsgCount);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}