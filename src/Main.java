/**
 * Communication Systems, HI1032
 * Lab assignment 4B - Implementation of the SIP protocol
 *
 * Simon Kers          skers@kth.se
 * David Wikmans       wikmans@kth.se
 *                                 KTH STH 2012
 */

public class Main {
	public static void main(String[] args) {
		// sc starts in state Waiting
		StateContext sc = new StateContext();

		// let's send some garbage
		System.out.println("Sending ack");
		sc.ack(sc);
		
		System.out.println("Sending bye");
		sc.bye(sc);
		
		// finally send the correct message
		System.out.println("Sending invite");
		sc.invite(sc);
		
		// sc is now in state Calling
		// let's send some garbage
		System.out.println("Sending invite");
		sc.invite(sc);
		
		System.out.println("Sending bye");
		sc.bye(sc);
		
		// finally send the correct message
		System.out.println("Sending ack");
		sc.ack(sc);
		
		// sc is now in state InSession
		// let's send some garbage
		System.out.println("Sending invite");
		sc.invite(sc);
		
		System.out.println("Sending ack");
		sc.ack(sc);
		
		// finally send the correct message
		System.out.println("Sending bye");
		sc.bye(sc);
		
		// sc returns to state Waiting
	}
}
