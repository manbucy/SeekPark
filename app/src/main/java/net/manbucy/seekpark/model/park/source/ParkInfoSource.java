package net.manbucy.seekpark.model.park.source;

import net.manbucy.seekpark.model.ModelCallback;
import net.manbucy.seekpark.model.park.Park;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * ParkInfoSource
 * Created by yang on 2017/6/28.
 */
interface ParkInfoSource {
    void uploadFile(BmobFile file, ModelCallback.Normal callback);

    void saveParkIno(Park park, ModelCallback.Normal callback);

    void findParkByLocation(BmobGeoPoint bmobGeoPoint, final ModelCallback.Query<List<Park>> callback);
}
