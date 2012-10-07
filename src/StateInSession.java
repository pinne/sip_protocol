/**
 * Communication Systems, HI1032
 * Lab assignment 4B - Implementation of the SIP protocol
 *
 * Simon Kers          skers@kth.se
 * David Wikmans       wikmans@kth.se
 *                                 KTH STH 2012
 */

public class StateInSession implements State {
	AudioStreamUDP stream = null;

	public StateInSession(AudioStreamUDP stream) {
		System.out.print("STATE: ");
		System.out.println("inSession");
		this.stream = stream;
		System.out.println("Starting stream..");
		this.stream.startStreaming();
	}

	public void parse(StateContext stateContext, String s) {
		if (s.equals("BYE")) {
			stateContext.send("OK");
			// close connection
			this.stream.stopStreaming();
			stateContext.setState(new StateWaiting());
		} else if (s.equals("OK")) {
			System.out.println("Ready to stream");
		} else if (s.equals("INVITE")) {
			stateContext.send("BUSY");
		} else {
			stateContext.send("ERROR");
			System.out.println("Error in StateInSession");
			System.exit(-1);
		}
	}
}
