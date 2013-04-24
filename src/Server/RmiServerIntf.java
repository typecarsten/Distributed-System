package Server;
import java.rmi.Remote;
import java.rmi.RemoteException;
 
public interface RmiServerIntf extends Remote {
    public double Status() throws RemoteException;
}