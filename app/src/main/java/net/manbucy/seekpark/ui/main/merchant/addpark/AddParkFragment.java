package net.manbucy.seekpark.ui.main.merchant.addpark;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.manbucy.seekpark.R;
import net.manbucy.seekpark.common.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * AddParkFragment
 * Created by yang on 2017/6/28.
 */

public class AddParkFragment extends BaseFragment implements AddParkContract.View {
    private AddParkContract.Presenter addParkPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    public static AddParkFragment getInstance(){
        return new AddParkFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.merchant_add_park_fragment,container,false);
        ButterKnife.bind(this,root);
        initView();
        return root;
    }

    private void initView() {
        toolbar.setTitle("添加停车场");
        setToolbar(toolbar,FRAGMENT_TYPE_SECONDE);
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
