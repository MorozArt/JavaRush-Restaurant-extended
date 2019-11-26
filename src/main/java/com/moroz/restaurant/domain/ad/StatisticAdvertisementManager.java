package com.moroz.restaurant.domain.ad;

import java.util.ArrayList;
import java.util.List;

public class StatisticAdvertisementManager {

    private AdvertisementStorage storage;

    private StatisticAdvertisementManager() {
        storage = AdvertisementStorage.getInstance();
    }

    public List<Advertisement> getVideoSet(boolean active) {
        List<Advertisement> result = new ArrayList<>();

        if(active) {
            for(Advertisement video : storage.list()) {
                if(video.getHits() > 0) result.add(video);
            }
        } else {
            for(Advertisement video : storage.list()) {
                if(video.getHits() <= 0) result.add(video);
            }
        }

        result.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        return result;
    }

    public static StatisticAdvertisementManager getInstance() {
        return StatisticAdvertisementManagerHolder.instance;
    }

    private static class StatisticAdvertisementManagerHolder {
        private static final StatisticAdvertisementManager instance = new StatisticAdvertisementManager();
    }
}
