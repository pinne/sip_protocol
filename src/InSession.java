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
	}
}
