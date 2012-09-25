#Protocol sketch

    state:  waiting    ringing      in session

    auto:              180 RINGING
                       200 OK
    
    event:  invite     ack          bye
    
    action: ringing    in session   ok
                                    waiting
