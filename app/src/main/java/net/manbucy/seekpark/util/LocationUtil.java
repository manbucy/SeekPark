package net.manbucy.seekpark.util;

import android.app.Activity;
import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 *
 * Created by yang on 2017/3/12.
 */

public class LocationUtil {
    private static LocationClient locationClient;
    private static MyLocationListener mListener;

    public static void setMyLocationListener(MyLocationListener myLocationListener) {
        mListener = myLocationListener;
    }

    public static void getLocation(Context context) {
        locationClient = new LocationClient(context);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setIsNeedLocationDescribe(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setNeedDeviceDirect(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);
        locationClient.setLocOption(option);

        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (mListener != null) {
                    if (bdLocation == null) {
                        locationClient.stop();
                        locationClient = null;
                        mListener.locateFailed();
                    } else {
                        if (bdLocation.getAddrStr() != null) {
                            locationClient.stop();
                            locationClient = null;
                            mListener.locateSucceed(bdLocation);
                        } else {
                            locationClient.stop();
                            locationClient = null;
                            mListener.locateFailed();
                        }
                    }
                }
            }

            @Override
            public void onConnectHotSpotMessage(String s, int i) {

            }
        });
        locationClient.start();
    }

    public static void stopLocation() {
        locationClient.stop();
    }

    public interface MyLocationListener {
        void locateSucceed(final BDLocation bdLocation);

        void locateFailed();
    }
}
