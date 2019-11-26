package com.moroz.restaurant.domain.ad;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementStorage {

    private final List<Advertisement> videos;

    private AdvertisementStorage() {
        videos = new ArrayList<>();

        Object someContent = new Object();
        videos.add(new Advertisement(someContent, "First Video", 5000, 100, 3 * 60));
        videos.add(new Advertisement(someContent, "Second Video", 100, 1, 15 * 60));
        videos.add(new Advertisement(someContent, "Third Video", 400, 2, 10 * 60));
        videos.add(new Advertisement(someContent, "Четвертое видео", 600, 5, 6 * 60));
        videos.add(new Advertisement(someContent, "Архивное видео", 400, 0, 10 * 60));
    }

    public List<Advertisement> list() {
        return videos;
    }

    public void add(Advertisement advertisement) {
        videos.add(advertisement);
    }

    public static AdvertisementStorage getInstance() {
        return AdvertisementStorageHolder.instance;
    }

    private static class AdvertisementStorageHolder {
        private static final AdvertisementStorage instance = new AdvertisementStorage();
    }
}

