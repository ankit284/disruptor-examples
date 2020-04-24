package com.pg.example;

import com.lmax.disruptor.EventFactory;

public class FooBarEvent {

    private double foo=0;
    private double bar=0;
    public double getFoo(){
        return foo;
    }
    public double getBar() {
        return bar;
    }
    public void setFoo(final double foo) {
        this.foo = foo;
    }
    public void setBar(final double bar) {
        this.bar = bar;
    }
    public final static EventFactory<FooBarEvent> EVENT_FACTORY
            = new EventFactory<FooBarEvent>() {
        public FooBarEvent newInstance() {
            return new FooBarEvent();
        }
    };

}
