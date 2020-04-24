package com.pg.publisher;

import com.lmax.disruptor.RingBuffer;
import com.pg.event.PGEvent;
import com.pg.translator.PGEventTranslator;

public class PGEventPublisherTranslator implements Runnable {

    private RingBuffer<PGEvent> ringBuffer;
    private PGEventTranslator pgEventTranslator;

    public PGEventPublisherTranslator(RingBuffer<PGEvent> ringBuffer, PGEventTranslator pgEventTranslator) {
        this.ringBuffer = ringBuffer;
        this.pgEventTranslator = pgEventTranslator;
    }

    public void run() {
        for(;;) {
            try {
                Thread.sleep(100);
                ringBuffer.publishEvent(pgEventTranslator);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
