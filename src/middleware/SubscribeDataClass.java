package middleware;

import java.net.InetAddress;

public class SubscribeDataClass {
	private InetAddress serverIp = null;
	private int port = 0;
	private int data_type = 0;
	
	public SubscribeDataClass(InetAddress serverIp, int port, int data_type){
		this.serverIp = serverIp;
		this.port = port;
		this.data_type = data_type;
	}

	public InetAddress getServerIp() {
		return serverIp;
	}

	public void setServerIp(InetAddress serverIp) {
		this.serverIp = serverIp;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getData_type() {
		return data_type;
	}

	public void setData_type(int data_type) {
		this.data_type = data_type;
	}
	
	
	

}
