#SIP Protocol implementation

##Running
Start the server:

	java Main s

Start the client:

	java Main c

Paste the server port into the client program.

Paste the client port into the server program.

Enjoy echo chamber!

##UML

###Class diagram
![SIP class diagram](https://raw.github.com/pinne/sip_protocol/master/doc/SIP-class_diagram.png "UML class diagram for SIP application")

###Sequence diagram for handshake
![SIP handshake](https://raw.github.com/pinne/sip_protocol/master/doc/SIP-sequence_diagram.png "UML sequence diagram for SIP handshake")

##Protocol sketch

    state:  waiting    ringing      in session

    auto:              180 RINGING
                       200 OK
    
    event:  invite     ack          bye
    
    action: ringing    in session   ok
                                    waiting
