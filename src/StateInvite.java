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
	String header = null;
	
	AudioStreamUDP stream = null;
	InetAddress toAddress;
	int remotePort = -1;
	private InetAddress fromAddress;
	
	public StateInvite(StateContext stateContext, String[] header) throws IOException {
		System.out.print("STATE: ");
		System.out.println("Invite");
		
		String concatenated = concatenateArray(header);
		// Sending the INVITE string to server.
		stateContext.send(concatenated);
		registerReceiver(concatenated);
	}
	
	public void parse(StateContext stateContext, String s) {
		if (s.startsWith("RING")) {
			stateContext.send("ACK");
		} else if (s.startsWith("OK")) {
			StringTokenizer st = new StringTokenizer(s);
			st.nextToken();
			this.remotePort = Integer.parseInt(st.nextToken());
			System.out.println("Changing remotePort to: " + remotePort);
			
			try {
				stream.connectTo(fromAddress, remotePort);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Transition to state InSession
			stateContext.setState(new StateInSession((AudioStreamUDP) stream, stateContext));
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
		this.toAddress          = InetAddress.getByName(st.nextToken());
		int voicePort           = Integer.parseInt(st.nextToken());

		try {
			// The AudioStream object will create a socket,
			// bound to a random port.
			stream = new AudioStreamUDP();
			this.remotePort = voicePort;
			this.fromAddress = fromAddress;
			
			// Set the address and port for the callee.
			System.out.println("What's the remote port number?");
			System.out.println("Using: " + voicePort);
			System.out.println(toAddress + ", " + remotePort);
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	private String concatenateArray(String[] s) {
		String result = "";
		for (int i = 0; i < 6; i += 1)
			result += s[i] + " ";
		
		return result;
	}
}
