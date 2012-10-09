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
				servSock.setSoTimeout(1000);
				this.sock = servSock.accept();
				System.out.println("Connection from " + sock.getInetAddress());
				BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				
				out = new PrintWriter(sock.getOutputStream(), true);
				sc.setWriter(out);

				while (true) {
					// read message
					String message = in.readLine();
					if (message == null) {
						sc.setState(new StateWaiting());
						break;
					}
					
					System.out.println("RECEIVED: " + message);
					// parse message
					sc.parse(sc, message);

					if (sc.getState() instanceof StateWaiting)
						break;
				}
			} catch (SocketTimeoutException e) {
				System.out.printf(".");
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
