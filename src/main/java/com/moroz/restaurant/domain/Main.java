package com.moroz.restaurant.domain;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Restaurant restaurant = (Restaurant) context.getBean("restaurant");
        try {
            restaurant.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
