/**
 * Communication Systems, HI1032
 * Lab assignment 4B - Implementation of the SIP protocol
 *
 * Simon Kers          skers@kth.se
 * David Wikmans       wikmans@kth.se
 *                                 KTH STH 2012
 */

public class Waiting implements State {

	public Waiting() {
		System.out.print("STATE: ");
		System.out.println("Waiting");
	}
	
	public void next(StateContext stateContext) {
		stateContext.setState(new Ringing());
	}
	
	public void invite(StateContext stateContext, String s) {
		System.out.println("Received message: " + s);
		stateContext.send("100 TRYING");
		stateContext.send("180 RINGING");
		stateContext.send("200 OK");
		
		// Transition to state Ringing
		stateContext.setState(new Ringing());
	}

	public void ack(StateContext stateContext, String s) {
		System.out.println("Received message: " + s);
		stateContext.send("ERROR");
	}

	public void bye(StateContext stateContext, String s) {
		System.out.println("Received message: " + s);
		stateContext.send("ERROR");
	}
}
