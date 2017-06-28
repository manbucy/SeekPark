package net.manbucy.seekpark.ui.main.merchant;

import net.manbucy.seekpark.ui.main.merchant.parkinfo.ParkInfoFragment;

/**
 * MerchantPresenter
 * Created by yang on 2017/6/28.
 */

public class MerchantPresenter implements MerchantContract.Presenter {
    private MerchantContract.ParkInfoView parkInfoView;


    @Override
    public void start() {

    }

    @Override
    public void detachView(Object o) {
        if (o instanceof ParkInfoFragment) {
            parkInfoView = null;
        }
    }

}
