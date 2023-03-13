// Java program for server application
import java.rmi.*;
import java.rmi.registry.*;

public class SortServer {
    public static void main(String[] args) {
        try {
            Server sorter = new serverImpl();
            // register the service at port 9999
            LocateRegistry.createRegistry(9999);
            // Binds the remote object with the name
            Naming.rebind("rmi://localhost:9999"+ "/sort", sorter);
        }
        catch(Exception ae)
        {
            System.out.println(ae);
        }
    }
}
