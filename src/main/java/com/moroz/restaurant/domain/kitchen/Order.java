package com.moroz.restaurant.domain.kitchen;

import com.moroz.restaurant.domain.ConsoleHelper;
import com.moroz.restaurant.domain.Tablet;

import java.io.IOException;
import java.util.List;

public class Order {

    private final Tablet tablet;
    private Cook cook;
    protected List<Dish> dishes;

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        initDishes();
    }

    public int getTotalCookingTime() {
        int totalCookingTime = 0;
        for(Dish dish : dishes) {
            totalCookingTime += dish.getDuration();
        }
        return totalCookingTime;
    }

    public boolean isEmpty() {
        return dishes.isEmpty();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public Tablet getTablet() {
        return tablet;
    }

    public Cook getCook() {
        return cook;
    }

    public void setCook(Cook cook) {
        this.cook = cook;
    }

    protected void initDishes() throws IOException {
        dishes = ConsoleHelper.getAllDishesForOrder();
    }

    @Override
    public String toString() {
        if(dishes.isEmpty()) {
            return "";
        } else {
            return String.format("Order: %s of %s", dishes, tablet);
        }
    }
}
