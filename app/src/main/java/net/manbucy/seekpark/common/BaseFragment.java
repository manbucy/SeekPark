package net.manbucy.seekpark.common;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import net.manbucy.seekpark.R;
import net.manbucy.seekpark.listener.DrawerLayoutListener;
import net.manbucy.seekpark.model.user.User;
import net.manbucy.seekpark.ui.main.MainActivity;

import cn.bmob.v3.BmobUser;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * BaseFragment
 * Created by yang on 2017/6/23.
 */

public class BaseFragment extends SupportFragment {
    protected static int FRAGMENT_TYPE_FIRST = 0;
    protected static int FRAGMENT_TYPE_SECONDE = 1;
    public DrawerLayoutListener drawerLayoutListener;

    /**
     * 设置 toolbar
     *
     * @param toolbar toolbar
     */
    public void setToolbar(Toolbar toolbar,int fragmentType) {
        toolbar.setTitleTextColor(Color.WHITE);
        if (fragmentType == 0){
            toolbar.setNavigationIcon(R.drawable.ic_toolbar_menu);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (drawerLayoutListener != null) {
                        drawerLayoutListener.onOpen();
                    }
                }
            });
        } else if (fragmentType == 1){
            toolbar.setNavigationIcon(R.drawable.ic_toolbar_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSoftInput();
                    pop();
                }
            });
        }

    }

    /**
     * toast提示
     *
     * @param message 要显示的信息
     */
    public void toast(String message) {
        Toast.makeText(_mActivity, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            drawerLayoutListener = (DrawerLayoutListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        drawerLayoutListener = null;
    }

    /**
     * 获取当前的登录用户
     * @return 当前的登录用户
     */
    public User getCurrentUser() {
        return BmobUser.getCurrentUser(User.class);
    }

    @Override
    public boolean onBackPressedSupport() {
        hideSoftInput();
        return super.onBackPressedSupport();
    }
}
