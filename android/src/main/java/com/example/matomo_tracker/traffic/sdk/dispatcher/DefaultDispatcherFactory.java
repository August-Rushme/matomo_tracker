package com.example.matomo_tracker.traffic.sdk.dispatcher;

import com.example.matomo_tracker.traffic.sdk.Tracker;
import com.example.matomo_tracker.traffic.sdk.tools.Connectivity;

public class DefaultDispatcherFactory implements DispatcherFactory {
    public Dispatcher build(Tracker tracker) {
        return new DefaultDispatcher(
                new EventCache(new EventDiskCache(tracker)),
                new Connectivity(tracker.getTraffic().getContext()),
                new PacketFactory(tracker.getAPIUrl()),
                new DefaultPacketSender()
        );
    }
}
