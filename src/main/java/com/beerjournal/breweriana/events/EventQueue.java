package com.beerjournal.breweriana.events;


import com.google.common.collect.EvictingQueue;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Component;

@Component
public final class EventQueue {

    private final static int MAX_SIZE = 100;

    private final EvictingQueue<EventDto> queue;

    private EventQueue(){
        queue = EvictingQueue.create(MAX_SIZE);
    }

    public boolean addEvent(EventDto event) {
        return queue.add(event);
    }

    ImmutableList<EventDto> getLatestEvents(int count) {
        ImmutableList<EventDto> eventsList = ImmutableList.copyOf(queue);
        int queueSize = eventsList.size();
        int countOrMax = (count > queueSize) ? queueSize : count;
        return eventsList.subList(queueSize - countOrMax, queueSize).reverse();
    }

}
