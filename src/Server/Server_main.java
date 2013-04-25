package Server;
import java.rmi.RemoteException;


public class Server_main {

	/**
	 * @param args
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws RemoteException {
		Server_socket_program socket = new Server_socket_program();
		Thread socket_thread = new Thread(socket);
//		Thread RMI = new Thread(new RmiServer(socket));
//		RMI.run();
		socket.run();
	}

}
