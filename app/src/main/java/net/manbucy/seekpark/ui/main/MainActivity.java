package net.manbucy.seekpark.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import net.manbucy.seekpark.R;
import net.manbucy.seekpark.common.BaseActivity;
import net.manbucy.seekpark.listener.DrawerLayoutListener;
import net.manbucy.seekpark.model.park.source.ParkRepository;
import net.manbucy.seekpark.model.park.source.remote.ParkRemoteSource;
import net.manbucy.seekpark.ui.login.LoginActivity;
import net.manbucy.seekpark.ui.main.merchant.parkinfo.ParkInfoFragment;
import net.manbucy.seekpark.ui.main.merchant.parkinfo.ParkInfoPresenter;
import net.manbucy.seekpark.ui.main.merchant.searchpark.SearchParkFragment;
import net.manbucy.seekpark.ui.main.merchant.searchpark.SearchParkPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity implements DrawerLayoutListener, NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.am_draw_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.am_nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState == null){
            SearchParkFragment searchParkFragment = SearchParkFragment.newInstance();
            loadRootFragment(R.id.contentFrame,searchParkFragment);
            new SearchParkPresenter(searchParkFragment,
                    ParkRepository.getIntance(ParkRemoteSource.getInstance()));
        }
        initView();
    }

    private void initView() {
        if (getCurrentUser().isMerchant()) {
            navigationView.inflateMenu(R.menu.nav_menu_merchant);
        } else {
            navigationView.inflateMenu(R.menu.nav_menu_customer);
        }
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onLock(boolean lock) {
        if (lock) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    @Override
    public void onOpen() {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        drawerLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (item.getItemId()) {
                    case R.id.nav_search_park:
                        SearchParkFragment searchParkFragment = findFragment(SearchParkFragment.class);
                        start(searchParkFragment, SupportFragment.SINGLETASK);
                        new SearchParkPresenter(searchParkFragment,
                                ParkRepository.getIntance(ParkRemoteSource.getInstance()));
                        break;
                    case R.id.nav_order_park:
                        toast("我的预约");
                        break;
                    case R.id.nav_history_order:
                        toast("我的历史订单");
                        break;
                    case R.id.nav_my_park:
                        ParkInfoFragment parkInfoFragment = findFragment(ParkInfoFragment.class);
                        if (parkInfoFragment == null) {
                            parkInfoFragment = ParkInfoFragment.newInstance();
                            start(parkInfoFragment);
                        } else {
                            start(parkInfoFragment, SupportFragment.SINGLETASK);
                        }
                        new ParkInfoPresenter(ParkRepository.getIntance(ParkRemoteSource.getInstance()),
                                parkInfoFragment);
                        break;
                    case R.id.nav_my_info:
                        toast("我的信息");
                        break;
                    case R.id.nav_login_out:
                        BmobUser.logOut();
                        Intent logOutIntent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(logOutIntent);
                        finish();
                        break;
                    default:
                }
            }
        }, 250);
        return true;
    }
}
