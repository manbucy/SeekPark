package net.manbucy.seekpark.listener;

import java.util.List;

/**
 * 动态权限申请的回调接口
 * Created by yang on 2017/6/22.
 */

public interface PermissionListener {
    void onGranted();

    void onDenied(List<String> deniedPermission);
}
