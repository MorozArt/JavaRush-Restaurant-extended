package com.moroz.restaurant.domain.statistic.event;

import java.util.Date;

public interface EventDataRow {

    EventType getType();

    Date getDate();

    int getTime();
}
