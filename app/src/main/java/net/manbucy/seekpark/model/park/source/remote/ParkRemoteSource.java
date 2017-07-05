package net.manbucy.seekpark.model.park.source.remote;

import net.manbucy.seekpark.model.ModelCallback;
import net.manbucy.seekpark.model.park.Park;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

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

    /**
     * 上传文件
     *
     * @param file     要上传的文件
     * @param callback 回调
     */
    public void uploadFile(BmobFile file, final ModelCallback.Normal callback) {
        file.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    callback.onSucceed();
                } else {
                    callback.onFailed();
                }
            }
        });
    }

    /**
     * 保存Park信息
     * @param park 要保存的对象
     * @param callback 回调
     */
    public void saveParkInfo(Park park, final ModelCallback.Normal callback){
        park.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    callback.onSucceed();
                } else {
                    callback.onFailed();
                }
            }
        });
    }

    /**
     * 根据地理位置查询 附近的park
     * @param bmobGeoPoint 位置
     * @param callback 回调
     */
    public void findParkByLocation(BmobGeoPoint bmobGeoPoint,
                                   final ModelCallback.Query<List<Park>> callback){
        BmobQuery<Park> query = new BmobQuery<>();
        query.setLimit(20);
        query.addWhereNear("location",bmobGeoPoint);
        query.findObjects(new FindListener<Park>() {
            @Override
            public void done(List<Park> list, BmobException e) {
                if (e == null){
                    callback.onSucceed(list);
                } else {
                    callback.onFailed();
                }
            }
        });
    }
}
