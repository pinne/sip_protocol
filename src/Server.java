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

public class Server implements Runnable {
	private String INVITE = "INVITE thomas.lind@sth.kth.se anders.lindstrom@sth.kth.se 127.0.0.1 127.0.0.1 2566";
	private static final int SERVER_PORT = 5060;
	private ServerSocket servSock = null;
	private Socket sock = null;
	private StateContext sc = null;
	private PrintWriter out = null;
	
	public Server(StateContext sc) {
		this.sc = sc;
	}
	
	public Socket getSocket() {
		return sock;
	}

	public void run() {

		while (true) {
			try {
				// Get a stream for sending messages to client 
				servSock = new ServerSocket(SERVER_PORT);
				System.out.println("Waiting for connection");
				this.sock = servSock.accept();
				System.out.println("Connection from " + sock.getInetAddress());
				BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				
				out = new PrintWriter(sock.getOutputStream(), true);
				sc.setWriter(out);

				while (true) {
					// read message
					String message = in.readLine();
					// parse message
					sc.parse(sc, message);

					if (sc.getState() instanceof StateWaiting)
						break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (servSock != null)
						servSock.close();
				} catch (Exception e) {
				}
			}
		}
	}

}
