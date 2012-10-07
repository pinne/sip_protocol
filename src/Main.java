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
		
		int ans = -1;
		if (args.length < 1) {
			System.out.println("Press 1 to start a server");
			System.out.println("Press 2 to start a client");
			System.out.println("");

			Scanner scan = new Scanner(System.in);
			ans = scan.nextInt();
		}
		
		if (args.length > 0 && args[0].startsWith("s"))
			ans = 1;
		else if (args.length > 0)
			ans = 2;
			
		
		if (ans == 1) { // Start server
			server = new Server(sc);
			System.out.println("Server started");
			
			new Thread(server).start();
		} else if (ans == 2) { // Start client
			Client client = new Client(sc);
			
			new Thread(client).start();
			System.out.println("Client started");
		}
	}
}
