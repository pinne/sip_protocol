import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.Scanner;

/**
 * Communication Systems, HI1032
 * Lab assignment 4B - Implementation of the SIP protocol
 *
 * Simon Kers          skers@kth.se
 * David Wikmans       wikmans@kth.se
 *                                 KTH STH 2012
 */

public class StateRinging implements State {
	AudioStreamUDP stream = null;

	public StateRinging(StateContext stateContext, String s[]) {
		System.out.print("STATE: ");
		System.out.println("Ringing");

		stateContext.send("RINGING");
		
		// parse the string and try to setup a connection
		try {
			if (registerCaller(s))
				stateContext.send("OK " + stream.getLocalPort());

		} catch (UnknownHostException uhe) {
			System.out.println("ERROR " + uhe.toString());
			stateContext.send("ERROR " + uhe.toString());
		}
	}

	/**
	 * Returns true upon successful connection to caller
	 * @throws UnknownHostException 
	 */
	private boolean registerCaller(String s[]) throws UnknownHostException {
		String receiverID       = s[1];
		String callerID         = s[2];
		InetAddress fromAddress = InetAddress.getByName(s[3]);
		InetAddress toAddress   = InetAddress.getByName(s[4]);
//		int voicePort           = null;
		int remotePort          = Integer.parseInt(s[5]);
		Scanner scan = new Scanner(System.in);

		try {
			// The AudioStream object will create a socket,
			// bound to a random port.
			stream = new AudioStreamUDP();
			int localPort = stream.getLocalPort();
			System.out.println("Bound to local port = " + localPort);

			// Set the address and port for the callee.
			System.out.println("What's the remote port number?");
//			String reply = scan.nextLine().trim();
//			remotePort = Integer.parseInt(reply);
			System.out.println("Remote: " + toAddress + ", " + remotePort);
			stream.connectTo(toAddress, remotePort);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public void parse(StateContext stateContext, String s) {
		if (s.equals("ACK")) {
			// provide InSession with information for setting up audio stream
			stateContext.setState(new StateInSession(stream, stateContext));
		} else if (s.startsWith("INVITE")) {
			stateContext.send("BUSY");
		} else {
			stateContext.send("ERROR");
		}
	}
}
