package com.pg.publisher;

import com.lmax.disruptor.RingBuffer;
import com.pg.event.PGEvent;

public class PGEventPublisher implements Runnable {

    private RingBuffer<PGEvent> ringBuffer;

    public PGEventPublisher(RingBuffer<PGEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void run() {
        for(;;) {
            try {
                Thread.sleep(100);
                long currentSeq = ringBuffer.next();
                PGEvent pgEvent = ringBuffer.get(currentSeq);
                System.out.println(" Started Publishing Event for Sequence : " + currentSeq);
                pgEvent.setAddress("Madhapur");
                pgEvent.setName("Ankit");
                pgEvent.setTelephoneNo(currentSeq);
                ringBuffer.publish(currentSeq);
                System.out.println(" Completed Publishing Event for Sequence : " + currentSeq);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
