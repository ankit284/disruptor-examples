package com.pg.example;


import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FooBarDisruptor {
    public static final int RING_SIZE=4;
    public static final ExecutorService EXECUTOR
            = Executors.newCachedThreadPool();
    final EventTranslator<FooBarEvent> eventTranslator
            =new EventTranslator<FooBarEvent>() {
        public void translateTo(FooBarEvent event,
                                long sequence) {
            double foo=event.getFoo();
            double bar=event.getBar();
            System.out.println("foo="+foo
                    +", bar="+bar
                    +" (sequence="+sequence+")");
        }
    };
    final EventHandler<FooBarEvent> fooHandler
            = new EventHandler<FooBarEvent>() {
        public void onEvent(final FooBarEvent event,
                            final long sequence,
                            final boolean endOfBatch)
                throws Exception {
            double foo=Math.random();
            event.setFoo(foo);
            System.out.println("setting foo to "+foo
                    +" (sequence="+sequence+")");
        }
    };
    final EventHandler<FooBarEvent> barHandler
            = new EventHandler<FooBarEvent>() {
        public void onEvent(final FooBarEvent event,
                            final long sequence,
                            final boolean endOfBatch)
                throws Exception {
            double bar=Math.random();
            event.setBar(bar);
            System.out.println("setting bar to "+bar
                    +" (sequence="+sequence+")");
        }
    };
    final EventHandler<FooBarEvent> fooBarHandler
            = new EventHandler<FooBarEvent>() {
        public void onEvent(final FooBarEvent event,
                            final long sequence,
                            final boolean endOfBatch)
                throws Exception {
            double foo=event.getFoo();
            double bar=event.getBar();
            System.out.println("foo="+foo
                    +", bar="+bar
                    +" (sequence="+sequence+")");
        }
    };
    public Disruptor setup() {
        Disruptor<FooBarEvent> disruptor =
                new Disruptor<FooBarEvent>(FooBarEvent.EVENT_FACTORY,
                        RING_SIZE,
                        EXECUTOR,
                        ProducerType.SINGLE,
                        new SleepingWaitStrategy());
        disruptor.handleEventsWith(fooHandler, barHandler).then(fooBarHandler);
        RingBuffer<FooBarEvent> ringBuffer = disruptor.start();
        return disruptor;
    }
    public void publish(Disruptor<FooBarEvent> disruptor) {
        for(int i=0;i<1000;i++) {
            disruptor.publishEvent(eventTranslator);
        }
    }
    public static void main(String[] args) {
        FooBarDisruptor fooBarDisruptor=new FooBarDisruptor();
        Disruptor disruptor=fooBarDisruptor.setup();
        fooBarDisruptor.publish(disruptor);
    }
}
