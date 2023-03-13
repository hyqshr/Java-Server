import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws IOException {
        boolean inputValid = false;
        Socket socket = new Socket("localhost", 59990);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter the message you want to send to the server");

        while (!inputValid) {
            String input = myObj.nextLine();  // Read user input
            System.out.println("Client Input: " + input);  // Output user input
            if (input.length() >= 6 && input.length()<80){
                inputValid = true;
                printWriter.println(input);
                printWriter.flush();
            } else{
                System.out.println("input.length: " + input.length());
                System.out.println("Please input 6-80 character! ");
            }
        }

        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        String str = bf.readLine();

        System.out.println("Server Response: " + str);

        try {
            socket.close();
        }
        catch(Exception e){
            System.out.println("Client Close Error");
        }

    }
}
