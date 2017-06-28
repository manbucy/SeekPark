package net.manbucy.seekpark.model.user.source;

import android.content.Context;

import net.manbucy.seekpark.model.user.source.local.UserLocalSource;
import net.manbucy.seekpark.model.user.source.remote.UserRemoteSource;
import net.manbucy.seekpark.util.Utility;

/**
 * 注入用户数据仓库
 * Created by yang on 2017/6/23.
 */

public class Injection {
    public static UserRepository provideUserRepository(Context context){
        Utility.checkNotNull(context);
        return UserRepository.getInstance(UserLocalSource.getInstance(context),
                UserRemoteSource.getInstance());
    }
}
