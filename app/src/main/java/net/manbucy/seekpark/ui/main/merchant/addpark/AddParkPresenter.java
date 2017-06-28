package net.manbucy.seekpark.ui.main.merchant.addpark;

import net.manbucy.seekpark.model.park.source.ParkRepository;

/**
 * AddParkPresenter
 * Created by yang on 2017/6/28.
 */

public class AddParkPresenter implements AddParkContract.Presenter {
    private AddParkContract.View addParkView;
    private ParkRepository parkRepository;

    public AddParkPresenter(AddParkContract.View addParkView, ParkRepository parkRepository) {
        this.addParkView = addParkView;
        this.parkRepository = parkRepository;
        this.addParkView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void detachView(Object o) {
        addParkView = null;
    }
}
