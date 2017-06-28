package net.manbucy.seekpark.ui.login.register;

import net.manbucy.seekpark.common.Constant;
import net.manbucy.seekpark.model.ModelCallback;
import net.manbucy.seekpark.model.user.User;
import net.manbucy.seekpark.model.user.source.UserInfoSource;
import net.manbucy.seekpark.model.user.source.UserRepository;
import net.manbucy.seekpark.util.Utility;

/**
 * RegisterPresenter
 * Created by yang on 2017/6/24.
 */

public class RegisterPresenter implements RegisterContract.Presenter {
    private UserRepository userRepository;
    private RegisterContract.View registerView;

    public RegisterPresenter(UserRepository userRepository, RegisterContract.View registerView) {
        this.userRepository = Utility.checkNotNull(userRepository);
        this.registerView = Utility.checkNotNull(registerView);
        registerView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void sendVerifyCode(final String phone) {
        registerView.showDialog("正在获取验证码");
        userRepository.queryUserByPhone(phone, new ModelCallback.Query<User>() {
            @Override
            public void onSucceed(User var) {
                if (var == null) {
                    requestVerifyCode(phone);
                } else {
                    registerView.hideDialog();
                    registerView.showInputErrorMsgs(Constant.InputLayoutEntry.PHONE_INPUT,
                            "该手机号码已注册");
                }
            }

            @Override
            public void onFailed() {
                registerView.hideDialog();
                registerView.showToast("出错啦-_-!!!");
            }
        });
    }

    private void requestVerifyCode(String phone) {
        userRepository.sendSMSCode(phone, new ModelCallback.Normal() {
            @Override
            public void onSucceed() {
                registerView.hideDialog();
                registerView.showToast("验证码已发送，请注意查收");
                registerView.startTimer();
            }

            @Override
            public void onFailed() {
                registerView.hideDialog();
                registerView.showToast("出错啦-_-!!!");
            }
        });
    }

    @Override
    public void checkUsername(String username) {
        registerView.showDialog("正在检测用户名");
        userRepository.queryUserByName(username, new ModelCallback.Query<User>() {
            @Override
            public void onSucceed(User var) {
                if (var != null){
                    registerView.hideDialog();
                    registerView.showInputErrorMsgs(Constant.InputLayoutEntry.USERNAME_INPUT,
                            "该名称已被注册，换一个试一试吧");
                    registerView.setIsVerify(false);
                }else{
                    registerView.hideDialog();
                    registerView.showInputErrorMsgs(Constant.InputLayoutEntry.USERNAME_INPUT,
                            "该名称未被注册,你可使用它");
                }
            }

            @Override
            public void onFailed() {
                registerView.hideDialog();
                registerView.showInputErrorMsgs(Constant.InputLayoutEntry.USERNAME_INPUT,
                        "检测失败");
                registerView.setIsVerify(false);
            }
        });
    }

    @Override
    public void singOrLogin(User user, String verifyCode) {
        registerView.showDialog("正在注册并登录");
        userRepository.singOrLogin(user, verifyCode, new UserInfoSource.loginCallback() {
            @Override
            public void onLoginSuccessful(User user) {
                registerView.hideDialog();
                registerView.showToast("登录成功");
                registerView.toMainActivity();
            }

            @Override
            public void onLoginFailing() {
                registerView.hideDialog();
                registerView.showToast("验证码错误");
                registerView.showInputErrorMsgs(Constant.InputLayoutEntry.VERIFY_INPUT,
                        "请输入正确的验证码");
            }
        });
    }

    @Override
    public void detachView(Object o) {
        registerView = null;
    }
}
