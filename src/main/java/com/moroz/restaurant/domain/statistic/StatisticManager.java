package com.moroz.restaurant.domain.statistic;

import com.moroz.restaurant.domain.statistic.event.CookedOrderEventDataRow;
import com.moroz.restaurant.domain.statistic.event.EventDataRow;
import com.moroz.restaurant.domain.statistic.event.EventType;
import com.moroz.restaurant.domain.statistic.event.VideoSelectedEventDataRow;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticManager {

    private final StatisticStorage statisticStorage;

    private StatisticManager() {
        statisticStorage = new StatisticStorage();
    }

    public static StatisticManager getInstance() {
        return StatisticManagerHolder.instance;
    }

    public void register(EventDataRow data) {
        statisticStorage.put(data);
    }

    private static class StatisticManagerHolder {
        public static final StatisticManager instance = new StatisticManager();
    }

    public Map<String, Double> calculateAdvertisementProfit() {
        Map<String, Double> result = new TreeMap<>(Collections.reverseOrder());

        List<EventDataRow> advertisementList = statisticStorage.getEventDataRow(EventType.SELECTED_VIDEOS);
        DateFormat targetFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        for(EventDataRow advertisementVideo : advertisementList) {
            VideoSelectedEventDataRow videoRow = (VideoSelectedEventDataRow) advertisementVideo;
            String date = targetFormat.format(videoRow.getDate());
            if(!result.containsKey(date)) {
                result.put(date, 0D);
            }

            result.put(date, result.get(date)+videoRow.getAmount());
        }

        return result;
    }

    public Map<String, Map<String, Integer> > calculateCookWorkloading() {
        Map<String, Map<String, Integer> > result = new TreeMap<>();

        List<EventDataRow> cookWorkloadingList = statisticStorage.getEventDataRow(EventType.COOKED_ORDER);
        DateFormat targetFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        for(EventDataRow cook : cookWorkloadingList) {
            CookedOrderEventDataRow cookRow = (CookedOrderEventDataRow) cook;
            String date = targetFormat.format(cookRow.getDate());
            String cookName = cookRow.getCookName();

            if(!result.containsKey(date)) {
                result.put(date, new TreeMap<>());
            }
            Map<String, Integer> cooksWorkloadingForDate = result.get(date);
            if(!cooksWorkloadingForDate.containsKey(cookName)) {
                cooksWorkloadingForDate.put(cookName, cookRow.getTime());
            } else {
                cooksWorkloadingForDate.put(cookName, cooksWorkloadingForDate.get(cookName)+cookRow.getTime());
            }
        }

        return result;
    }

    private class StatisticStorage {

        private Map<EventType, List<EventDataRow>> storage;

        public StatisticStorage() {
            storage = new HashMap<>(3);
            storage.put(EventType.COOKED_ORDER, new ArrayList<>());
            storage.put(EventType.NO_AVAILABLE_VIDEO, new ArrayList<>());
            storage.put(EventType.SELECTED_VIDEOS, new ArrayList<>());
        }

        private void put(EventDataRow data) {
            storage.get(data.getType()).add(data);
        }

        public List<EventDataRow> getEventDataRow(EventType eventType) {
            return storage.get(eventType);
        }
    }
}
