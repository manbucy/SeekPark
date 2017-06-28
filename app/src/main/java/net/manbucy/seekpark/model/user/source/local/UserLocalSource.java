package net.manbucy.seekpark.model.user.source.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import net.manbucy.seekpark.model.user.UserLoginInfo;
import net.manbucy.seekpark.util.KeyStoreUtil;

/**
 * 本地用户数据
 * Created by yang on 2017/6/23.
 */

public class UserLocalSource {
    private static UserLocalSource INSTANCE = null;
    private static final String PASSWORD_KEYSTORE_ALIAS = "net.manbucy.seekpark.password";
    private Context context;
    private SharedPreferences preferences;


    public static UserLocalSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UserLocalSource(context);
        }
        return INSTANCE;
    }

    private UserLocalSource(Context context) {
        this.context = context;
    }

    /**
     * 查询本地存储的用户信息
     * 将查询的到密码解密
     *
     * @return UserLoginInfo
     */
    public UserLoginInfo queryUserInfo() {
        KeyStoreUtil.createNewKeys(PASSWORD_KEYSTORE_ALIAS);
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isRemember = preferences.getBoolean("is_remember", false);
        //存在已记住密码
        if (isRemember) {
            userLoginInfo.setRemember(true);
            userLoginInfo.setUsername(preferences.getString("account", ""));
            String cipherPassword = preferences.getString("password", "");
            //保存的不密码为空
            if (!cipherPassword.isEmpty()) {
                String password = KeyStoreUtil.decryptString(PASSWORD_KEYSTORE_ALIAS, cipherPassword);
                userLoginInfo.setPassword(password);
            } else {
                userLoginInfo.setPassword("");
            }
        } else {
            userLoginInfo.setUsername("");
            userLoginInfo.setPassword("");
            userLoginInfo.setRemember(false);
        }
        preferences = null;
        return userLoginInfo;
    }

    /**
     * 保存用户登录信息到本地
     * @param userLoginInfo 用户登录信息
     */
    public void saveUserInfo(UserLoginInfo userLoginInfo) {
        KeyStoreUtil.createNewKeys(PASSWORD_KEYSTORE_ALIAS);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        if (userLoginInfo.isRemember()){
            editor.putBoolean("is_remember",true);
            editor.putString("account",userLoginInfo.getUsername());
            String password = KeyStoreUtil.encryptString(PASSWORD_KEYSTORE_ALIAS,
                    userLoginInfo.getPassword());
            editor.putString("password",password);
        }else{
            editor.clear();
        }
        editor.apply();
        preferences = null;
    }
}
