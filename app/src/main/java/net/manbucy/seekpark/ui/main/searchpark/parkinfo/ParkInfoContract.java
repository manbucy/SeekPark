package net.manbucy.seekpark.ui.main.searchpark.parkinfo;

import net.manbucy.seekpark.ui.BasePresenter;
import net.manbucy.seekpark.ui.BaseView;

/**
 * ParkInfoContract
 * Created by yang on 2017/6/28.
 */

interface ParkInfoContract {

    interface View extends BaseView<Presenter> {
        void showToast(String message);
    }

    interface Presenter extends BasePresenter {
        void showToast(String message);
    }
}
