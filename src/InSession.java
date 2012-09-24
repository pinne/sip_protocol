/**
 * Communication Systems, HI1032
 * Lab assignment 4B - Implementation of the SIP protocol
 *
 * Simon Kers          skers@kth.se
 * David Wikmans       wikmans@kth.se
 *                                 KTH STH 2012
 */

public class InSession implements State {

	public InSession() {
		System.out.print("STATE: ");
		System.out.println("inSession");
	}
	
	public void next(StateContext stateContext) {
		stateContext.setState(new Waiting());
	}
	
	public void invite(StateContext stateContext, String s) {
		System.out.println("Received message: " + s);
		stateContext.send("BUSY");
	}

	public void ack(StateContext stateContext, String s) {
		System.out.println("Received message: " + s);
		stateContext.send("ERROR");
	}

	public void bye(StateContext stateContext, String s) {
		System.out.println("Received message: " + s);
		stateContext.send("OK");
		// close connection
		stateContext.setState(new Waiting());
	}
}
