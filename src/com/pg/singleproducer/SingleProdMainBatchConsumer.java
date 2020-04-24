package com.pg.singleproducer;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.pg.consumer.PGEventBatchConsumer;
import com.pg.consumer.PGEventConsumer;
import com.pg.event.PGEvent;
import com.pg.publisher.PGEventPublisher;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SingleProdMainBatchConsumer {

    public static void main(String [] args) {
        Executor EXECUTOR = Executors.newSingleThreadExecutor();
        Disruptor<PGEvent> disruptor = new Disruptor<PGEvent>(PGEvent.EVENT_FACTORY, 1024, EXECUTOR, ProducerType.SINGLE, new SleepingWaitStrategy());
        RingBuffer<PGEvent> ringBuffer = disruptor.getRingBuffer();
        PGEventPublisher pgEventPublisher = new PGEventPublisher(ringBuffer);
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
        PGEventBatchConsumer pgEventBatchConsumer = new PGEventBatchConsumer(sequenceBarrier);
        disruptor.handleEventsWith(new BatchEventProcessor<PGEvent>(ringBuffer,sequenceBarrier,pgEventBatchConsumer));
        disruptor.start();
        pgEventPublisher.run();
    }

}
