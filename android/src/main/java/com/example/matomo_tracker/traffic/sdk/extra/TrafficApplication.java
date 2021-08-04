package com.example.matomo_tracker.traffic.sdk.extra;

import android.app.Application;

import com.example.matomo_tracker.traffic.sdk.Tracker;
import com.example.matomo_tracker.traffic.sdk.Traffic;
import com.example.matomo_tracker.traffic.sdk.TrackerBuilder;

public abstract class TrafficApplication extends Application {
    private Tracker mTrafficTracker;

    public Traffic getTraffic() {
        return Traffic.getInstance(this);
    }

    /**
     * Gives you an all purpose thread-safe persisted Tracker.
     *
     * @return a shared Tracker
     */
    public synchronized Tracker getTracker() {
        if (mTrafficTracker == null) mTrafficTracker = onCreateTrackerConfig().build(getTraffic());
        return mTrafficTracker;
    }

    /**
     * See {@link TrackerBuilder}.
     * You may be interested in {@link TrackerBuilder#createDefault(String, int)}
     *
     * @return the tracker configuration you want to use.
     */
    public abstract TrackerBuilder onCreateTrackerConfig();

    @Override
    public void onLowMemory() {
        if (mTrafficTracker != null) mTrafficTracker.dispatch();
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        if ((level == TRIM_MEMORY_UI_HIDDEN || level == TRIM_MEMORY_COMPLETE) && mTrafficTracker != null) {
            mTrafficTracker.dispatch();
        }
        super.onTrimMemory(level);
    }

}
