package net.manbucy.seekpark.model.user.source;

import net.manbucy.seekpark.model.ModelCallback;
import net.manbucy.seekpark.model.user.User;
import net.manbucy.seekpark.model.user.UserLoginInfo;

/**
 * UserRepository 方法接口?
 * Created by yang on 2017/6/22.
 */

public interface UserInfoSource {

    interface loginCallback {
        void onLoginSuccessful(User user);

        void onLoginFailing();
    }

    void login(String username, String password, loginCallback callback);

    UserLoginInfo queryLocalUserRecord();

    void saveLocalUserRecord(UserLoginInfo userLoginInfo);

    void queryUserByName(String username, ModelCallback.Query<User> callback);

    void queryUserByPhone(String phone, ModelCallback.Query<User> callback);

    void sendSMSCode(String phone, final ModelCallback.Normal callback);

    void singOrLogin(User user, String verifyCode, final UserInfoSource.loginCallback callback);
    void resetPassword(String password, String verifyCode, final ModelCallback.Normal callback);
    void updateUser(User newUser, User oldUser, final ModelCallback.Normal callback);
}
