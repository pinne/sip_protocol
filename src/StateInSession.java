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
	public boolean inviter = false;

	public StateInSession(StreamThreadWrapper st, StateContext stateContext, boolean inviter) {
		System.out.print("STATE: ");
		System.out.println("inSession");
		System.out.println("Starting stream..");
		this.inviter = inviter;

		this.streamThread = st;
		new Thread(this.streamThread).start();
	}

	public void parse(StateContext stateContext, String s) {
		if (s.equals("BYE")) {
			stateContext.send("OK");
			// close connection
			streamThread.stop();
			if (inviter)
				System.exit(0);
			
		} else if (s.equals("INVITE")) {
			System.out.println("Received invite");
			stateContext.send("BUSY");
		} else {
			stateContext.send("BUSY");
		}
	}

	public void bye(StateContext sc) {
		sc.send("BYE");
		sc.setState(new StateWaiting());
		if (inviter) {
			System.exit(0);
		}
	}
}
