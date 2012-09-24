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
		stateContext.setState(new Calling());
	}

}
