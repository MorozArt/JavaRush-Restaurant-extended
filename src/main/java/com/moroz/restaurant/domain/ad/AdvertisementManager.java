package com.moroz.restaurant.domain.ad;

import com.moroz.restaurant.domain.ConsoleHelper;
import com.moroz.restaurant.domain.statistic.StatisticManager;
import com.moroz.restaurant.domain.statistic.event.NoAvailableVideoEventDataRow;
import com.moroz.restaurant.domain.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AdvertisementManager {

    private final AdvertisementStorage storage;
    private int timeSeconds;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
        storage = AdvertisementStorage.getInstance();
    }

    public void processVideos() {
        if(storage.list().isEmpty()) {
            StatisticManager.getInstance().register(new NoAvailableVideoEventDataRow(timeSeconds));
            throw new NoVideoAvailableException();
        }

        List<Advertisement> sortedList = new ArrayList<>(storage.list());
        sortedList = sortedList.stream().sorted(Comparator
                .comparingLong(Advertisement::getAmountPerOneDisplaying).reversed()
                .thenComparingLong(a->a.getAmountPerOneDisplaying()/a.getDuration()))
                .collect(Collectors.toList());
        List<Advertisement> resultVideos = selectionVideos(sortedList,0, timeSeconds);

        int totalAmount = 0;
        int totalDuration = 0;
        for(Advertisement video : resultVideos) {
            totalAmount += video.getAmountPerOneDisplaying();
            totalDuration += video.getDuration();
        }
        StatisticManager.getInstance().register(new VideoSelectedEventDataRow(resultVideos, totalAmount, totalDuration));

        for(Advertisement video : resultVideos) {
            ConsoleHelper.writeMessage(video.toString());
            video.revalidate();
        }
    }

    private List<Advertisement> selectionVideos(List<Advertisement> sortedVideoList, int startListIndex, int duration) {
        List<Advertisement> result = new ArrayList<>();
        List<Advertisement> subList = new ArrayList<>();

        for(int i=startListIndex;i<sortedVideoList.size();++i) {
            Advertisement video = sortedVideoList.get(i);
            if(video.getHits() > 0 && video.getDuration() <= duration) {
                subList.add(video);
                subList.addAll(selectionVideos(sortedVideoList, i+1, duration-video.getDuration()));

                result = compareAdvertisementsList(subList, result);
                subList = new ArrayList<>();
            }
        }

        return result;
    }

    private List<Advertisement> compareAdvertisementsList(List<Advertisement> list1, List<Advertisement> list2) {
        long list1Amount = 0;
        if(!list1.isEmpty()) {
            for(Advertisement video : list1) {
                list1Amount += video.getAmountPerOneDisplaying();
            }
        }

        long list2Amount = 0;
        if(!list2.isEmpty()) {
            for(Advertisement video : list2) {
                list2Amount += video.getAmountPerOneDisplaying();
            }
        }

        if(list1Amount == 0 && list2Amount == 0) {
            return list1;
        }

        if(list1Amount != list2Amount) {
            return list1Amount>list2Amount ? list1 : list2;
        } else {

            int list1TotalTime = 0;
            for(Advertisement video : list1) {
                list1TotalTime += video.getDuration();
            }

            int list2TotalTime = 0;
            for(Advertisement video : list2) {
                list2TotalTime += video.getDuration();
            }

            if(list1TotalTime != list2TotalTime) {
                return list1TotalTime>list2TotalTime ? list1 : list2;
            } else {
                return list1.size() < list2.size() ? list1 : list2;
            }
        }
    }
}
