package net.manbucy.seekpark.ui.main.searchpark.parkinfo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.navisdk.adapter.BNCommonSettingParam;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * NavigationService
 * Created by yang on 2017/6/21.
 */

public class NavigationService {
    private static String TAG = "navigationService";
    private Activity mActivity;
    private ProgressDialog progressDialog;
    private String mSDCardPath = null;
    private static final String APP_FOLDER_NAME = "SearchPark";
    public static List<Activity> activityList = new LinkedList<>();
    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    private BDLocation bdLocation;
    private BmobGeoPoint bmobGeoPoint;

    public NavigationService(Activity mActivity) {
        this.mActivity = mActivity;
        activityList.add(mActivity);
    }

    public void initNavigation() {
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("正在定位。。。");
        if (initDirs()) {
            initNavi();
        }
    }

    public void startNavigation(BDLocation bdLocation, BmobGeoPoint bmobGeoPoint) {
        this.bdLocation = bdLocation;
        this.bmobGeoPoint = bmobGeoPoint;
        initNavigation();
    }
    private void start(){
        routeplanToNavi(bdLocation, true, bmobGeoPoint);
    }

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    String authinfo = null;
    /**
     * 内部TTS播报状态回传handler
     */
    private Handler ttsHandler = new Handler() {
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
                    Log.d(TAG, "handleMessage: "+"Handler : TTS play start");
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                    Log.d(TAG, "handleMessage: "+"Handler : TTS play end");
                    break;
                }
                default:
                    break;
            }
        }
    };
    /**
     * 内部TTS播报状态回调接口
     */
    private BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {

        @Override
        public void playEnd() {
            Log.d(TAG, "playEnd: "+"TTSPlayStateListener : TTS play end");
        }

        @Override
        public void playStart() {

        }
    };

    private void initNavi() {

        Bundle bundle = new Bundle();
        // 必须设置APPID，否则会静音
        bundle.putString(BNCommonSettingParam.TTS_APP_ID, "9833358");
        BNaviSettingManager.setNaviSdkParam(bundle);

        BNOuterTTSPlayerCallback ttsCallback = null;

        BaiduNaviManager.getInstance().init(mActivity, mSDCardPath, APP_FOLDER_NAME,
                new BaiduNaviManager.NaviInitListener() {
                    @Override
                    public void onAuthResult(int status, String msg) {
                        if (0 == status) {
                            authinfo = "key校验成功!";
                        } else {
                            authinfo = "key校验失败, " + msg;
                        }
                        Log.d(TAG, "onAuthResult: "+authinfo);
                    }

                    public void initSuccess() {
                        Log.d(TAG, "initSuccess: 百度导航引擎初始化成功");
                        initSetting();
                        start();
                    }

                    public void initStart() {
                        Log.d(TAG, "initStart: 百度导航引擎初始化开始");
                    }

                    public void initFailed() {
                        Log.d(TAG, "initFailed: 百度导航引擎初始化失败");
                    }

                }, null, ttsHandler, ttsPlayStateListener);

    }

    private void initSetting() {
        // 设置是否双屏显示
        BNaviSettingManager.setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        // 设置导航播报模式
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        // 是否开启路况
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
    }

    private void routeplanToNavi(BDLocation myLocation, boolean isMock, BmobGeoPoint bmobGeoPoint) {
        BmobGeoPoint location = bmobGeoPoint;
        BNRoutePlanNode.CoordinateType coType = BNRoutePlanNode.CoordinateType.BD09LL;

        BNRoutePlanNode sNode = new BNRoutePlanNode(myLocation.getLongitude(), myLocation.getLatitude(), "我的位置", null, coType);
        BNRoutePlanNode eNode = new BNRoutePlanNode(location.getLongitude(), location.getLatitude(), "目地地点", null, coType);

        List<BNRoutePlanNode> list = new ArrayList<>();
        list.add(sNode);
        list.add(eNode);
        BaiduNaviManager.getInstance().launchNavigator(mActivity, list, 1, isMock, new DemoRoutePlanListener(sNode));

    }

    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
            /*
             * 设置途径点以及resetEndNode会回调该接口
			 */

            for (Activity ac : activityList) {
                if (ac.getClass().getName().endsWith("BNDemoGuideActivity")) {
                    return;
                }
            }
            progressDialog.dismiss();
            Intent intent = new Intent(mActivity, BNDemoGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, mBNRoutePlanNode);
            intent.putExtras(bundle);
            mActivity.startActivity(intent);
        }

        @Override
        public void onRoutePlanFailed() {
            // TODO Auto-generated method stub
            Toast.makeText(mActivity, "算路失败", Toast.LENGTH_SHORT).show();
        }
    }

    public void showProgressDialog() {
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }
}
