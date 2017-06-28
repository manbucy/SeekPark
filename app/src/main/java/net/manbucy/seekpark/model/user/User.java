package net.manbucy.seekpark.model.user;

import cn.bmob.v3.BmobUser;

/**
 * 用户类
 * 商家---停车场 1:1
 * Created by yang on 2017/6/22.
 */

public class User extends BmobUser {
    private boolean isMerchant;
    private boolean isCommitAuth;
    private boolean hasPark;
    private String parkId;

    public String getParkId() {
        return parkId;
    }

    public void setParkId(String parkId) {
        this.parkId = parkId;
    }

    public boolean isHasPark() {
        return hasPark;
    }

    public void setHasPark(boolean hasPark) {
        this.hasPark = hasPark;
    }

    public boolean isCommitAuth() {
        return isCommitAuth;
    }

    public void setCommitAuth(boolean commitAuth) {
        isCommitAuth = commitAuth;
    }

    public boolean isMerchant() {
        return isMerchant;
    }

    public void setMerchant(boolean merchant) {
        isMerchant = merchant;
    }

}
