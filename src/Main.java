/**
 * Communication Systems, HI1032
 * Lab assignment 4B - Implementation of the SIP protocol
 *
 * Simon Kers          skers@kth.se
 * David Wikmans       wikmans@kth.se
 *                                 KTH STH 2012
 */

import java.io.*;

public class Main {

	public static void main(String[] args) throws IOException {
		StateContext sc = null;
		sc = new StateContext(null);
		Server server = null;

		server = new Server(sc);
		System.out.println("Server started");
		new Thread(server).start();
	}
}
