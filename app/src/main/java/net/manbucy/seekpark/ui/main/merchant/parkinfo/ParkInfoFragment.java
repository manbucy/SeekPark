package net.manbucy.seekpark.ui.main.merchant.parkinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.manbucy.seekpark.R;
import net.manbucy.seekpark.common.BaseFragment;
import net.manbucy.seekpark.ui.main.merchant.MerchantContract;

import butterknife.ButterKnife;

/**
 * ParkInfoFragment 商家 停车场信息 view
 * Created by yang on 2017/6/28.
 */

public class ParkInfoFragment extends BaseFragment implements MerchantContract.ParkInfoView{
    private MerchantContract.Presenter merchantPresenter;

    public static ParkInfoFragment newInstance(){
        return new ParkInfoFragment();
    }

    public ParkInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.merchant_park_info_fragment,container,false);
        ButterKnife.bind(this,root);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        merchantPresenter.start();
    }

    @Override
    public void setPresenter(MerchantContract.Presenter presenter) {
        merchantPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        merchantPresenter.detachView(this);
    }
}
