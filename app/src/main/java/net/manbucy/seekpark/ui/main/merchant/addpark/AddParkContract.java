package net.manbucy.seekpark.ui.main.merchant.addpark;

import android.app.Activity;
import android.content.Intent;

import com.baidu.location.BDLocation;

import net.manbucy.seekpark.model.park.Park;
import net.manbucy.seekpark.model.user.User;
import net.manbucy.seekpark.ui.BasePresenter;
import net.manbucy.seekpark.ui.BaseView;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;

/**
 * AddParkContract
 * Created by yang on 2017/6/28.
 */

interface AddParkContract {
    interface View extends BaseView<Presenter> {
        void toMyPark();
        void showInputErrorMsgs(int inputLayoutEntry, String message);

        void setLocation(final BDLocation location);

        void showDialog(String message);

        void hideDialog();

        void showToast(String message);

        void setImage(File file);

    }

    interface Presenter extends BasePresenter {
        void getLocation();

        void getImage(Intent data);

        void addPark(BmobFile imageFile, Park park,User oldUser);
    }
}
