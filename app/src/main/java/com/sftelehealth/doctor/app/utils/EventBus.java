package com.sftelehealth.doctor.app.utils;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;

/**
 * Created by Rahul on 22/06/17.
 * for more info about the bus and subscribing to specific Events only check out the below link
 * {https://gist.github.com/jaredsburrows/e9706bd8c7d587ea0c1114a0d7651d13}
 */

public class EventBus {

    private static EventBus instance;

    private final Relay<Object> bus = PublishRelay.create().toSerialized();

    public static EventBus instanceOf() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }

    public void send(Object event) {
        bus.accept(event);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}
