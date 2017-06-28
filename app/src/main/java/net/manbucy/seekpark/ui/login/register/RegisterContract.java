package net.manbucy.seekpark.ui.login.register;

import net.manbucy.seekpark.model.user.User;
import net.manbucy.seekpark.ui.BasePresenter;
import net.manbucy.seekpark.ui.BaseView;

/**
 * 注册 view presenter 所遵守的规范(契约)
 * Created by yang on 2017/6/24.
 */

interface RegisterContract {

    interface View extends BaseView<Presenter> {
        void showDialog(String message);

        void hideDialog();

        void showToast(String message);

        void showInputErrorMsgs(int inputLayoutEntry, String message);

        void startTimer();

        void setIsVerify(boolean isVerify);

        void toMainActivity();
    }

    interface Presenter extends BasePresenter {
        void sendVerifyCode(String phone);

        void checkUsername(String username);

        void singOrLogin(User user, String verifyCode);
    }
}
