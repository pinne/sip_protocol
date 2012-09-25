import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
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
	
	public StateInvite(StateContext stateContext, String header) throws IOException {
		System.out.print("STATE: ");
		System.out.println("Invite");
//		registerReceiver(header);
	}
	
	public void parse(StateContext stateContext, String s) {
		System.out.println("Parsing message: " + s);
		
		if (s.startsWith("OK") || s.startsWith("RING")) {
			stateContext.send("ACK");

			// Transition to state InSession
			stateContext.setState(new StateInSession((AudioStreamUDP) stream));
		} else {
			stateContext.send("ERROR");
		}
	}

	/**
	 * Returns true upon successful connection to receiver
	 * @throws UnknownHostException 
	 */
	private boolean registerReceiver(String header) throws UnknownHostException {
		StringTokenizer st      = new StringTokenizer(header, "\n ");
		st.nextToken();
		String receiverID       = st.nextToken();
		String callerID         = st.nextToken();
		InetAddress fromAddress = InetAddress.getByName(st.nextToken());
		InetAddress toAddress   = InetAddress.getByName(st.nextToken());
		int remotePort          = Integer.parseInt(st.nextToken());
		int localPort           = 5060;

		try {
			// The AudioStream object will create a socket,
			// bound to a random port.
			stream = new AudioStreamUDP();
			//int localPort = stream.getLocalPort();
			System.out.println("Bound to local port = " + localPort);

			System.out.println("Making call to " + toAddress + ", " + remotePort);
			stream.connectTo(fromAddress, localPort);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
}
