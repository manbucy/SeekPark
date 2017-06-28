package net.manbucy.seekpark.common;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang on 2017/2/26.
 */

public class ActivityCollector {
    private static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActiity(Activity activity) {
        activityList.remove(activity);
    }

    public static Activity getTopActivity() {
        if (activityList.isEmpty()){
            return null;
        }else{
            return activityList.get(activityList.size()-1);
        }
    }
}
