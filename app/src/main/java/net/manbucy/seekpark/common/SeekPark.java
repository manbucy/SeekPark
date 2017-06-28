package net.manbucy.seekpark.common;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import net.manbucy.seekpark.util.LogUtil;

import java.security.KeyStore;

import me.yokeyword.fragmentation.BuildConfig;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * 自定义Application类，用于初始化一些数据
 * Created by yang on 2017/6/22.
 */

public class SeekPark extends Application {
    private  static Context mContext ;
    private static KeyStore mKeyStore;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");
            mKeyStore.load(null);
        } catch (Exception e) {
            LogUtil.e("KeyStoreException",e.toString());
        }

        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        Log.d("App", "onException: " + e);
                    }
                })
                .install();
    }

    /**
     * 获取一个全局的 Context
     * @return 全局Context
     */
    public static Context getmContext() {
        return mContext;
    }

    /**
     * 获取一个KeyStore
     * @return KeyStore
     */
    public static KeyStore getKeyStore() {
        return mKeyStore;
    }
}
