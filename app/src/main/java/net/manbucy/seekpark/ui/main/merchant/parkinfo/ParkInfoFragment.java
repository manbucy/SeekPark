package net.manbucy.seekpark.ui.main.merchant.parkinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.manbucy.seekpark.R;
import net.manbucy.seekpark.common.BaseFragment;
import net.manbucy.seekpark.model.park.source.ParkRepository;
import net.manbucy.seekpark.model.park.source.remote.ParkRemoteSource;
import net.manbucy.seekpark.model.user.User;
import net.manbucy.seekpark.ui.main.merchant.addpark.AddParkFragment;
import net.manbucy.seekpark.ui.main.merchant.addpark.AddParkPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * ParkInfoFragment 商家 停车场信息 view
 * Created by yang on 2017/6/28.
 */

public class ParkInfoFragment extends BaseFragment implements ParkInfoContract.View {
    private ParkInfoContract.Presenter merchantPresenter;
    private User mCurrentUser;
    public static final int START_ADD = 100;
    public static final int START_ALTER = 101;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mpif_no_park)
    LinearLayout noParkLayout;
    @BindView(R.id.mpif_normal)
    LinearLayout normalParLayout;
    @BindView(R.id.mpif_add_or_alert_button)
    FloatingActionButton addOrAlertButton;

    public static ParkInfoFragment newInstance() {
        return new ParkInfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.merchant_park_info_fragment, container, false);
        ButterKnife.bind(this, root);
        mCurrentUser = getCurrentUser();
        initView();
        return root;
    }

    private void initView() {
        toolbar.setTitle("我的停车场");
        setToolbar(toolbar,FRAGMENT_TYPE_FIRST);
        if (mCurrentUser.isHasPark()) {
            noParkLayout.setVisibility(View.GONE);
            normalParLayout.setVisibility(View.VISIBLE);
            addOrAlertButton.setImageResource(R.drawable.ic_park_alter);
        } else {
            noParkLayout.setVisibility(View.VISIBLE);
            normalParLayout.setVisibility(View.GONE);
            addOrAlertButton.setImageResource(R.drawable.ic_park_add);
        }
    }
    @OnClick(R.id.mpif_add_or_alert_button)
    public void AddOrAlert(){
        if (getCurrentUser().isHasPark()) {
//            startForResult(AlterParkFragment.newInstance(), START_ALTER);
        } else {
            AddParkFragment addParkFragment = AddParkFragment.getInstance();
            new AddParkPresenter(addParkFragment,
                    ParkRepository.getIntance(ParkRemoteSource.getInstance()));
            startForResult(addParkFragment,START_ADD);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        merchantPresenter.start();
    }

    @Override
    public void setPresenter(ParkInfoContract.Presenter presenter) {
        merchantPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        merchantPresenter.detachView(this);
    }
}
