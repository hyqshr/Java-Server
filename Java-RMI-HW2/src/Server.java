// Creating a server interface
import java.rmi.*;
import java.util.List;

public interface Server extends Remote
{
    // Declaring the method prototype
    public List<Integer> sort(List<Integer> integerList) throws RemoteException;

}