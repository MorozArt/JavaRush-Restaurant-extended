package com.moroz.restaurant.domain.kitchen;

import com.moroz.restaurant.domain.ConsoleHelper;

import java.util.concurrent.LinkedBlockingQueue;

public class Waiter implements Runnable {

    private LinkedBlockingQueue<Order> queue;
    private String name;

    public Waiter(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        Order order = null;
        while (!Thread.interrupted()) {

            if(!queue.isEmpty()) {
                order = queue.poll();
            }

            if(order != null) {
                ConsoleHelper.writeMessage(order + " was cooked by " + order.getCook()+" and delivered by "+name);
                order = null;
            }
        }
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public String getName() {
        return name;
    }
}
