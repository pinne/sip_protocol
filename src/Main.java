/**
 * Communication Systems, HI1032
 * Lab assignment 4B - Implementation of the SIP protocol
 *
 * Simon Kers          skers@kth.se
 * David Wikmans       wikmans@kth.se
 *                                 KTH STH 2012
 */

public class Main {
	private static final String INVITE =
			"INVITE thomas.lind@sth.kth.se anders.lindstrom@sth.kth.se 130.10.30.100 130.10.30.101 2566";
	private static final String ACK = "ACK";
	private static final String BYE = "BYE";
	
	public static void main(String[] args) {
		// sc starts in state Waiting
		StateContext sc = new StateContext();

		// let's send some garbage
		sc.ack(sc, ACK);
		sc.bye(sc, BYE);
		
		// finally send the correct message
		sc.invite(sc, INVITE);
		
		// sc is now in state Calling
		// let's send some garbage
		sc.invite(sc, INVITE);
		sc.bye(sc, BYE);
		
		// finally send the correct message
		sc.ack(sc, ACK);
		
		// sc is now in state InSession
		// let's send some garbage
		sc.invite(sc, INVITE);
		sc.ack(sc, ACK);
		
		// finally send the correct message
		sc.bye(sc, BYE);
		
		// sc returns to state Waiting
	}
}
