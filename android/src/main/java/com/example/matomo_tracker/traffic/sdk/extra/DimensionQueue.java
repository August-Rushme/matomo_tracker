package com.example.matomo_tracker.traffic.sdk.extra;

import com.example.matomo_tracker.traffic.sdk.TrackMe;
import com.example.matomo_tracker.traffic.sdk.Tracker;
import com.example.matomo_tracker.traffic.sdk.Traffic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A helper class for custom dimensions. Acts like a queue for dimensions to be send.
 * On each tracking call it will insert as many saved dimensions as it is possible without overwriting existing information.
 */
public class DimensionQueue {
    private static final String TAG = Traffic.tag(DimensionQueue.class);
    private final List<CustomDimension> mOneTimeDimensions = new ArrayList<>();

    public DimensionQueue(Tracker tracker) {
        Tracker.Callback callback = DimensionQueue.this::onTrack;
        tracker.addTrackingCallback(callback);
    }

    /**
     * The added id-value-pair will be injected into the next tracked event,
     * if that events slot for this ID is still empty.
     */
    public void add(int id, String value) {
        mOneTimeDimensions.add(new CustomDimension(id, value));
    }

    private TrackMe onTrack(TrackMe trackMe) {
        for (Iterator<CustomDimension> it = mOneTimeDimensions.iterator(); it.hasNext(); ) {
            CustomDimension dim = it.next();
            String existing = CustomDimension.getDimension(trackMe, dim.getId());
            if (existing != null) {
                //Timber.tag(TAG).d("Setting dimension %s to slot %d would overwrite %s, skipping!", dim.getValue(), dim.getId(), existing);
            } else {
                CustomDimension.setDimension(trackMe, dim);
                it.remove();
            }
        }
        return trackMe;
    }
}
