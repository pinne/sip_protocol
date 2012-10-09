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
import java.sql.Array;

public class Client implements Runnable {
	private static final int SERVER_PORT = 5060;
	private Socket sock = null;
	private StateContext stateContext = null;
	private PrintWriter out = null;
	private BufferedReader in;
	
	public Client(StateContext sc, String args[]) throws IOException {
		System.out.println("Making connection");
		this.stateContext = sc;
		// Get a stream for sending messages to server 
		InetAddress iaddr = InetAddress.getByName(args[3]);
		sock = new Socket(iaddr, SERVER_PORT);
		in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

		out = new PrintWriter(sock.getOutputStream(), true);
		stateContext.setWriter(out);
		
		AudioStreamUDP stream = new AudioStreamUDP();
		String[] header = new String[args.length+1];
		System.arraycopy(args, 0, header, 0, args.length);
		header[header.length-1] = Integer.toString(stream.getLocalPort());
		System.out.println(header[5]);
		// Finally change the state to Invite
		sc.setState(new StateInvite(sc, header, stream));
	}
	
	public void run() {

		try {
			System.out.println("Running thread");
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
	
	public Socket getSocket() {
		return sock;
	}
}
