package com.moroz.restaurant.domain.kitchen;

import com.moroz.restaurant.domain.Tablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class TestOrder extends Order {

    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected void initDishes() {
        dishes = new ArrayList<>();

        ThreadLocalRandom random = ThreadLocalRandom.current();
        int n = random.nextInt(4)+1;
        Dish[] values = Dish.values();
        for(int i=0;i<n;++i) {
            dishes.add(values[random.nextInt(values.length)]);
        }
    }
}
