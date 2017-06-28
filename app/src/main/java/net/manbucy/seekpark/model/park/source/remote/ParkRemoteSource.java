package net.manbucy.seekpark.model.park.source.remote;

/**
 * ParkRemoteSource
 * Created by yang on 2017/6/28.
 */

public class ParkRemoteSource {
    private static ParkRemoteSource INSTANCE = null;

    public static ParkRemoteSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ParkRemoteSource();
        }
        return INSTANCE;
    }

    public ParkRemoteSource() {

    }
}
