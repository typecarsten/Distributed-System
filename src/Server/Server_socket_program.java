package Server;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.Provider.Service;
import java.text.DecimalFormat;

public class Server_socket_program implements Runnable {

	ServerSocket serverSocket = null;
	Socket clientSocket = null;
	String inputLine;
	BufferedReader in = null;
	int count = 0;
	double avg_temp = 0;
	double temp = 0;
	double temp_all = 0;
	int port_no = 1337;
	DecimalFormat df = new DecimalFormat("#.##");

	public void create_connection() {
		try {
			serverSocket = new ServerSocket(port_no);
			System.out.println("Lytter på " + port_no);
		} catch (IOException e) {
			System.out.println("Could not listen on port: " + port_no);
			System.exit(-1);
		}

		try {
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			System.out.println("Accept failed: " + port_no);
			System.exit(-1);
		}

		try {
			in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void read_input(){
			try {
				while (true) {
				if((inputLine = in.readLine()) != null) {
					System.out.println(inputLine);
					String[] arguments = inputLine.split(";");
					if (arguments[0].equalsIgnoreCase("Server request")) {
						System.out.println("fedt");
					}
				} else {
					closeConnection();
					create_connection();
				}
				}
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
	}

	private void closeConnection() throws IOException {
		in.close();
		serverSocket.close();
	}

	public void datagramConnection(){
		byte[] receiveData = new byte[100];
		   DatagramSocket clientSocket= null;
		try {
			clientSocket = new DatagramSocket(port_no);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  DatagramPacket receivePacket =
		          new DatagramPacket(receiveData,
		                       receiveData.length);
		  
		       try {
				clientSocket.receive(receivePacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		       if(receivePacket.getData().equals("Server request")){
		       byte[] sendData = new byte[100];
		       String sendString = "ServerIp: " + clientSocket.getLocalAddress();
		       sendData = sendString.getBytes();
		       DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length);
		       try {
				clientSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		       }
		       System.out.println(receivePacket.getAddress());
		       
		       InputStreamReader receive = new InputStreamReader(new ByteArrayInputStream(receivePacket.getData()), Charset.forName("UTF-8"));
		       StringBuilder receivedString = new StringBuilder();
		       try {
				for (int i; (i = receive.read()) != -1;) {
					if (i>64 && i<122 || i == 32) {
						receivedString.append((char)i);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		       System.out.println(receivedString);
	}
	
	@Override
	public void run() {
		datagramConnection();
		create_connection();
		read_input();
	}
}
