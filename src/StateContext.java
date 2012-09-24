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
	
	public StateContext() {
		setState(new Waiting());
	}
	
	public void setState(State s) {
		this.myState = s;
	}

	public void next() {
		this.myState.next(this);
	}

	public void invite(StateContext stateContext) {
		this.myState.invite(stateContext);
	}

	public void ack(StateContext stateContext) {
		this.myState.ack(stateContext);
	}

	public void bye(StateContext stateContext) {
		this.myState.bye(stateContext);
	}
}
