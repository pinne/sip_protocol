import java.util.Scanner;

/**
 * Communication Systems, HI1032
 * Lab assignment 4B - Implementation of the SIP protocol
 *
 * Simon Kers          skers@kth.se
 * David Wikmans       wikmans@kth.se
 *                                 KTH STH 2012
 */

public class StateInSession implements State {
	StreamThreadWrapper streamThread = null;

	public StateInSession(StreamThreadWrapper st, StateContext stateContext) {
		System.out.print("STATE: ");
		System.out.println("inSession");
		System.out.println("Starting stream..");
		
		this.streamThread = st;
		new Thread(this.streamThread).start();
		System.out.println("Stream started in thread");
		
		// User presses enter to end stream.
		Scanner scan = new Scanner(System.in);
//		System.out.println("Press ENTER to hang up..");
//		scan.nextLine();
		
//		stateContext.send("BYE");
//		this.stream.stopStreaming();
	}

	public void parse(StateContext stateContext, String s) {
		if (s.equals("BYE")) {
			stateContext.send("OK");
			// close connection
			//this.stream.stopStreaming();
			this.streamThread.stop();
			
			stateContext.setState(new StateWaiting());
		} else if (s.equals("INVITE")) {
			System.out.println("Received invite");
			stateContext.send("BUSY");
		} else {
			stateContext.send("BUSY");
			
			// For debugging purposes only.
//			System.exit(-1);
		}
	}
}
