package net.manbucy.seekpark.ui.main.searchpark.parklist;

import net.manbucy.seekpark.model.park.Park;
import net.manbucy.seekpark.ui.BasePresenter;
import net.manbucy.seekpark.ui.BaseView;

import java.util.List;

/**
 * SearchParkContract
 * Created by yang on 2017/6/28.
 */

interface SearchParkContract {
    interface View extends BaseView<Presenter>{
        void showToast(String message);
        void setRefreshing(boolean refreshing);
        void loadData(List<Park> parkList);
    }
    interface Presenter extends BasePresenter{
        void findPark();
    }
}
