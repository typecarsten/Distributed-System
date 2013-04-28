package middleware;

public class MidlewareServerMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
	Middleware mM = new Middleware();
	Thread t = new Thread(mM);
	Datagramconnection dC = new Datagramconnection();
	Thread t2 = new Thread(dC);
	t2.start();
	t.start();

	}

}
