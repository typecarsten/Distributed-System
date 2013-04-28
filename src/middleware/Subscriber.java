package middleware;

import java.net.InetAddress;

public class Subscriber {
	private InetAddress clientIp = null;
	
	public Subscriber(InetAddress clientIp){
		this.clientIp = clientIp;
		
	}

}
