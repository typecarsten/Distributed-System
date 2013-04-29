package middleware;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Random;

public class Functions {
	private final int PORT = 1337;
	private byte[] receiveData;
	private DatagramPacket receivePacket;
	private InetAddress serverIp;
	private DataOutputStream dataOut = null;
	private Socket serverSocket;

	public boolean findServer() throws SocketTimeoutException {
		DatagramSocket dataSocket = null;
		try {
			dataSocket = new DatagramSocket();
			dataSocket.setBroadcast(true);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte[] sendData = new byte[100];
		DatagramPacket sendPacket = new DatagramPacket(sendData,
				sendData.length);
		try {
			sendPacket.setAddress(InetAddress.getByAddress(new byte[] {
					(byte) 255, (byte) 255, (byte) 255, (byte) 255 }));
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sendPacket.setPort(PORT);

		String sendString = "Server request";

		sendData = sendString.getBytes();
		sendPacket.setData(sendData);
		
		try {
			dataSocket.send(sendPacket);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		receiveData = new byte[100];
		receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				dataSocket.receive(receivePacket);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		InputStreamReader receive = new InputStreamReader(
				new ByteArrayInputStream(receivePacket.getData()),
				Charset.forName("UTF-8"));
		StringBuilder receivedString = new StringBuilder();
		try {
			for (int i; (i = receive.read()) != -1;) {
				if (i > 64 && i < 122 || i == 32) {
					receivedString.append((char) i);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (receivedString.toString().equalsIgnoreCase("Server request")) {
			serverIp = receivePacket.getAddress();
			dataSocket.close();
			return true;
		}else {
			try {
				dataSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		dataSocket.close();
		return false;
	}

	public void publish(int data_type, double value) {
		dataOut = connect(PORT);
		try {
			dataOut.writeBytes("Publish" + ";" + data_type + ";" + value);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			dataOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DataOutputStream connect(int PORT) {
		try {
			serverSocket = new Socket(serverIp, PORT);
			return new DataOutputStream(serverSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void closeConnection() {
		try {
			dataOut.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void subscribe(int data_type, int replyPort) {
		dataOut = connect(PORT);
		try {
			dataOut.writeBytes("Subscribe" + ";" + data_type + ";" + replyPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeConnection();
	}

	public StringBuilder subscribtionReceive(int replyPort) {
		byte[] receiveData = new byte[100];
		DatagramPacket receivePacket = new DatagramPacket(receiveData,
				receiveData.length);
		DatagramSocket subscribeSocket = null;
		try {
			subscribeSocket = new DatagramSocket(replyPort);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			subscribeSocket.receive(receivePacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		subscribeSocket.close();
		InputStreamReader receive = new InputStreamReader(
				new ByteArrayInputStream(receivePacket.getData()),
				Charset.forName("UTF-8"));
		StringBuilder receivedString = new StringBuilder();
		try {
			for (int i; (i = receive.read()) != -1;) {
				receivedString.append((char) i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return receivedString;
	}

	public int generate_temp() {
		int random_num;
		Random generator = new Random();
		random_num = generator.nextInt(9) + 14;
		System.out.println(random_num);
		return random_num;
	}
}
