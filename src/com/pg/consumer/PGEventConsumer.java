package com.pg.consumer;


import com.lmax.disruptor.EventHandler;

public class PGEventConsumer implements EventHandler {

    public PGEventConsumer() {
    }

    public void onEvent(Object o, long l, boolean b) throws Exception {
        System.out.println(" Consumer : Sequence " + l + " Event : " + o);
    }
}
