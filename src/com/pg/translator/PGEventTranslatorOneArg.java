package com.pg.translator;

import com.lmax.disruptor.EventTranslatorOneArg;

public class PGEventTranslatorOneArg implements EventTranslatorOneArg {

    public void translateTo(Object o, long l, Object o1) {
        o = o1;
        System.out.println(" Inside Translator : " + o);
    }
}
