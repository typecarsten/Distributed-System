package Sensor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

import javax.sound.sampled.Port;

public class Sensor_socket {

	private int port_number = 0;
	private String hostname = "localhost";
	private Socket serverSocket = null;
	private DataOutputStream dataOut = null;
	
	public void publish(int data_type, double randomnum) {
		// envent_type 1 = publish, 2 = subscribe
		int event_type = 1;
		connection_data_class cd = new connection_data_class();
		connect(cd);
		try {
			dataOut.writeBytes(event_type + ";" + data_type + ";" + randomnum);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeConnection();
	}

	public void subscribe(int data_type) {
		// envent_type 1 = publish, 2 = subscribe
		int event_type = 2;
		connection_data_class cd = new connection_data_class();
		connect(cd);
		try {
			dataOut.writeBytes(event_type + ";" + data_type);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeConnection();
	}

	public int generate_num() {
		int random_num;
		Random generator = new Random();
		random_num = generator.nextInt(9) + 14;
		System.out.println(random_num);
		return random_num;
	}
	
	public void connect(Sensor_socket.connection_data_class cData) {
		try {
			serverSocket = new Socket(cData.getHostname(), cData.getPortNo());
			dataOut = new DataOutputStream(serverSocket.getOutputStream());

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void closeConnection() {
		try {
			dataOut.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Inner class with connection data
	private class connection_data_class {
		private String hostname = "localhost";
		private int port_no = 1337;

		/**
		 * Will connect to predefined host.
		 * 
		 * @param hostname
		 *            = "localhost"
		 * @param port_no
		 *            = 1337
		 */
		public connection_data_class() {
		}

		public connection_data_class(String hostname, int port_no) {
			this.hostname = hostname;
			this.port_no = port_no;
		}

		public String getHostname() {
			return hostname;
		}

		public int getPortNo() {
			return port_no;
		}

	}

}