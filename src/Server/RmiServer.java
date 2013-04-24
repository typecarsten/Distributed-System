package Server;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*; 
 
public class RmiServer extends UnicastRemoteObject 
    implements RmiServerIntf, Runnable {
	Server_socket_program socket;
	private double avg_temp = 2;
 
	public RmiServer(Server_socket_program server_socket) throws RemoteException {
		this.socket = server_socket;
	}
 
    public double Status() {
        return socket.avg_temp;
    }

	@Override
	public void run() {
        System.out.println("RMI server started");
        
        // Create and install a security manager
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
            System.out.println("Security manager installed.");
        } else {
            System.out.println("Security manager already exists.");
        }
 
        try { //special exception handler for registry creation
            LocateRegistry.createRegistry(1099); 
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            //do nothing, error means registry already exists
            System.out.println("java RMI registry already exists.");
        }
 
        try {
            //Instantiate RmiServer
            RmiServer obj = new RmiServer(socket);
 
            // Bind this object instance to the name "RmiServer"
            Naming.rebind("//localhost/RmiServer", obj);
 
            System.out.println("PeerServer bound in registry");
        } catch (Exception e) {
            System.err.println("RMI server exception:" + e);
            e.printStackTrace();
        }
	}
}