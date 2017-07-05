package net.manbucy.seekpark.ui.main.searchpark.parkinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;

import net.manbucy.seekpark.R;
import net.manbucy.seekpark.common.BaseFragment;
import net.manbucy.seekpark.model.park.Park;
import net.manbucy.seekpark.model.user.User;
import net.manbucy.seekpark.util.LocationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ParkInfoFragment  停车场详细信息 view
 * Created by yang on 2017/6/28.
 */

public class ParkInfoFragment extends BaseFragment implements ParkInfoContract.View {
    private ParkInfoContract.Presenter parkInfoPresenter;
    private User mCurrentUser;
    Park mPark;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.park_info_image)
    ImageView parkInfoImage;
    @BindView(R.id.search_park_info_normal_number)
    TextView normalNumber;
    @BindView(R.id.search_park_info_normal_price)
    TextView normalPrice;
    @BindView(R.id.search_park_info_charging)
    LinearLayout chargingLayout;
    @BindView(R.id.search_park_info_charging_number)
    TextView chargingNumber;
    @BindView(R.id.search_park_info_charging_price)
    TextView chargingPrice;
    @BindView(R.id.search_park_info_address)
    TextView address;
    @BindView(R.id.search_park_info_remark)
    TextView remark;

    public static ParkInfoFragment newInstance(Park park) {
        ParkInfoFragment parkInfoFragment = new ParkInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("park_info",park);
        parkInfoFragment.setArguments(bundle);
        return parkInfoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.search_park_info_fragment, container, false);
        ButterKnife.bind(this, root);
        mCurrentUser = getCurrentUser();
        initView();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPark = (Park) bundle.getSerializable("park_info");
        }
    }

    private void initView() {
        toolbar.setTitle(mPark.getName());
        setToolbar(toolbar,FRAGMENT_TYPE_FIRST);
        Glide.with(_mActivity).load(mPark.getImageUrl()).into(parkInfoImage);
        normalNumber.setText(String.valueOf(mPark.getNumber()));
        normalPrice.setText(String.valueOf(mPark.getPrice()));
        if (mPark.isHasCharging()) {
            chargingLayout.setVisibility(View.VISIBLE);
            chargingNumber.setText(String.valueOf(mPark.getChargingNumber()));
            chargingPrice.setText(String.valueOf(mPark.getChargingPrice()));
        } else {
            chargingLayout.setVisibility(View.GONE);
        }
        address.setText(mPark.getAddress());
        remark.setText(mPark.getRemark());
    }
    /**
     * 预定 停车位
     */
    @OnClick(R.id.order_park)
    public void orderPark() {
        parkInfoPresenter.showToast("预定成功");
    }


    /**
     * 到停车场去，打开导航界面
     */
    @OnClick(R.id.to_park)
    public void toPark() {
        final NavigationService navigationService = new NavigationService(_mActivity);
        LocationUtil.getLocation(_mActivity);
        LocationUtil.setMyLocationListener(new LocationUtil.MyLocationListener() {
            @Override
            public void locateSucceed(final BDLocation bdLocation) {
                _mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        navigationService.startNavigation(bdLocation, mPark.getLocation());
                        navigationService.showProgressDialog();
                    }
                });
            }

            @Override
            public void locateFailed() {
                _mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        navigationService.dismissProgressDialog();
                    }
                });
            }
        });
    }

    @Override
    public void showToast(String message) {
        toast(message);
    }

    @Override
    public void onResume() {
        super.onResume();
        parkInfoPresenter.start();
    }

    @Override
    public void setPresenter(ParkInfoContract.Presenter presenter) {
        parkInfoPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        parkInfoPresenter.detachView(this);
    }
}
