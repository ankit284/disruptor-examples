package com.pg.singleproducer;


import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.pg.consumer.PGEventConsumer;
import com.pg.event.PGEvent;
import com.pg.publisher.PGEventPublisher;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SingleProdMainMultiConsumer {

    public static void main(String [] args) {
        /**
         * Added multiple consumer by means of increasing the Executor thread pool.
         * Both the consumer will receive the event
         */
        Executor EXECUTOR = Executors.newFixedThreadPool(2);
        Disruptor<PGEvent> disruptor = new Disruptor<PGEvent>(PGEvent.EVENT_FACTORY, 1024, EXECUTOR, ProducerType.SINGLE, new SleepingWaitStrategy());
        RingBuffer<PGEvent> ringBuffer = disruptor.getRingBuffer();
        PGEventPublisher pgEventPublisher = new PGEventPublisher(ringBuffer);
        PGEventConsumer pgEventConsumer1 = new PGEventConsumer();
        PGEventConsumer pgEventConsumer2 = new PGEventConsumer();
        disruptor.handleEventsWith(pgEventConsumer1,pgEventConsumer2);
        disruptor.start();
        pgEventPublisher.run();
    }
}
