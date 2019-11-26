package com.moroz.restaurant.domain;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomOrderGeneratorTask implements Runnable {

    private List<Tablet> tablets;
    private final int orderCreatingInterval;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int orderCreatingInterval) {
        this.tablets = tablets;
        this.orderCreatingInterval = orderCreatingInterval;
    }

    @Override
    public void run() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        try {
            tablets.get(random.nextInt(tablets.size())).createTestOrder();
            Thread.sleep(orderCreatingInterval);
        } catch (InterruptedException ignore) {
        }
    }
}

