package Publishers;

import java.net.SocketTimeoutException;

import middleware.Functions;

public class TempSensor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Functions func = new Functions();
		run(func);
	}

	private static void run(Functions func) {
		try {
			if(func.findServer()){
				func.publish(1, func.generate_temp());
			}
		} catch (SocketTimeoutException e) {
			run(func);
			e.printStackTrace();
		}
	}

}
