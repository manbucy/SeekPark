package net.manbucy.seekpark.common;

import android.app.Application;
import android.content.Context;
import net.manbucy.seekpark.util.LogUtil;

import java.security.KeyStore;

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
