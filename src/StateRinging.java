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
	StreamThreadWrapper streamThread = null;
	StateContext stateContext = null;
	int localPort;

	public StateRinging(StateContext stateContext, String s[]) {
		System.out.print("STATE: ");
		System.out.println("Ringing");

		stateContext.send("RINGING");
		this.stateContext = stateContext;
		
		// parse the string and try to setup a connection
		try {
			if (registerCaller(s, stateContext))
				stateContext.send("OK " + localPort);

		} catch (UnknownHostException uhe) {
			System.out.println("ERROR " + uhe.toString());
			stateContext.send("ERROR " + uhe.toString());
		}
	}

	/**
	 * Returns true upon successful connection to caller
	 * @throws UnknownHostException 
	 */
	private boolean registerCaller(String s[], StateContext stateContext) throws UnknownHostException {
		String receiverID         = s[1];
		String callerID           = s[2];
		InetAddress localAddress  = InetAddress.getByName(s[3]);
		InetAddress remoteAddress = InetAddress.getByName(s[4]);
		int remotePort            = Integer.parseInt(s[5]);
		Scanner scan = new Scanner(System.in);

		try {
			// The AudioStream object will create a socket,
			// bound to a random port.
			//this.stream = new AudioStreamUDP();
			this.streamThread = new StreamThreadWrapper(stateContext);
			this.localPort = this.streamThread.stream.getLocalPort();
			//int localPort = Integer.parseInt(s[5]);
			
			// Set the address and port for the callee.
			System.out.println("Local: " + localAddress + ", " + localPort);
			System.out.println("Remote: " + remoteAddress + ", " + remotePort);
			streamThread.stream.connectTo(remoteAddress, remotePort);
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public void parse(StateContext stateContext, String s) {
		if (s.equals("ACK")) {
			// provide InSession with information for setting up audio stream
			stateContext.setState(new StateInSession(this.streamThread, stateContext, false));
		} else if (s.startsWith("INVITE")) {
			stateContext.send("BUSY");
		} else {
			stateContext.send("ERROR");
		}
	}
	
	public void stop(StateContext sc) {
		;
	}
}
