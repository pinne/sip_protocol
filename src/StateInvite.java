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
	private String header = null;
	
	private AudioStreamUDP stream;
	private InetAddress localAddress;
	private int localPort;
	private InetAddress remoteAddress;
	private int remotePort;
	
	public StateInvite(StateContext stateContext, String[] header, AudioStreamUDP stream) {
		System.out.print("STATE: ");
		System.out.println("Invite");
		this.localPort = Integer.parseInt(header[5]);
		
		this.stream = stream;
		String concatenated = concatenateArray(header);
		// Sending the INVITE string to server.
		stateContext.send(concatenated);
		try {
			registerReceiver(concatenated);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void parse(StateContext stateContext, String s) {
		if (s.startsWith("RING")) {
			stateContext.send("ACK");
		} else if (s.startsWith("OK")) {
			StringTokenizer st = new StringTokenizer(s);
			st.nextToken();
			localPort = stream.getLocalPort();
			remotePort = Integer.parseInt(st.nextToken());
			System.out.println("Local: " + localAddress + ", " + localPort);
			System.out.println("Remote: " + remoteAddress + ", " + remotePort);
			
			try {
				stream.connectTo(remoteAddress, remotePort);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Transition to state InSession
			stateContext.setState(new StateInSession(stream, stateContext));
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
		
		try {
			StringTokenizer st = new StringTokenizer(header, "\n ");
			st.nextToken();
			String receiverID  = st.nextToken();
			String callerID    = st.nextToken();
			this.remoteAddress = InetAddress.getByName(st.nextToken());
			this.localAddress  = InetAddress.getByName(st.nextToken());
//			this.localPort     = Integer.parseInt(st.nextToken());

			// The AudioStream object will create a socket
//			this.stream = new AudioStreamUDP();
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	private String concatenateArray(String[] s) {
		String result = "";
		for (int i = 0; i < s.length; i += 1)
			result += s[i] + " ";
		
		return result;
	}
}
