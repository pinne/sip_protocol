/**
 * Communication Systems, HI1032
 * Lab assignment 4B - Implementation of the SIP protocol
 *
 * Simon Kers          skers@kth.se
 * David Wikmans       wikmans@kth.se
 *                                 KTH STH 2012
 */

import java.io.*;
import java.util.Scanner;

public class Main {
	private static String INVITE = "INVITE thomas.lind@sth.kth.se anders.lindstrom@sth.kth.se 127.0.0.1 127.0.0.1 2566";

	public static void main(String[] args) throws IOException {
		StateContext sc = null;
		sc = new StateContext(null);
		Server server = null;

		System.out.println("SIP protocol implementation");
		System.out.println("USAGE: ");
		System.out.println("Server: java Main");
		System.out.println("Client: java Main INVITE receiver@email.com sender@email.com <toIP> <fromIP> <voicePort>");

		if (args.length == 0) { // Start server
			server = new Server(sc);
			new Thread(server).start();
			System.out.println("Server started");
		} else { // Start client
			Client client = new Client(sc, args);

			new Thread(client).start();
			System.out.println("Client started");
		}
	}
}
