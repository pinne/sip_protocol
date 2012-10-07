/**
 * Communication Systems, HI1032
 * Lab assignment 4B - Implementation of the SIP protocol
 *
 * Simon Kers          skers@kth.se
 * David Wikmans       wikmans@kth.se
 *                                 KTH STH 2012
 */

import java.io.*;
import java.net.*;

public class Client implements Runnable {
	private String INVITE = "INVITE thomas.lind@sth.kth.se anders.lindstrom@sth.kth.se 127.0.0.1 127.0.0.1 2566";
	private static final int SERVER_PORT = 5060;
	private Socket sock = null;
	private StateContext stateContext = null;
	private PrintWriter out = null;
	private BufferedReader in;

	public Client(StateContext sc) throws IOException {
		this.stateContext = sc;
		// Get a stream for sending messages to server 
		InetAddress iaddr = InetAddress.getByName("localhost");
		sock = new Socket(iaddr, SERVER_PORT);
		System.out.println("Making connection");
		in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

		out = new PrintWriter(sock.getOutputStream(), true);
		stateContext.setWriter(out);
		sc.setState(new StateInvite(sc, INVITE));
	}

	public Socket getSocket() {
		return sock;
	}

	public void run() {

		try {
			while (true) {
				String message;
				// read message
				message = in.readLine();
				System.out.println("RECEIVED: " + message);
				// parse message
				stateContext.parse(stateContext, message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (sock != null)
					sock.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
