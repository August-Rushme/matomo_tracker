package com.example.matomo_tracker.traffic.sdk.dispatcher;

import com.example.matomo_tracker.traffic.sdk.Tracker;

public interface DispatcherFactory {
    Dispatcher build(Tracker tracker);
}
