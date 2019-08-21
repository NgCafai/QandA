package com.ngcafai.QandA.async;

import java.util.List;

/**
 * Created by NgCafai on 2019/4/27 15:07.
 */
public interface EventHandler {
    void doHandle(EventModel model);

    List<EventType> getSupportEventTypes();
}
