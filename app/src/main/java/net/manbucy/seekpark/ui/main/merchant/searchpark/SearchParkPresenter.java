package net.manbucy.seekpark.ui.main.merchant.searchpark;

import net.manbucy.seekpark.model.park.source.ParkRepository;

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
    public void start() {

    }

    @Override
    public void detachView(Object o) {
        searchParkView = null;
    }
}
