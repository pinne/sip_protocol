#SIP Protocol implementation

##Running
Start the server:

	java Main s

Start the client:

	java Main c

Paste the server port into the client program.

Paste the client port into the server program.

Enjoy echo chamber!

##Protocol sketch

    state:  waiting    ringing      in session

    auto:              180 RINGING
                       200 OK
    
    event:  invite     ack          bye
    
    action: ringing    in session   ok
                                    waiting
