package net.manbucy.seekpark.ui.login.findpwd;

import net.manbucy.seekpark.ui.BasePresenter;
import net.manbucy.seekpark.ui.BaseView;

/**
 * FindPassword View---Presenter
 * Created by yang on 2017/6/25.
 */

interface FindPwdContract {
    interface View extends BaseView<Presenter> {
        void showDialog(String message);

        void hideDialog();

        void showToast(String message);

        void showInputErrorMsgs(int inputLayoutEntry, String message);

        void startTimer();

        void toLogin();
    }

    interface Presenter extends BasePresenter {
        void sendVerifyCode(String phone);

        void resetPassword(String password, String verifyCode);
    }
}
