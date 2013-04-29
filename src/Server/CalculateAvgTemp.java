package Server;

import java.net.SocketTimeoutException;

import middleware.Functions;

public class CalculateAvgTemp implements Runnable {
	private int count;
	private double temp_all;
	private double avg_temp;
	private Functions func;
	private int port = 5678;

	public void run() {
		func = new Functions();
		try {
			if(func.findServer()){
				func.subscribe(1,port);
				while(true){
					StringBuilder receivedString = func.subscribtionReceive(port);
					String[] string = receivedString.toString().split(";");
					int data_type = Integer.parseInt(string[0]);
					double value = Double.parseDouble(string[1]);
					if(data_type == 1){
						count++;
						temp_all = temp_all + value;
						avg_temp = temp_all / count;
						System.out.println(avg_temp);
					}
					
				}
			}
		} catch (SocketTimeoutException e) {
			run();
			e.printStackTrace();
		}
	}

	public double getAvg_temp() {
		return avg_temp;
	}

}
