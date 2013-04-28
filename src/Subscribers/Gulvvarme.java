package Subscribers;

import middleware.Functions;

public class Gulvvarme {
	
	public static void main(String[] args) {
		Functions func = new Functions();
		if(func.findServer()){
			func.subscribe();
			while(true){
			func.subscribtionReceive();
			}
		}
	}

}
