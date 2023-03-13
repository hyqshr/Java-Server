import java.io.*;
import java.net.*;

// Server class
class multiThreadServer {
    public static void main(String[] args)
    {
        ServerSocket server = null;
        try {
            // server is listening on port 1234
            server = new ServerSocket(1234);
            server.setReuseAddress(true);

            while (true) {
                // socket object to receive incoming client
                System.out.println("Server Listening");
                Socket client = server.accept();
                System.out.println("New client msg " + client.getInetAddress().getHostAddress());

                // create a new thread object
                new Thread(new ClientHandler(client)).start();
                // create thread handle the client
                System.out.println("System time: " + System.currentTimeMillis() + " Total msg handled: " + ClientHandler.totalHandledMsgCount);
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

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        public static int totalHandledMsgCount = 0;

        // Constructor
        public ClientHandler(Socket socket)
        {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                // sent the acknowledgement to client and close the socket
                PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());
                printWriter.println("Server receive the request");
                printWriter.flush();
                clientSocket.close();
                addTotalHandledMsgCount();
            }
            catch (IOException e) {
                System.out.println("Request Fail");
                e.printStackTrace();
            }
        }

        private synchronized void addTotalHandledMsgCount(){
            totalHandledMsgCount += 1;
        }
    }
}
