/**
 * Communication Systems, HI1032
 * Lab assignment 4B - Implementation of the SIP protocol
 *
 * Simon Kers          skers@kth.se
 * David Wikmans       wikmans@kth.se
 *                                 KTH STH 2012
 */

interface State {
	void next(StateContext stateContext);
	void invite(StateContext stateContext);
	void ack(StateContext stateContext);
	void bye(StateContext stateContext);
}
