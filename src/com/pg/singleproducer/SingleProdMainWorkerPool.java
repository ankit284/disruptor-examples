package com.pg.singleproducer;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.pg.consumer.PGEventWorkHandler1;
import com.pg.consumer.PGEventWorkHandler2;
import com.pg.event.PGEvent;
import com.pg.publisher.PGEventPublisher;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SingleProdMainWorkerPool {

    public static void main(String [] args) {
        Executor EXECUTOR = Executors.newFixedThreadPool(2);
        Disruptor<PGEvent> disruptor = new Disruptor<PGEvent>(PGEvent.EVENT_FACTORY, 1024, EXECUTOR, ProducerType.SINGLE, new SleepingWaitStrategy());
        RingBuffer<PGEvent> ringBuffer = disruptor.getRingBuffer();
        PGEventPublisher pgEventPublisher = new PGEventPublisher(ringBuffer);
        disruptor.handleEventsWithWorkerPool(new PGEventWorkHandler1(), new PGEventWorkHandler2());
        disruptor.start();
        pgEventPublisher.run();
    }
}
