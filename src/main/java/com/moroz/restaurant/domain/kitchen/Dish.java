package com.moroz.restaurant.domain.kitchen;

public enum Dish {
    Fish(25),
    Steak(30),
    Soup(15),
    Juice(5),
    Water(3);

    private int duration;

    Dish(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public static String allDishesToString() {
        StringBuilder stringBuilder = new StringBuilder("");

        for(Dish dish : Dish.values()) {
            stringBuilder.append(dish+", ");
        }

        return stringBuilder.replace(stringBuilder.length()-2,stringBuilder.length(),".").toString();
    }
}
