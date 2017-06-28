package net.manbucy.seekpark.model.park.source;

import android.support.v4.app.INotificationSideChannel;

import net.manbucy.seekpark.model.park.source.remote.ParkRemoteSource;

/**
 * ParkRepository
 * Created by yang on 2017/6/28.
 */

public class ParkRepository implements ParkInfoSource {
    private static ParkRepository INSTANCE;
    private final ParkRemoteSource parkRemoteSource;

    private ParkRepository(ParkRemoteSource parkRemoteSource) {
        this.parkRemoteSource = parkRemoteSource;
    }

    public static ParkRepository getIntance(ParkRemoteSource parkRemoteSource) {
        if (INSTANCE == null) {
            INSTANCE = new ParkRepository(parkRemoteSource);
        }
        return INSTANCE;
    }



}
