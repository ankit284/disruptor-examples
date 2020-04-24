package com.pg.concurrency;

public class SimpleWait {

    public static void main(String args[]) {
        Object object = new Object();

        synchronized (object) {
            try {
                System.out.println("Before Wait");
                object.wait();
                System.out.println("After Wait");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
