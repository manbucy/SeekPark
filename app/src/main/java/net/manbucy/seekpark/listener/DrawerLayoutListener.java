package net.manbucy.seekpark.listener;

/**
 * 抽屉监听类
 * Created by yang on 2017/6/26.
 */

public interface DrawerLayoutListener {
    /**
     * 在fragment里面锁定抽屉 不让其滑动打开
     */
    void onLock(boolean lock);


    /**
     * 在fragment里面打开抽屉  点击toolbar 上面的按钮 打开抽屉
     */
    void onOpen();
}
