import java.io.IOException;
import java.net.InetAddress;
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
	private StreamThreadWrapper streamThread;
	private InetAddress localAddress;
	private int localPort;
	private InetAddress remoteAddress;
	private int remotePort;
	
	public StateInvite(StateContext stateContext, String[] header) {
		System.out.print("STATE: ");
		System.out.println("Invite");
		
		try {
			// Starts the AudioStreamUDP and sets the localPort.
			registerReceiver(header);
			
			// Add the localPort to the INVITE String.
			stateContext.send(concatenateArray(header) + localPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void parse(StateContext stateContext, String s) {
		if (s.startsWith("RING")) {
			stateContext.send("ACK");
		} else if (s.startsWith("OK")) {
			// The port number is sent with the OK message.
			remotePort = getPortNumber(s);
			localPort = streamThread.stream.getLocalPort();
			System.out.println("Local: " + localAddress + ", " + localPort);
			System.out.println("Remote: " + remoteAddress + ", " + remotePort);
			
			try {
				streamThread.stream.connectTo(remoteAddress, remotePort);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Transition to state InSession
			stateContext.setState(new StateInSession(streamThread, stateContext));
		} else if (s.startsWith("ERROR")) {
			return;
		} else if (s.startsWith("BUSY")) {
			System.out.println("Server is busy");
			System.exit(0);
		} else {
			stateContext.send("ERROR");
		}
	}

	/**
	 * Returns true upon successful connection to receiver
	 * @throws UnknownHostException 
	 */
	private boolean registerReceiver(String[] header) throws UnknownHostException {
		try {
			String receiverID  = header[1];
			String callerID    = header[2];
			this.remoteAddress = InetAddress.getByName(header[3]);
			this.localAddress  = InetAddress.getByName(header[4]);

			// The AudioStream object will create a socket
			//this.stream = new AudioStreamUDP();
			this.streamThread = new StreamThreadWrapper();
			this.localPort = streamThread.stream.getLocalPort();
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	private int getPortNumber(String s) {
		StringTokenizer st = new StringTokenizer(s);
		st.nextToken();
		return Integer.parseInt(st.nextToken());
	}

	private String concatenateArray(String[] s) {
		String result = "";
		for (int i = 0; i < s.length; i += 1)
			result += s[i] + " ";

		return result;
	}
}
