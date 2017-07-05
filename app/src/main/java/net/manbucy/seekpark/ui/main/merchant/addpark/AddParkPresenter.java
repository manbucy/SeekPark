package net.manbucy.seekpark.ui.main.merchant.addpark;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.baidu.location.BDLocation;

import net.manbucy.seekpark.common.SeekPark;
import net.manbucy.seekpark.model.ModelCallback;
import net.manbucy.seekpark.model.park.Park;
import net.manbucy.seekpark.model.park.source.ParkRepository;
import net.manbucy.seekpark.model.user.User;
import net.manbucy.seekpark.model.user.source.UserRepository;
import net.manbucy.seekpark.util.LocationUtil;
import net.manbucy.seekpark.util.image.Luban;
import net.manbucy.seekpark.util.image.OnCompressListener;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;

/**
 * AddParkPresenter
 * Created by yang on 2017/6/28.
 */

public class AddParkPresenter implements AddParkContract.Presenter {
    private AddParkContract.View addParkView;
    private ParkRepository parkRepository;
    private UserRepository userRepository;
    private Handler handler = new Handler();

    public AddParkPresenter(AddParkContract.View addParkView, ParkRepository parkRepository,
                            UserRepository userRepository) {
        this.addParkView = addParkView;
        this.parkRepository = parkRepository;
        this.userRepository = userRepository;
        this.addParkView.setPresenter(this);
    }

    @Override
    public void getLocation() {
        addParkView.showDialog("正在定位...");
        LocationUtil.getLocation(SeekPark.getmContext());
        LocationUtil.setMyLocationListener(new LocationUtil.MyLocationListener() {
            @Override
            public void locateSucceed(final BDLocation bdLocation) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        addParkView.hideDialog();
                        addParkView.setLocation(bdLocation);
                    }
                });
            }

            @Override
            public void locateFailed() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        addParkView.hideDialog();
                        addParkView.showToast("获取定位失败");
                    }
                });
            }
        });
    }

    @Override
    public void addPark(final BmobFile imageFile, final Park park, final User oldUser) {
        addParkView.showDialog("正在提交...");
        parkRepository.uploadFile(imageFile, new ModelCallback.Normal() {
            @Override
            public void onSucceed() {
                park.setImageUrl(imageFile.getUrl());
                saveParkInfo(park, oldUser);
            }

            @Override
            public void onFailed() {
                addParkView.hideDialog();
                addParkView.showToast("上传图片失败");
            }
        });
    }

    private void saveParkInfo(final Park park, final User oldUser) {
        parkRepository.saveParkIno(park, new ModelCallback.Normal() {
            @Override
            public void onSucceed() {
                User newUser = new User();
                newUser.setParkId(park.getObjectId());
                newUser.setMerchant(true);
                newUser.setCommitAuth(true);
                newUser.setHasPark(true);
                updateUserInfo(newUser, oldUser);
            }

            @Override
            public void onFailed() {
                addParkView.hideDialog();
                addParkView.showToast("保存park信息失败");
            }
        });
    }

    private void updateUserInfo(User newUser, User oldUser) {
        userRepository.updateUser(newUser, oldUser, new ModelCallback.Normal() {
            @Override
            public void onSucceed() {
                addParkView.hideDialog();
                addParkView.toMyPark();
            }

            @Override
            public void onFailed() {
                addParkView.hideDialog();
                addParkView.showToast("更新User信息失败");
            }
        });
    }

    @Override
    public void getImage(Intent data) {
        String imagePath = handleImageOnKitKat(data);
        final File file = new File(imagePath);
        Luban.get(SeekPark.getmContext())
                .load(file)
                .putGear(Luban.THIRD_GEAR)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        addParkView.setImage(file);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }).launch();
    }

    /**
     * 获取 data 选择的照片的路径
     *
     * @param data
     * @return
     */
    private String handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(SeekPark.getmContext(), uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.provider.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.provider.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downlodas"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    /**
     * 根据 Uri 获取照片路径
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = SeekPark.getmContext().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    public void start() {

    }

    @Override
    public void detachView(Object o) {
        addParkView = null;
    }
}
