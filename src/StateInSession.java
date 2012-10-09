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
			// close connection
			this.stop(stateContext);
		} else if (s.equals("INVITE")) {
			System.out.println("Received invite");
			stateContext.send("BUSY");
		} else {
			stateContext.send("BUSY");
		}
	}

	public void stop(StateContext sc) {
		sc.setState(new StateWaiting());
		if (inviter) {
			sc.send("OK");
			System.exit(0);
		}
	}
}
