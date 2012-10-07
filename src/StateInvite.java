import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Communication Systems, HI1032
 * Lab assignment 4B - Implementation of the SIP protocol
 *
 * Simon Kers          skers@kth.se
 * David Wikmans       wikmans@kth.se
 *                                 KTH STH 2012
 */

public class StateInvite implements State {
	InetAddress dst = null;
	Socket sock = null;
	AudioStreamUDP stream = null;
	String header = null;
	
	public StateInvite(StateContext stateContext, String header) throws IOException {
		System.out.print("STATE: ");
		System.out.println("Invite");
		
		// Sending the INVITE string to server.
		stateContext.send(header);
		
		registerReceiver(header);
	}
	
	public void parse(StateContext stateContext, String s) {
		System.out.println("check");
		if (s.startsWith("OK") || s.startsWith("RING")) {
			stateContext.send("ACK");

			// Transition to state InSession
			stateContext.setState(new StateInSession((AudioStreamUDP) stream));
		} else if (s.startsWith("ERROR")) {
			return;
		} else {
			stateContext.send("ERROR");
		}
	}

	/**
	 * Returns true upon successful connection to receiver
	 * @throws UnknownHostException 
	 */
	private boolean registerReceiver(String header) throws UnknownHostException {
		String reply = null;
		Scanner scan = new Scanner(System.in);
		
		StringTokenizer st      = new StringTokenizer(header, "\n ");
		st.nextToken();
		String receiverID       = st.nextToken();
		String callerID         = st.nextToken();
		InetAddress fromAddress = InetAddress.getByName(st.nextToken());
		InetAddress toAddress   = InetAddress.getByName(st.nextToken());
		int voicePort           = Integer.parseInt(st.nextToken());

		try {
			// The AudioStream object will create a socket,
			// bound to a random port.
			stream = new AudioStreamUDP();
			int localPort = stream.getLocalPort();
			System.out.println("Bound to local port = " + localPort);
			
			// Set the address and port for the callee.
			System.out.println("What's the remote port number?");
			reply = scan.nextLine().trim();
			int remotePort = Integer.parseInt(reply);
			System.out.println(toAddress + ", " + remotePort);
			stream.connectTo(toAddress, remotePort);
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
}
