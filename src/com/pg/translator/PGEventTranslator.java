package com.pg.translator;

import com.lmax.disruptor.EventTranslator;
import com.pg.event.PGEvent;

public class PGEventTranslator implements EventTranslator {

    public void translateTo(Object event, long sequence) {
        System.out.println(" Inside Translator PGEvent : " + event);
        ((PGEvent)event).setAddress("MADHAPUR");
        ((PGEvent)event).setName("ANKIT");
        ((PGEvent)event).setTelephoneNo(sequence);
    }
}
