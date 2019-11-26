package com.moroz.restaurant.domain.ad;

public class Advertisement {

    private Object content;
    private String name;
    private long initialAmount;
    private int hits;
    private int duration;
    private long amountPerOneDisplaying;

    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;
        amountPerOneDisplaying = hits > 0 ? initialAmount/hits : 0;
    }

    public void revalidate() {
        if(hits > 0) {
            --hits;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getHits() {
        return hits;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    @Override
    public String toString() {
        return "Video: " + name + ", " + getAmountPerOneDisplaying() + ", " + (getAmountPerOneDisplaying()*1000/duration);
    }
}
