/**
 * Communication Systems, HI1032
 * Lab assignment 4B - Implementation of the SIP protocol
 *
 * Simon Kers          skers@kth.se
 * David Wikmans       wikmans@kth.se
 *                                 KTH STH 2012
 */

public class Ringing implements State {
	
	public Ringing() {
		System.out.print("STATE: ");
		System.out.println("Ringing");
	}
	
	public void next(StateContext stateContext) {
		stateContext.setState(new InSession());
	}
	
	public void invite(StateContext stateContext, String s) {
		System.out.println("Received message: " + s);
		stateContext.send("BUSY");
	}

	public void ack(StateContext stateContext, String s) {
		System.out.println("Received message: " + s);
		// provide InSession with information for setting up audiostream
		stateContext.setState(new InSession());
	}

	public void bye(StateContext stateContext, String s) {
		System.out.println("Received message: " + s);
		stateContext.send("ERROR");
	}
}
