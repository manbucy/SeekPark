package net.manbucy.seekpark.ui.main.merchant.addpark;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;

import net.manbucy.seekpark.R;
import net.manbucy.seekpark.common.BaseFragment;
import net.manbucy.seekpark.common.Constant;
import net.manbucy.seekpark.model.park.Park;
import net.manbucy.seekpark.model.user.User;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * AddParkFragment
 * Created by yang on 2017/6/28.
 */

public class AddParkFragment extends BaseFragment implements AddParkContract.View {
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
    @BindView(R.id.mapf_lat_lon)
    TextInputLayout latAndLon;
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
    @BindView(R.id.mapf_image_toast)
    TextView imageToast;
    @BindView(R.id.mapf_park_remark)
    TextInputLayout remarkInputLayout;
    EditText remark;
    boolean isVerify = false;
    boolean hasImage = false;
    ProgressDialog progressDialog;
    BmobFile imageFile;
    private static final int CHOOSE_FROM_ALBUM = 2;

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
                    isVerify = true;
                    checkEmpty();
                    if (isVerify && hasImage) {
                        Park park = new Park();
                        park.setName(name.getText().toString());
                        park.setAddress(address.getText().toString());
                        park.setLocation(
                                new BmobGeoPoint(Double.parseDouble(longitude.getText().toString()),
                                        Double.parseDouble(latitude.getText().toString())));
                        park.setNumber(Integer.parseInt(normalNum.getText().toString()));
                        park.setPrice(Double.parseDouble(normalPrice.getText().toString()));
                        if (hasCharging.isChecked()) {
                            park.setHasCharging(true);
                            park.setChargingNumber(Integer.parseInt(chargingNum.getText().toString()));
                            park.setChargingPrice(Double.parseDouble(chargingPrice.getText().toString()));
                        }
                        park.setRemark(remark.getText().toString());
                        addParkPresenter.addPark(imageFile, park, getCurrentUser());
                    }
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
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    isVerify = false;
                    showInputErrorMsgs(Constant.InputLayoutEntry.PARK_NAME, "名称不能为空");
                } else {
                    isVerify = true;
                    nameInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    isVerify = false;
                    showInputErrorMsgs(Constant.InputLayoutEntry.PARK_ADDRESS, "地址不能为空");
                } else {
                    isVerify = true;
                    addressInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        normalNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    isVerify = false;
                    showInputErrorMsgs(Constant.InputLayoutEntry.NORAMAL_NUN, "数量不能为空");
                } else {
                    isVerify = true;
                    normalNumInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        normalPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    isVerify = false;
                    showInputErrorMsgs(Constant.InputLayoutEntry.NORMAL_PRICE, "价格不能为空");
                } else {
                    isVerify = true;
                    normalPriceInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        chargingNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    isVerify = false;
                    showInputErrorMsgs(Constant.InputLayoutEntry.CHARGING_NUM, "数量不能为空");
                } else {
                    isVerify = true;
                    chargingNumInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        chargingPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    isVerify = false;
                    showInputErrorMsgs(Constant.InputLayoutEntry.CHARGING_PRICE, "价格不能为空");
                } else {
                    isVerify = true;
                    chargingPriceInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        remark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    isVerify = false;
                    showInputErrorMsgs(Constant.InputLayoutEntry.REMARK, "备注不能为空");
                } else {
                    isVerify = true;
                    remarkInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initView() {
        name = nameInputLayout.getEditText();
        address = addressInputLayout.getEditText();
        normalNum = normalNumInputLayout.getEditText();
        normalPrice = normalPriceInputLayout.getEditText();
        chargingNum = chargingNumInputLayout.getEditText();
        chargingPrice = chargingPriceInputLayout.getEditText();
        remark = remarkInputLayout.getEditText();
        toolbar.setTitle("添加停车场");
        toolbar.inflateMenu(R.menu.add_park_menu);
        setToolbar(toolbar, FRAGMENT_TYPE_SECONDE);
    }

    @Override
    public void toMyPark() {
        User user = getCurrentUser();
        Bundle bundle = new Bundle();
        bundle.putSerializable("newUser", user);
        setFragmentResult(RESULT_OK, bundle);
        pop();
    }

    @OnClick(R.id.mapf_get_address)
    public void getLocation() {
        addParkPresenter.getLocation();
    }

    @OnClick(R.id.mapf_park_add_image)
    public void choosePic() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_FROM_ALBUM);
    }

    @Override
    public void setImage(File file) {
        Glide.with(_mActivity).load(file).into(addImage);
        cancelImage.setVisibility(View.VISIBLE);
        imageToast.setVisibility(View.INVISIBLE);
        hasImage = true;
        imageFile = new BmobFile(file);
    }

    @OnClick(R.id.mapf_park_cancel_image)
    public void cancelPic() {
        Glide.with(_mActivity).load(R.drawable.add_pic).into(addImage);
        cancelImage.setVisibility(View.GONE);
        imageToast.setVisibility(View.VISIBLE);
        hasImage = false;
        imageFile = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_FROM_ALBUM:
                if (resultCode == RESULT_OK) {
                    addParkPresenter.getImage(data);
                }
                break;
        }
    }

    /**
     * 设置位置信息
     *
     * @param location 位置
     */
    @Override
    public void setLocation(final BDLocation location) {
        address.setText(location.getAddrStr() + location.getLocationDescribe());
        latitude.setText(String.valueOf(location.getLatitude()));
        longitude.setText(String.valueOf(location.getLongitude()));
        isVerify = true;
        latAndLon.setErrorEnabled(false);
    }

    /**
     * 检查输入框是否为空
     */
    private void checkEmpty() {
        if (name.getText().toString().isEmpty()) {
            isVerify = false;
            showInputErrorMsgs(Constant.InputLayoutEntry.PARK_NAME, "名称不能为空");
        }
        if (address.getText().toString().isEmpty()) {
            isVerify = false;
            showInputErrorMsgs(Constant.InputLayoutEntry.PARK_ADDRESS, "地址不能为空");
        }
        if (latitude.getText().toString().isEmpty() || longitude.getText().toString().isEmpty()) {
            isVerify = false;
            showInputErrorMsgs(Constant.InputLayoutEntry.LAT_LON, "点击“获取地址”获取经纬度");
        }
        if (normalNum.getText().toString().isEmpty()) {
            isVerify = false;
            showInputErrorMsgs(Constant.InputLayoutEntry.NORAMAL_NUN, "数量不能为空");
        }
        if (normalPrice.getText().toString().isEmpty()) {
            isVerify = false;
            showInputErrorMsgs(Constant.InputLayoutEntry.NORMAL_PRICE, "价格不能为空");
        }
        if (hasCharging.isChecked()) {
            if (chargingNum.getText().toString().isEmpty()) {
                isVerify = false;
                showInputErrorMsgs(Constant.InputLayoutEntry.CHARGING_NUM, "数量不能为空");
            }
            if (chargingPrice.getText().toString().isEmpty()) {
                isVerify = false;
                showInputErrorMsgs(Constant.InputLayoutEntry.CHARGING_PRICE, "价格不能为空");
            }
        }
        if (remark.getText().toString().isEmpty()) {
            isVerify = false;
            showInputErrorMsgs(Constant.InputLayoutEntry.REMARK, "备注不能为空");
        }

    }

    /**
     * 显示错误信息
     */
    @Override
    public void showInputErrorMsgs(int inputLayoutEntry, String message) {
        switch (inputLayoutEntry) {
            case Constant.InputLayoutEntry.PARK_NAME:
                nameInputLayout.setError(message);
                break;
            case Constant.InputLayoutEntry.PARK_ADDRESS:
                addressInputLayout.setError(message);
                break;
            case Constant.InputLayoutEntry.LAT_LON:
                latAndLon.setError(message);
                break;
            case Constant.InputLayoutEntry.NORAMAL_NUN:
                normalNumInputLayout.setError(message);
                break;
            case Constant.InputLayoutEntry.NORMAL_PRICE:
                normalPriceInputLayout.setError(message);
                break;
            case Constant.InputLayoutEntry.CHARGING_NUM:
                chargingNumInputLayout.setError(message);
                break;
            case Constant.InputLayoutEntry.CHARGING_PRICE:
                chargingPriceInputLayout.setError(message);
                break;
            case Constant.InputLayoutEntry.REMARK:
                remarkInputLayout.setError(message);
                break;
        }
    }


    @Override
    public void showDialog(String message) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(message);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    @Override
    public void hideDialog() {
        progressDialog.dismiss();
        progressDialog = null;
    }

    @Override
    public void showToast(String message) {
        toast(message);
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
