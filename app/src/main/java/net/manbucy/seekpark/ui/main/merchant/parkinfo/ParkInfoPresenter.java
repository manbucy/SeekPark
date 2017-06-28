package net.manbucy.seekpark.ui.main.merchant.parkinfo;

/**
 * ParkInfoPresenter
 * Created by yang on 2017/6/28.
 */

public class ParkInfoPresenter implements ParkInfoContract.Presenter {
    private ParkInfoContract.View parkInfoView;


    @Override
    public void start() {

    }

    @Override
    public void detachView(Object o) {
            parkInfoView = null;
    }

}
