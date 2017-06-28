package net.manbucy.seekpark.ui.main.merchant.parkinfo;

import net.manbucy.seekpark.model.park.source.ParkRepository;

/**
 * ParkInfoPresenter
 * Created by yang on 2017/6/28.
 */

public class ParkInfoPresenter implements ParkInfoContract.Presenter {
    private ParkInfoContract.View parkInfoView;
    private ParkRepository parkRepository;

    public ParkInfoPresenter(ParkRepository parkRepository, ParkInfoContract.View parkInfoView) {
        this.parkRepository = parkRepository;
        this.parkInfoView = parkInfoView;
        parkInfoView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void detachView(Object o) {
        parkInfoView = null;
    }

}
