// Java program for client application
import java.rmi.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRequest {
    public static void main(String[] args) {
        try
        {
            // init the 10 integer list to be sorted
            List<Integer> value = new ArrayList<Integer>() {{
                add(1);
                add(3);
                add(5);
                add(1);
                add(2);
                add(11);
                add(4);
                add(9);
                add(7);
                add(2);

            }};
            System.out.println("Let the server sort this: " + value);
            // lookup method
            Server access = (Server)Naming.lookup("rmi://localhost:9999"+ "/sort");
            List<Integer> answer = access.sort(value);
            System.out.println("Answer received: " + answer);
        }
        catch(Exception ae)
        {
            System.out.println(ae);
        }
    }
}
