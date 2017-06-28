package net.manbucy.seekpark.ui.login.login;

import net.manbucy.seekpark.model.user.UserLoginInfo;
import net.manbucy.seekpark.ui.BasePresenter;
import net.manbucy.seekpark.ui.BaseView;

/**
 * LoginContract 定义了 View 与 Presenter 所遵守的规范(契约)
 * Created by yang on 2017/6/23.
 */

interface LoginContract {

    interface View extends BaseView<Presenter> {
        void setUserLoginInfo(UserLoginInfo userLoginInfo);

        void showInputErrorMsgs(int inputLayoutEntry,String message);

        void showDialog();

        void hideDialog();

        void showToast(String message);
        void toMainActivity();
    }

    interface Presenter extends BasePresenter {
        void queryLocalUserRecord();

        void login(UserLoginInfo userLoginInfo);
    }
}
