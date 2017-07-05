package net.manbucy.seekpark.ui.main.searchpark.parklist;

import com.baidu.location.BDLocation;

import net.manbucy.seekpark.common.SeekPark;
import net.manbucy.seekpark.model.ModelCallback;
import net.manbucy.seekpark.model.park.Park;
import net.manbucy.seekpark.model.park.source.ParkRepository;
import net.manbucy.seekpark.util.LocationUtil;

import java.util.List;

import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * SearchParkPresenter
 * Created by yang on 2017/6/28.
 */

public class SearchParkPresenter implements SearchParkContract.Presenter {
    private SearchParkContract.View searchParkView;
    private ParkRepository parkRepository;

    public SearchParkPresenter(SearchParkContract.View searchParkView, ParkRepository parkRepository) {
        this.searchParkView = searchParkView;
        this.parkRepository = parkRepository;
        this.searchParkView.setPresenter(this);
    }

    @Override
    public void findPark() {
        LocationUtil.getLocation(SeekPark.getmContext());
        LocationUtil.setMyLocationListener(new LocationUtil.MyLocationListener() {
            @Override
            public void locateSucceed(final BDLocation bdLocation) {
                BmobGeoPoint bmobGeoPoint = new BmobGeoPoint(bdLocation.getLongitude(),
                        bdLocation.getLatitude());
                parkRepository.findParkByLocation(bmobGeoPoint, new ModelCallback.Query<List<Park>>() {
                    @Override
                    public void onSucceed(List<Park> var) {
                        searchParkView.loadData(var);
                    }

                    @Override
                    public void onFailed() {
                        searchParkView.showToast("获取失败");
                        searchParkView.setRefreshing(false);
                    }
                });
            }

            @Override
            public void locateFailed() {
                searchParkView.showToast("定位失败");
                searchParkView.setRefreshing(false);
            }
        });
    }
    @Override
    public void start() {

    }

    @Override
    public void detachView(Object o) {
        searchParkView = null;
    }
}
