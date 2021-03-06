package middleware;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Middleware implements Runnable {
	private List<SubscribeDataClass> SubscriberList = new ArrayList<>();;
	private final int PORT = 1337;
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private String inputLine;
	private BufferedReader in = null;
	DatagramSocket publishSocket = null;

	public void publishSubscribeReader() {
		while (true) {
			System.out.println("Venter p� et Publish eller Subscribe");
			listen(PORT);
			read_input();
		}
	}

	private void listen(int PORT) {
		try {
			serverSocket = new ServerSocket(PORT);
			clientSocket = serverSocket.accept();
			serverSocket.close();
			in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void read_input() {
		try {
				if ((inputLine = in.readLine()) != null) {
					String[] arguments = inputLine.split(";");
					if (arguments[0].equalsIgnoreCase("Publish")) {
						System.out.println("Publish modtaget");
						int data_type = Integer.parseInt(arguments[1]);
						Double value = Double.parseDouble(arguments[2]);
						byte[] transmitPublish = new byte[100];
						publishSocket = new DatagramSocket();
						String pubmsg = data_type + ";" + value;
						transmitPublish = pubmsg.getBytes();
						DatagramPacket publishPacket = new DatagramPacket(
								transmitPublish, transmitPublish.length);
						if (SubscriberList.size() > 0) {
							for (SubscribeDataClass subscriber : SubscriberList) {
								publishPacket.setAddress(subscriber.getServerIp());
								publishPacket.setPort(subscriber.getPort());
								if (subscriber.getData_type() == data_type) {
									publishSocket.send(publishPacket);
								}
							}
						}
					}
					if (arguments[0].equalsIgnoreCase("Subscribe")) {
						int data_type = Integer.parseInt(arguments[1]);
						int port = Integer.parseInt(arguments[2]);
						SubscribeDataClass subscriber = new SubscribeDataClass(clientSocket.getInetAddress(), port, data_type);
						SubscriberList.add(subscriber);
					}
				}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		try {
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		publishSubscribeReader();
	}
}
