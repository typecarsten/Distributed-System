package Server;
import java.net.SocketTimeoutException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*; 

import middleware.Functions;
 
public class RmiServer extends UnicastRemoteObject 
    implements RmiServerIntf, Runnable {
	private double avg_temp = 0;
	Functions func = null;
	private int count = 0;
	private double temp_all = 0;
	CalculateAvgTemp cal= null;
 
	public RmiServer(CalculateAvgTemp cal) throws RemoteException {
		this.cal = cal;
	}
 
    public double Status() {
        return cal.getAvg_temp();
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
            RmiServer obj = new RmiServer(cal);
 
            // Bind this object instance to the name "RmiServer"
            Naming.rebind("//localhost/RmiServer", obj);
 
            System.out.println("PeerServer bound in registry");
        } catch (Exception e) {
            System.err.println("RMI server exception:" + e);
            e.printStackTrace();
        }
	}
}