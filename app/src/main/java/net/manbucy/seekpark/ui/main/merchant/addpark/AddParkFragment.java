package net.manbucy.seekpark.ui.main.merchant.addpark;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import net.manbucy.seekpark.R;
import net.manbucy.seekpark.common.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * AddParkFragment
 * Created by yang on 2017/6/28.
 */

public class AddParkFragment extends BaseFragment implements AddParkContract.View{
    private AddParkContract.Presenter addParkPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mapf_park_name)
    TextInputLayout nameInputLayout;
    EditText name;
    @BindView(R.id.mapf_park_address)
    TextInputLayout addressInputLayout;
    EditText address;
    @BindView(R.id.mapf_get_address)
    Button location;
    @BindView(R.id.mapf_park_address_lat)
    TextView latitude;
    @BindView(R.id.mapf_park_address_lon)
    TextView longitude;
    @BindView(R.id.mapf_park_normal_num)
    TextInputLayout normalNumInputLayout;
    EditText normalNum;
    @BindView(R.id.mapf_park_normal_price)
    TextInputLayout normalPriceInputLayout;
    EditText normalPrice;
    @BindView(R.id.mapf_has_charging)
    Switch hasCharging;
    @BindView(R.id.mapf_charging_park)
    LinearLayout chargingParkLayout;
    @BindView(R.id.mapf_park_charging_num)
    TextInputLayout chargingNumInputLayout;
    EditText chargingNum;
    @BindView(R.id.mapf_park_charging_price)
    TextInputLayout chargingPriceInputLayout;
    EditText chargingPrice;
    @BindView(R.id.mapf_park_add_image)
    ImageView addImage;
    @BindView(R.id.mapf_park_cancel_image)
    ImageButton cancelImage;
    @BindView(R.id.mapf_park_remark)
    TextInputLayout remarkInputLayout;
    EditText remark;

    public static AddParkFragment getInstance() {
        return new AddParkFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.merchant_add_park_fragment, container, false);
        ButterKnife.bind(this, root);
        initView();
        initListener();
        return root;
    }

    private void initListener() {
        // toolbar 上面的 勾
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_add_park) {
                    toast("添加成功");
                }
                return true;
            }
        });
        // 显示 隐藏 充电车位添加界面
        hasCharging.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    chargingParkLayout.setVisibility(View.GONE);
                    hasCharging.setText("不含有");
                } else {
                    hasCharging.setText("含有");
                    chargingParkLayout.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    private void initView() {
        name = nameInputLayout.getEditText();
        address= addressInputLayout.getEditText();
        toolbar.setTitle("添加停车场");
        toolbar.inflateMenu(R.menu.add_park_menu);
        setToolbar(toolbar, FRAGMENT_TYPE_SECONDE);
        address.setText("安徽理工大学");
    }


    @Override
    public void onResume() {
        super.onResume();
        addParkPresenter.start();
    }

    @Override
    public void setPresenter(AddParkContract.Presenter presenter) {
        addParkPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        addParkPresenter.detachView(this);
    }

}
