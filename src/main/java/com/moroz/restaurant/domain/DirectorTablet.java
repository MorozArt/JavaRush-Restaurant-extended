package com.moroz.restaurant.domain;

import com.moroz.restaurant.domain.ad.Advertisement;
import com.moroz.restaurant.domain.ad.StatisticAdvertisementManager;
import com.moroz.restaurant.domain.statistic.StatisticManager;

import java.util.List;
import java.util.Map;

public class DirectorTablet {

    public void printAdvertisementProfit(){
        Map<String, Double> advertisementProfitMap = StatisticManager.getInstance().calculateAdvertisementProfit();
        Double totalAmount = 0D;

        for(Map.Entry<String, Double> entry : advertisementProfitMap.entrySet()) {
            if (entry.getValue() > 0) {
                ConsoleHelper.writeMessage(entry.getKey()+" - "+entry.getValue()/100);
                totalAmount += entry.getValue()/100;
            }
        }
        ConsoleHelper.writeMessage("Total - "+totalAmount);
    }

    public void printCookWorkloading() {
        Map<String, Map<String, Integer> > cookWorkloadingMap = StatisticManager.getInstance().calculateCookWorkloading();

        for(String date : cookWorkloadingMap.keySet()) {
            ConsoleHelper.writeMessage(date);
            for(Map.Entry<String, Integer> entry : cookWorkloadingMap.get(date).entrySet()) {
                if (entry.getValue() != 0) {
                    ConsoleHelper.writeMessage(entry.getKey()+" - "+entry.getValue()+" min");
                }
            }
        }
    }

    public void printActiveVideoSet() {
        List<Advertisement> videoList = StatisticAdvertisementManager.getInstance().getVideoSet(true);
        for(Advertisement video : videoList) {
            ConsoleHelper.writeMessage(video.getName()+" - "+video.getHits());
        }
    }

    public void printArchivedVideoSet() {
        List<Advertisement> archiveVideoList = StatisticAdvertisementManager.getInstance().getVideoSet(false);
        for(Advertisement video : archiveVideoList) {
            ConsoleHelper.writeMessage(video.getName());
        }
    }
}
