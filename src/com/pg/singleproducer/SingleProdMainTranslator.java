package com.pg.singleproducer;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.pg.consumer.PGEventConsumer;
import com.pg.event.PGEvent;
import com.pg.publisher.PGEventPublisherTranslator;
import com.pg.translator.PGEventTranslator;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SingleProdMainTranslator {

    public static void main(String [] args) {
        Executor EXECUTOR = Executors.newSingleThreadExecutor();
        Disruptor<PGEvent> disruptor = new Disruptor<PGEvent>(PGEvent.EVENT_FACTORY, 1024, EXECUTOR, ProducerType.SINGLE, new SleepingWaitStrategy());
        RingBuffer<PGEvent> ringBuffer = disruptor.getRingBuffer();
        PGEventPublisherTranslator pgEventPublisherTranslator = new PGEventPublisherTranslator(ringBuffer, new PGEventTranslator());
        PGEventConsumer pgEventConsumer = new PGEventConsumer();
        disruptor.handleEventsWith(pgEventConsumer);
        disruptor.start();
        pgEventPublisherTranslator.run();
    }
}
