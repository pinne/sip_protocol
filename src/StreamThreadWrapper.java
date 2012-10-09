import java.io.IOException;

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
	
	public StreamThreadWrapper() {
		try {
			this.stream = new AudioStreamUDP();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		System.out.println("From thread StreamThreadWrapper");
		this.stream.startStreaming();
	}
	
	public void stop() {
		this.stream.stopStreaming();
	}

}
