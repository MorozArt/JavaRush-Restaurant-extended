package com.moroz.restaurant.domain;

import com.moroz.restaurant.domain.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class ConsoleHelper {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return reader.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> result = new LinkedList<>();

        writeMessage(Dish.allDishesToString());
        writeMessage("Введите название блюда или exit для завершения заказа.");

        while (true) {
            String str = readString();

            if(str.equals("exit")) {
                break;
            }

            try {
                result.add(Dish.valueOf(str));
            } catch (IllegalArgumentException e) {
                writeMessage("Такого блюда нет!");
            }

        }

        return result;
    }
}
