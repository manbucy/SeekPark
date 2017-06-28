package net.manbucy.seekpark.ui.login;

import android.content.Intent;
import android.os.Bundle;

import net.manbucy.seekpark.R;
import net.manbucy.seekpark.common.BaseActivity;
import net.manbucy.seekpark.common.SeekPark;
import net.manbucy.seekpark.model.user.source.Injection;
import net.manbucy.seekpark.ui.login.findpwd.FindPwdFragment;
import net.manbucy.seekpark.ui.login.findpwd.FindPwdPresenter;
import net.manbucy.seekpark.ui.login.login.LoginFragment;
import net.manbucy.seekpark.ui.login.login.LoginPresenter;
import net.manbucy.seekpark.ui.login.register.RegisterFragment;
import net.manbucy.seekpark.ui.login.register.RegisterPresenter;
import net.manbucy.seekpark.ui.main.MainActivity;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginFragment loginFragment;
        if (savedInstanceState == null){
            loginFragment = LoginFragment.newInstance();
            loadRootFragment(R.id.contentFrame, loginFragment);
            new LoginPresenter(Injection.provideUserRepository(SeekPark.getmContext()),loginFragment);
        }
    }

    public void startRegister(){
        RegisterFragment registerFragment = RegisterFragment.getInstance();
        start(registerFragment);
        new RegisterPresenter(Injection.provideUserRepository(SeekPark.getmContext()),registerFragment);
    }
    public void startFindPwd(){
        FindPwdFragment findPwdFragment = FindPwdFragment.getInstance();
        start(findPwdFragment);
        new FindPwdPresenter(Injection.provideUserRepository(SeekPark.getmContext()),findPwdFragment);
    }

    /**
     * 跳转到主界面
     */
    public void toMainActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    /**
     * 返回键 事件处理 若Fragment栈里面还有Fragment则pop出栈
     * 否则 按两次退出程序
     */
    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            pressBack();
        }
    }
}
