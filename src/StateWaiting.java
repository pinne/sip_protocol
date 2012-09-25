/**
 * Communication Systems, HI1032
 * Lab assignment 4B - Implementation of the SIP protocol
 *
 * Simon Kers          skers@kth.se
 * David Wikmans       wikmans@kth.se
 *                                 KTH STH 2012
 */

import java.util.StringTokenizer;

public class StateWaiting implements State {

	public StateWaiting() {
		System.out.print("STATE: ");
		System.out.println("Waiting");
	}
	
	public void parse(StateContext stateContext, String s) {
		System.out.println("Parsing message: " + s);
		
		StringTokenizer st = new StringTokenizer(s);
		
		if (s.startsWith("INVITE") && st.countTokens() >= 6) {
			stateContext.send("TRYING");

			// Transition to state Ringing
			stateContext.setState(new StateRinging(stateContext, s));
		} else {
			stateContext.send("ERROR");
		}
	}
}
