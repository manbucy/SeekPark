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
import net.manbucy.seekpark.ui.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

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
    public void onBackPressedSupport() {
        pressBack();
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
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
                        toast("寻找停车场");
                        break;
                    case R.id.nav_order_park:
                        toast("我的预约");
                        break;
                    case R.id.nav_history_order:
                        toast("我的历史订单");
                        break;
                    case R.id.nav_my_park:
                        toast("我的停车场");
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
