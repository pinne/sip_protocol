/**
 * Communication Systems, HI1032
 * Lab assignment 4B - Implementation of the SIP protocol
 *
 * Simon Kers          skers@kth.se
 * David Wikmans       wikmans@kth.se
 *                                 KTH STH 2012
 */

import java.util.Arrays;
import java.util.StringTokenizer;

import com.sun.xml.internal.fastinfoset.util.StringArray;

public class StateWaiting implements State {

	public StateWaiting() {
		System.out.print("STATE: ");
		System.out.println("Waiting");
	}
	
	public void parse(StateContext stateContext, String s) {
		String[] args;
		args = s.split(" ");
		
		if (s.startsWith("INVITE") && args.length >= 6) {
			// We do not need TRYING when we are without proxy.
			//			stateContext.send("TRYING");

			// Transition to state Ringing
			stateContext.setState(new StateRinging(stateContext, args));
		} else {
			stateContext.send("ERROR");
			System.exit(-1);
		}
	}

	private String[] toTokens(String s) {
		return s.split("\\s");
	}
}
