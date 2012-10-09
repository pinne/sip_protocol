import java.io.PrintWriter;
import java.sql.Timestamp;

/**
 * Communication Systems, HI1032
 * Lab assignment 4B - Implementation of the SIP protocol
 *
 * Simon Kers          skers@kth.se
 * David Wikmans       wikmans@kth.se
 *                                 KTH STH 2012
 */

public class StateContext {
	private State myState;
	private PrintWriter pw = null;
	
	public StateContext(String s) {
		setState(new StateWaiting());
	}
	
	public void setState(State s) {
		this.myState = s;
	}
	
	public void setWriter(PrintWriter pw) {
		this.pw = pw;
	}
	
	public State getState() {
		return this.myState;
	}

	public void send(String string) {
		System.out.println("SENDING: " + string);
		pw.println(string);
	}

	public void parse(StateContext sc, String s) {
		this.myState.parse(sc, s);
	}

	public void bye(StateContext sc) {
		this.myState.bye(sc);
	}
}
