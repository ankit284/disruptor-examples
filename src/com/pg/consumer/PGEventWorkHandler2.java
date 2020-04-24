package com.pg.consumer;

import com.lmax.disruptor.WorkHandler;

public class PGEventWorkHandler2 implements WorkHandler {

    public void onEvent(Object event) throws Exception {
        System.out.println(" Inside PGEventWorkHandler2 : PGEvent : " + event);
    }
}

