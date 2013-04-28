package Publishers;

import middleware.Functions;

public class TempSensor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Functions func = new Functions();
		if(func.findServer()){
			func.publish(1, func.generate_temp());
		}		
	}

}
