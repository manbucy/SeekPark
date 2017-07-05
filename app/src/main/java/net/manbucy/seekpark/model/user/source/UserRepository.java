package net.manbucy.seekpark.model.user.source;

import net.manbucy.seekpark.model.ModelCallback;
import net.manbucy.seekpark.model.user.User;
import net.manbucy.seekpark.model.user.UserLoginInfo;
import net.manbucy.seekpark.model.user.source.local.UserLocalSource;
import net.manbucy.seekpark.model.user.source.remote.UserRemoteSource;

/**
 * 用户数据仓库
 * Created by yang on 2017/6/23.
 */

public class UserRepository implements UserInfoSource {
    private static UserRepository INSTANCE = null;

    private final UserLocalSource userLocalSource;
    private final UserRemoteSource userRemoteSource;

    public static UserRepository getInstance(UserLocalSource userLocalSource,
                                             UserRemoteSource userRemoteSource) {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository(userLocalSource, userRemoteSource);
        }
        return INSTANCE;
    }

    private UserRepository(UserLocalSource userLocalSource, UserRemoteSource userRemoteSource) {
        this.userLocalSource = userLocalSource;
        this.userRemoteSource = userRemoteSource;
    }

    /**
     * 用来强制创建一个新的UserRepository Instance
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * 进行登录
     *
     * @param username 用户名
     * @param password 密码
     * @param callback 回调接口
     */
    @Override
    public void login(String username, String password, final loginCallback callback) {
        userRemoteSource.login(username, password, callback);
    }

    /**
     * 查询本地存储的用户信息
     *
     * @return UserLoginInfo
     */
    @Override
    public UserLoginInfo queryLocalUserRecord() {
        return userLocalSource.queryUserInfo();
    }

    /**
     * 保存用户登录信息到本地
     *
     * @param userLoginInfo 用户登录信息
     */
    @Override
    public void saveLocalUserRecord(UserLoginInfo userLoginInfo) {
        userLocalSource.saveUserInfo(userLoginInfo);
    }

    /**
     * 用户名不允许重复
     * 根据用户名查找对应的用户
     *
     * @param username 用户名
     */
    @Override
    public void queryUserByName(String username, ModelCallback.Query<User> callback) {
        userRemoteSource.queryUserByName(username, callback);
    }

    /**
     * 手机号码不允许重复
     * 根据手机号码查找对应的用户
     *
     * @param phone 手机号码
     */
    @Override
    public void queryUserByPhone(String phone, ModelCallback.Query<User> callback) {
        userRemoteSource.queryUserByPhone(phone, callback);
    }

    /**
     * 发送验证码
     *
     * @param phone    手机号码
     * @param callback 回调接口
     */
    @Override
    public void sendSMSCode(String phone, ModelCallback.Normal callback) {
        userRemoteSource.sendSMSCode(phone, callback);
    }

    /**
     * 通过手机号码注册的同时登录
     *
     * @param user       要登陆的账号 里面号含有手机号码(必填) 用户民(默认为手机号码) 密码
     * @param verifyCode 获取到的验证码
     * @param callback   回调接口
     */
    @Override
    public void singOrLogin(User user, String verifyCode, loginCallback callback) {
        userRemoteSource.singOrLogin(user, verifyCode, callback);
    }

    /**
     * 重置密码
     *
     * @param password   新密码
     * @param verifyCode 验证码
     * @param callback   回调接口
     */
    @Override
    public void resetPassword(String password, String verifyCode, ModelCallback.Normal callback) {
        userRemoteSource.resetPassword(password, verifyCode, callback);
    }
    /**
     * 更新User
     * @param newUser 新User 里面可以只包含新的字段
     * @param oldUser 旧User
     * @param callback 回调
     */
    @Override
    public void updateUser(User newUser, User oldUser, ModelCallback.Normal callback) {
        userRemoteSource.updateUser(newUser,oldUser,callback);
    }
}
