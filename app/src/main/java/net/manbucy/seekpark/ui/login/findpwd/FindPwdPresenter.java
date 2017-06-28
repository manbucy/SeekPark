package net.manbucy.seekpark.ui.login.findpwd;

import android.content.Context;

import net.manbucy.seekpark.common.Constant;
import net.manbucy.seekpark.model.ModelCallback;
import net.manbucy.seekpark.model.user.User;
import net.manbucy.seekpark.model.user.source.UserRepository;
import net.manbucy.seekpark.util.Utility;

/**
 * FindPwdPresenter
 * Created by yang on 2017/6/25.
 */

public class FindPwdPresenter implements FindPwdContract.Presenter {
    private FindPwdContract.View findPwdView;
    private UserRepository userRepository;

    public FindPwdPresenter(UserRepository userRepository, FindPwdContract.View findPwdView) {
        this.findPwdView = Utility.checkNotNull(findPwdView);
        this.userRepository = Utility.checkNotNull(userRepository);
        findPwdView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void sendVerifyCode(final String phone) {
        findPwdView.showDialog("正在获取验证码");
        userRepository.queryUserByPhone(phone, new ModelCallback.Query<User>() {
            @Override
            public void onSucceed(User var) {
                if (var != null) {
                    requestVerifyCode(phone);
                } else {
                    findPwdView.hideDialog();
                    findPwdView.showInputErrorMsgs(Constant.InputLayoutEntry.PHONE_INPUT,
                            "该手机号码未注册");
                }
            }

            @Override
            public void onFailed() {
                findPwdView.hideDialog();
                findPwdView.showToast("出错啦-_-!!!");
            }
        });
    }

    private void requestVerifyCode(String phone) {
        userRepository.sendSMSCode(phone, new ModelCallback.Normal() {
            @Override
            public void onSucceed() {
                findPwdView.hideDialog();
                findPwdView.showToast("验证码已发送，请注意查收");
                findPwdView.startTimer();
            }

            @Override
            public void onFailed() {
                findPwdView.hideDialog();
                findPwdView.showToast("出错啦-_-!!!");
            }
        });
    }

    @Override
    public void resetPassword(String password, String verifyCode) {
        findPwdView.showDialog("正在重置密码");
        userRepository.resetPassword(password, verifyCode, new ModelCallback.Normal() {
            @Override
            public void onSucceed() {
                findPwdView.hideDialog();
                findPwdView.showToast("重置密码成功");
                findPwdView.toLogin();
            }

            @Override
            public void onFailed() {
                findPwdView.hideDialog();
                findPwdView.showToast("重置失败");
            }
        });
    }

    @Override
    public void detachView(Object o) {
        findPwdView = null;
    }
}
