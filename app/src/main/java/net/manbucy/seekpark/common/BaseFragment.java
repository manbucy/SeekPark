package net.manbucy.seekpark.common;

import android.content.Context;
import android.widget.Toast;

import net.manbucy.seekpark.listener.DrawerLayoutListener;
import net.manbucy.seekpark.ui.main.MainActivity;

import me.yokeyword.fragmentation.SupportFragment;

/**
 *
 * Created by yang on 2017/6/23.
 */

public class BaseFragment extends SupportFragment {
    public DrawerLayoutListener drawerLayoutListener;
    /**
     * toast提示
     * @param message 要显示的信息
     */
    public void toast(String message){
        Toast.makeText(_mActivity, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity){
            drawerLayoutListener = (DrawerLayoutListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        drawerLayoutListener = null;
    }
}
