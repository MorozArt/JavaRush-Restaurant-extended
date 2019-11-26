package com.moroz.restaurant.domain;

import com.moroz.restaurant.dao.CookDao;
import com.moroz.restaurant.domain.kitchen.Cook;
import com.moroz.restaurant.domain.kitchen.Order;
import com.moroz.restaurant.domain.kitchen.Waiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class Restaurant {

    private static final int ORDER_CREATING_INTERVAL = 100;
    private static final LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();
    private static final LinkedBlockingQueue<Order> readyOrderQueue = new LinkedBlockingQueue<>();

    @Autowired
    private CookDao cookDao;

    public void start() throws InterruptedException {

        /*Cook amigoCook = new Cook("Amigo Chief");
        amigoCook.setOrderQueue(orderQueue);
        amigoCook.setReadyOrderQueue(readyOrderQueue);
        Thread amigoCookThread = new Thread(amigoCook, "Amigo Chief");
        amigoCookThread.setDaemon(true);
        amigoCookThread.start();

        Cook notAmigoCook = new Cook("NotAmigo Chief");
        notAmigoCook.setOrderQueue(orderQueue);
        notAmigoCook.setReadyOrderQueue(readyOrderQueue);
        Thread notAmigoCookThread = new Thread(notAmigoCook, "NotAmigo Chief");
        notAmigoCookThread.setDaemon(true);
        notAmigoCookThread.start();*/

        for(Cook cook : cookDao.getCooks()) {
            cook.setOrderQueue(orderQueue);
            cook.setReadyOrderQueue(readyOrderQueue);
            Thread cookThread = new Thread(cook, cook+" Chief");
            cookThread.setDaemon(true);
            cookThread.start();
        }

        Waiter waiterOne = new Waiter("Waiter One");
        waiterOne.setQueue(readyOrderQueue);
        Thread waiterOneThread = new Thread(waiterOne, "Waiter One");
        waiterOneThread.setDaemon(true);
        waiterOneThread.start();

        Waiter waiterTwo = new Waiter("Waiter Two");
        waiterTwo.setQueue(readyOrderQueue);
        Thread waiterTwoThread = new Thread(waiterTwo, "Waiter Two");
        waiterTwoThread.setDaemon(true);
        waiterTwoThread.start();


        List<Tablet> tablets = new ArrayList<>();
        for (int i=1;i<=5;++i) {
            Tablet tablet = new Tablet(i);
            tablet.setQueue(orderQueue);
            tablets.add(tablet);
        }

        Thread randomOrderGeneratorTask = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
        randomOrderGeneratorTask.start();
        Thread.sleep(1000);
        randomOrderGeneratorTask.interrupt();


//        DirectorTablet directorTablet = new DirectorTablet();
//        directorTablet.printAdvertisementProfit();
//        directorTablet.printCookWorkloading();
//        directorTablet.printActiveVideoSet();
//        directorTablet.printArchivedVideoSet();
    }
}
