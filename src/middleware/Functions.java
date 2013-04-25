package middleware;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Functions {
	private Socket serverSocket = null;
	private DataOutputStream dataOut = null;
	private BufferedReader dataIn = null;
	private final String HOSTNAME = "192.168.88.255";
	private final int PORT = 1337;
	private String serverIp = null;

	public void findServer() {
		DatagramSocket dataSocket = null;
        try {
        	dataSocket = new DatagramSocket();
			dataSocket.setBroadcast(true);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        byte[] sendData = new byte[100];
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length);
        try {
			sendPacket.setAddress(InetAddress.getByAddress(new byte[] { (byte) 255,
			        (byte) 255, (byte) 255, (byte) 255 }));
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
        
        
		
//		try {
//			serverSocket = new Socket(HOSTNAME, PORT);
//			System.out.println(serverSocket.getLocalSocketAddress());
//			dataOut = new DataOutputStream(serverSocket.getOutputStream());
//			dataIn = new BufferedReader(new InputStreamReader(
//					serverSocket.getInputStream()));
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		try {
//			if (dataOut != null) {
//				dataOut.writeBytes("Server request;"
//						+ serverSocket.getLocalSocketAddress());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
////		if (dataIn != null) {
////			try {
////				while ((serverIp = dataIn.readLine()) != null) {
////					return serverIp;
////				}
////			} catch (IOException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////		}
//		return null;


	}

}
