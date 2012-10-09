import java.io.IOException;
import java.util.Scanner;

/**
 * Communication Systems, HI1032
 * Lab assignment 4B - Implementation of the SIP protocol
 *
 * Simon Kers          skers@kth.se
 * David Wikmans       wikmans@kth.se
 *                                 KTH STH 2012
 */

public class StreamThreadWrapper implements Runnable {
	public AudioStreamUDP stream = null;
	private StateContext state = null;
	
	public StreamThreadWrapper(StateContext state) {
		try {
			this.state = state;
			this.stream = new AudioStreamUDP();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		System.out.println("From thread StreamThreadWrapper");
		this.stream.startStreaming();
		
		System.out.println("Press ENTER to hang up");
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
		stop();
	}
	
	public void stop() {
		this.stream.stopStreaming();
		state.bye(state);
	}

}
