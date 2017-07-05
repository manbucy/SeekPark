package net.manbucy.seekpark.ui.main.searchpark.parklist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.manbucy.seekpark.R;
import net.manbucy.seekpark.common.BaseFragment;
import net.manbucy.seekpark.model.park.Park;
import net.manbucy.seekpark.model.park.source.ParkRepository;
import net.manbucy.seekpark.model.park.source.remote.ParkRemoteSource;
import net.manbucy.seekpark.ui.main.searchpark.parkinfo.ParkInfoFragment;
import net.manbucy.seekpark.ui.main.searchpark.parkinfo.ParkInfoPresenter;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.spf_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.spf_recycle)
    RecyclerView recyclerView;
    List<Park> parks = new ArrayList<>();
    ParkAdapter parkAdapter;

    public static SearchParkFragment newInstance() {
        return new SearchParkFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.search_park_fragment, container, false);
        ButterKnife.bind(this, root);
        initView();
        initListener();
        return root;
    }

    private void initListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchParkPresenter.findPark();
            }
        });
    }

    private void initView() {
        toolbar.setTitle("寻找停车场");
        setToolbar(toolbar, FRAGMENT_TYPE_FIRST);
        setRefreshing(true);
        parkAdapter = new ParkAdapter(parks, R.layout.park_info_item, new ParkAdapter.ClickListener() {
            @Override
            public void onClickListener(Park park) {
//                toast(park.getName());
                ParkInfoFragment parkInfoFragment = ParkInfoFragment.newInstance(park);
                start(parkInfoFragment,SINGLETASK);
                new ParkInfoPresenter(ParkRepository.getInstance(ParkRemoteSource.getInstance()),
                        parkInfoFragment);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));//这里用线性显示 类似于listview
        recyclerView.setAdapter(parkAdapter);
        searchParkPresenter.findPark();
    }

    @Override
    public void showToast(String message) {
        toast(message);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        swipeRefreshLayout.setRefreshing(refreshing);
    }

    @Override
    public void loadData(List<Park> parkList) {
        parks.clear();
        for (Park park : parkList) {
            parks.add(park);
        }
//        adapter.notifyDataSetChanged();
        parkAdapter.notifyDataSetChanged();
        setRefreshing(false);
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
