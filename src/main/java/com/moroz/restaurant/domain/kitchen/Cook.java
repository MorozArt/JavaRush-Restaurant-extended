package com.moroz.restaurant.domain.kitchen;

import com.moroz.restaurant.domain.ConsoleHelper;
import com.moroz.restaurant.domain.statistic.StatisticManager;
import com.moroz.restaurant.domain.statistic.event.CookedOrderEventDataRow;

import java.util.concurrent.LinkedBlockingQueue;

public class Cook implements Runnable {

    private String name;
    private LinkedBlockingQueue<Order> orderQueue;
    private LinkedBlockingQueue<Order> readyOrderQueue;

    public Cook(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        Order order = null;
        while (!Thread.interrupted()) {

            if(!orderQueue.isEmpty()) {
                order = orderQueue.poll();
            }

            if(order != null) {
                startCookingOrder(order);
                order = null;
            }
        }
    }

    public void startCookingOrder(Order order) {
        ConsoleHelper.writeMessage("Start cooking - " + order +", cooking time "+order.getTotalCookingTime()+"min");
        order.setCook(this);

        StatisticManager.getInstance().register(new CookedOrderEventDataRow(order.getTablet().toString(),
                name, order.getTotalCookingTime(), order.getDishes()));

        try {
            Thread.sleep(order.getTotalCookingTime()*10);
        } catch (InterruptedException ignore) {}

        readyOrderQueue.add(order);
    }

    public void setOrderQueue(LinkedBlockingQueue<Order> orderQueue) {
        this.orderQueue = orderQueue;
    }

    public void setReadyOrderQueue(LinkedBlockingQueue<Order> readyOrderQueue) {
        this.readyOrderQueue = readyOrderQueue;
    }

    @Override
    public String toString() {
        return name;
    }
}
