package Subscribers;

import java.net.SocketTimeoutException;

import middleware.Functions;

public class Gulvvarme {
	
	public static void main(String[] args) {
		Functions func = new Functions();
			run(func);
	}

	private static void run(Functions func) {
		int gulvvarmePort = 1234;
		int data_type = 1; // temp
		try {
			if(func.findServer()){
				func.subscribe(data_type,gulvvarmePort);
				while(true){
				StringBuilder receivedString = func.subscribtionReceive(gulvvarmePort);
				String[] string = receivedString.toString().split(";");
				double value = Double.parseDouble(string[1]);
				System.out.println(value);
				}
			}
		} catch (SocketTimeoutException e) {
			run(func);
			System.out.println(e);
		}
	}

}
