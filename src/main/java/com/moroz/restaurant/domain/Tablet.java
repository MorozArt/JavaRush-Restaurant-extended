package com.moroz.restaurant.domain;

import com.moroz.restaurant.domain.ad.AdvertisementManager;
import com.moroz.restaurant.domain.ad.NoVideoAvailableException;
import com.moroz.restaurant.domain.kitchen.Order;
import com.moroz.restaurant.domain.kitchen.TestOrder;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet {

    public static Logger logger = Logger.getLogger(Tablet.class.getName());
    private LinkedBlockingQueue<Order> queue;
    public final int number;

    public Tablet(int number) {
        this.number = number;
    }

    public Order createOrder() {
        try {
            Order order = new Order(this);
            processOrder(order);
            return order;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }

        return null;
    }

    public void createTestOrder() {
        try {
            Order order = new TestOrder(this);
            processOrder(order);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Wait WHAT?!");
        }
    }

    private void processOrder(Order order) {
        ConsoleHelper.writeMessage(order.toString());
        if(!order.isEmpty()) {
            queue.add(order);

            try {
                new AdvertisementManager(order.getTotalCookingTime()*60).processVideos();
            } catch (NoVideoAvailableException e) {
                logger.log(Level.INFO, "No video is available for the order "+order);
            }
        }
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "number=" + number +
                '}';
    }

}