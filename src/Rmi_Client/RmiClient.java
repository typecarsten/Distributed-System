package Rmi_Client;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;

import Server.RmiServerIntf;
 
public class RmiClient { 
    // "obj" is the reference of the remote object
    RmiServerIntf obj = null; 
 
    public double Status() { 
        try { 
            obj = (RmiServerIntf)Naming.lookup("//localhost/RmiServer");
            return obj.Status(); 
        } catch (Exception e) { 
            System.err.println("RmiClient exception: " + e); 
            e.printStackTrace(); 
 
            return -1;
        } 
    } 
 
    public static void main(String args[]) {
        // Create and install a security manager
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
 
        RmiClient cli = new RmiClient();
 
        System.out.println(cli.Status());
    }
}