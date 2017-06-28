package net.manbucy.seekpark.ui.splash;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import net.manbucy.seekpark.R;
import net.manbucy.seekpark.common.BaseActivity;
import net.manbucy.seekpark.listener.PermissionListener;
import net.manbucy.seekpark.model.user.User;
import net.manbucy.seekpark.ui.login.LoginActivity;
import net.manbucy.seekpark.ui.main.MainActivity;
import net.manbucy.seekpark.util.LogUtil;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * <h6>Splash欢迎界面</h6><br/>
 * BmobSDK的初始化，必要危险权限的动态申请
 */
public class SplashActivity extends BaseActivity {
    //要申请的危险权限
    public static String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
    //BmobSDK的
    private static final String APPID = "59757585c529b8034ceefe596f904e41";
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏透明
        if (Build.VERSION.SDK_INT >= 21) {
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_splash);
        //初始化 Bmob的SDKID
        Bmob.initialize(this, APPID);
        //动态申请权限
        requestRuntimePermission(permissions, new PermissionListener() {
            @Override
            public void onGranted() {
                LogUtil.d("permission", "onGranted");
                //获取本地缓存用户
                final User localUser = BmobUser.getCurrentUser(User.class);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toLoginOrSeekPark(localUser);
                    }
                }, 500);
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                LogUtil.d("permission", "onDenied");
                toast("必须授予所有权限才能正常使用,即将退出。。。");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);
            }
        });

    }

    /**
     * 跳转到 LoginActivity登录界面 或者 SeekParkActivity界面
     *
     * @param localUser 本地用户<br>
     *                  not null: to SeekPark <br>
     *                  null: to Login
     */
    private void toLoginOrSeekPark(User localUser) {
        if (localUser != null) {
            Intent intent;
            intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }

}
