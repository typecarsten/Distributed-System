package Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
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
						temp = Integer.parseInt(inputLine);
					if (temp != 0) {
						count++;
						temp_all = temp_all + temp;
						avg_temp = temp_all / count;
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

	@Override
	public void run() {
		create_connection();
		read_input();
	}
}
