package com.pg.consumer;


import com.lmax.disruptor.WorkHandler;

public class PGEventWorkHandler1 implements WorkHandler {

    public void onEvent(Object event) throws Exception {
        System.out.println(" Inside PGEventWorkHandler1 : PGEvent : " + event);
    }
}
