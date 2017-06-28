package net.manbucy.seekpark.model.user.source.remote;

import net.manbucy.seekpark.model.ModelCallback;
import net.manbucy.seekpark.model.user.User;
import net.manbucy.seekpark.model.user.source.UserInfoSource;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 远端数据
 * Created by yang on 2017/6/23.
 */

public class UserRemoteSource {
    private static UserRemoteSource INSTANCE = null;

    public static UserRemoteSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRemoteSource();
        }
        return INSTANCE;
    }

    private UserRemoteSource() {

    }

    /**
     * 进行登录
     *
     * @param username 用户名
     * @param password 密码
     * @param callback 回调接口
     */
    public void login(String username, String password, final UserInfoSource.loginCallback callback) {
        BmobUser.loginByAccount(username, password, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    callback.onLoginSuccessful(user);
                } else {
                    callback.onLoginFailing();
                }
            }
        });
    }

    /**
     * 手机号码不允许重复
     * 根据手机号码查找对应的用户
     *
     * @param phone 手机号码
     */
    public void queryUserByPhone(String phone, final ModelCallback.Query<User> callback) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("mobilePhoneNumber", phone);
        query.setLimit(1);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    callback.onSucceed(list.size() > 0 ? list.get(0) : null);
                } else {
                    callback.onFailed();
                }
            }
        });
    }

    /**
     * 用户名不允许重复
     * 根据用户名查找对应的用户
     *
     * @param username 用户名
     */
    public void queryUserByName(String username, final ModelCallback.Query<User> callback) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("username", username);
        query.setLimit(1);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    callback.onSucceed(list.size() > 0 ? list.get(0) : null);
                } else {
                    callback.onFailed();
                }
            }
        });
    }

    /**
     * 发送验证码
     *
     * @param phone    手机号码
     * @param callback 回调接口
     */
    public void sendSMSCode(String phone, final ModelCallback.Normal callback) {
        BmobSMS.requestSMSCode(phone, "SP注册", new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e ==null){
                    callback.onSucceed();
                }else{
                    callback.onFailed();
                }
            }
        });
    }

    /**
     * 通过手机号码注册的同时登录
     * @param user 要登陆的账号 里面号含有手机号码(必填) 用户民(默认为手机号码) 密码
     * @param verifyCode 获取到的验证码
     * @param callback 回调接口
     */
    public void singOrLogin(User user, String verifyCode, final UserInfoSource.loginCallback callback) {
        user.signOrLogin(verifyCode, new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null){
                    callback.onLoginSuccessful(user);
                }else{
                    callback.onLoginFailing();
                }
            }
        });
    }

    /**
     * 重置密码
     * @param password 新密码
     * @param verifyCode 验证码
     * @param callback 回调接口
     */
    public void resetPassword(String password, String verifyCode, final ModelCallback.Normal callback){
        BmobUser.resetPasswordBySMSCode(verifyCode, password, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e ==null){
                    callback.onSucceed();
                }else{
                    callback.onFailed();
                }
            }
        });

    }

}
