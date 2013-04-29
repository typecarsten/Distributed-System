package Server;
import java.rmi.RemoteException;


public class Server_main {

	/**
	 * @param args
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws RemoteException {
		CalculateAvgTemp cal = new CalculateAvgTemp();
		Thread calThread = new Thread(cal);
		Thread RMI = new Thread(new RmiServer(cal));
		RMI.start();
		calThread.start();
	}

}
