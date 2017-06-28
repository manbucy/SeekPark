package net.manbucy.seekpark.ui.login.login;

import android.content.Context;

import net.manbucy.seekpark.model.user.User;
import net.manbucy.seekpark.model.user.UserLoginInfo;
import net.manbucy.seekpark.model.user.source.UserInfoSource;
import net.manbucy.seekpark.model.user.source.UserRepository;
import net.manbucy.seekpark.util.Utility;

/**
 * 登录 视图---业务 中间件
 * Created by yang on 2017/6/23.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private UserRepository userRepository;
    private LoginContract.View loginView;

    public LoginPresenter(UserRepository userRepository, LoginContract.View loginView) {
        this.userRepository = Utility.checkNotNull(userRepository);
        this.loginView = Utility.checkNotNull(loginView);
        loginView.setPresenter(this);
    }

    @Override
    public void start() {
        queryLocalUserRecord();
    }

    @Override
    public void login(final UserLoginInfo userLoginInfo) {
        loginView.showDialog();
        userRepository.login(userLoginInfo.getUsername(), userLoginInfo.getPassword(),
                new UserInfoSource.loginCallback() {
            @Override
            public void onLoginSuccessful(User user) {
                saveLocalUserRecord(userLoginInfo);
                loginView.toMainActivity();
                loginView.hideDialog();
                loginView.showToast("登录成功");
            }

            @Override
            public void onLoginFailing() {
                loginView.hideDialog();
                loginView.showToast("登录失败");
            }
        });
    }

    @Override
    public void queryLocalUserRecord() {
        UserLoginInfo userLoginInfo = userRepository.queryLocalUserRecord();
        if (userLoginInfo.isRemember()) {
            loginView.setUserLoginInfo(userLoginInfo);
        }
    }

    private void saveLocalUserRecord(UserLoginInfo userLoginInfo) {
        userRepository.saveLocalUserRecord(userLoginInfo);
    }

    @Override
    public void detachView(Object o) {
        loginView = null;
    }
}
