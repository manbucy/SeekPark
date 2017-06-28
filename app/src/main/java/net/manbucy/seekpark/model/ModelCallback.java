package net.manbucy.seekpark.model;

/**
 * 回调接口
 * Created by yang on 2017/6/25.
 */

public interface ModelCallback {
    /**
     * 查询回调接口
     * @param <T>
     */
    interface Query<T> {
        void onSucceed(T var);

        void onFailed();
    }

    /**
     * 普通回调接口
     */
    interface Normal {
        void onSucceed();

        void onFailed();
    }
}
