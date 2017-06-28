package net.manbucy.seekpark.ui.main.merchant.searchpark;

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
 * SearchParkFragment
 * Created by yang on 2017/6/28.
 */

public class SearchParkFragment extends BaseFragment implements SearchParkContract.View {
    private SearchParkContract.Presenter searchParkPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static SearchParkFragment newInstance(){
        return new SearchParkFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.search_park_fragment,container,false);
        ButterKnife.bind(this,root);
        initView();
        return root;
    }

    private void initView() {
        toolbar.setTitle("寻找停车场");
        setToolbar(toolbar,FRAGMENT_TYPE_FIRST);
    }

    @Override
    public void setPresenter(SearchParkContract.Presenter presenter) {
        searchParkPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        searchParkPresenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        searchParkPresenter.detachView(this);
    }
}
