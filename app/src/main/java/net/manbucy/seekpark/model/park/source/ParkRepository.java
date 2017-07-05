package net.manbucy.seekpark.model.park.source;

import net.manbucy.seekpark.model.ModelCallback;
import net.manbucy.seekpark.model.park.Park;
import net.manbucy.seekpark.model.park.source.remote.ParkRemoteSource;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;

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

    public static ParkRepository getInstance(ParkRemoteSource parkRemoteSource) {
        if (INSTANCE == null) {
            INSTANCE = new ParkRepository(parkRemoteSource);
        }
        return INSTANCE;
    }

    @Override
    public void uploadFile(BmobFile file, ModelCallback.Normal callback) {
        parkRemoteSource.uploadFile(file,callback);
    }

    @Override
    public void saveParkIno(Park park, ModelCallback.Normal callback) {
        parkRemoteSource.saveParkInfo(park,callback);
    }

    @Override
    public void findParkByLocation(BmobGeoPoint bmobGeoPoint, ModelCallback.Query<List<Park>> callback) {
        parkRemoteSource.findParkByLocation(bmobGeoPoint,callback);
    }
}
