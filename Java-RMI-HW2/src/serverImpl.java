// implement the server
import java.rmi.*;
import java.rmi.server.*;
import java.util.Collections;
import java.util.List;

public class serverImpl extends UnicastRemoteObject implements Server{
    serverImpl() throws RemoteException
    {
        super();
    }
    public List<Integer> sort(List<Integer> integerList) throws RemoteException{
        Collections.sort(integerList);
        return integerList;
    }
}
