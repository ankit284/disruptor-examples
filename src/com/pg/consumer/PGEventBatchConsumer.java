package com.pg.consumer;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.SequenceBarrier;
import com.pg.event.PGEvent;

public class PGEventBatchConsumer implements EventHandler <PGEvent> {

    private SequenceBarrier sequenceBarrier;
    public static long counter = 0L;

    public PGEventBatchConsumer(SequenceBarrier sequenceBarrier) {
        this.sequenceBarrier = sequenceBarrier;
    }

    public void onEvent(PGEvent event, long sequence, boolean endOfBatch) throws Exception {
        long wait = counter + 3;
        this.sequenceBarrier.waitFor(wait);
        System.out.println(" Inside Batch Event Consumer : PGEvent : " + event);
        counter = wait;
    }
}
