package middleware;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;

public class Datagramconnection implements Runnable {
	private int PORT = 1337;

	public void datagramConnection() {
		while(true){
		byte[] receiveData = new byte[100];
		DatagramSocket clientSocket = null;
		try {
			clientSocket = new DatagramSocket(PORT);
			System.out.println("Venter på datagram");
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DatagramPacket receivePacket = new DatagramPacket(receiveData,
				receiveData.length);

		try {
			clientSocket.receive(receivePacket);
			System.out.println("datagram modtaget");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(receivePacket.getAddress());

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
		if (receivedString.toString().equals("Server request")) {
			byte[] sendData = new byte[100];
			InetAddress address = receivePacket.getAddress();
			int port = receivePacket.getPort();
			sendData = receivedString.toString().getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData,
					sendData.length, address, port);
			try {
				clientSocket.send(sendPacket);
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(receivedString);
		}
	}

	@Override
	public void run() {
		datagramConnection();
	}

}
