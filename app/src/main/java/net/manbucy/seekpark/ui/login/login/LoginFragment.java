package net.manbucy.seekpark.ui.login.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import net.manbucy.seekpark.R;
import net.manbucy.seekpark.common.BaseFragment;
import net.manbucy.seekpark.common.Constant;
import net.manbucy.seekpark.model.user.UserLoginInfo;
import net.manbucy.seekpark.ui.login.LoginActivity;
import net.manbucy.seekpark.util.StringUtils;
import net.manbucy.seekpark.util.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录界面 fragment 作为视图 view
 * Created by yang on 2017/6/23.
 */

public class LoginFragment extends BaseFragment implements LoginContract.View {
    private LoginContract.Presenter loginPresenter;
    @BindView(R.id.lf_input_username)
    TextInputLayout usernameInput;
    @BindView(R.id.lf_input_password)
    TextInputLayout passwordInput;
    @BindView(R.id.lf_remember_password)
    CheckBox rememberPassword;
    private EditText username;
    private EditText password;
    private boolean isVerify = true;
    private ProgressDialog dialog;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    public LoginFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_fragment, container, false);
        ButterKnife.bind(this, root);
        username = usernameInput.getEditText();
        password = passwordInput.getEditText();
        initListener();
        return root;
    }

    private void initListener() {
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isEligible = StringUtils.verifyUsername(s.toString());
                if (!isEligible) {
                    showInputErrorMsgs(Constant.InputLayoutEntry.USERNAME_INPUT,
                            "用户名中汉字不能超过7个单词字符不能超过14个");
                    isVerify = false;
                } else {
                    usernameInput.setErrorEnabled(false);
                    isVerify = true;
                }
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    passwordInput.setErrorEnabled(false);
                    isVerify = false;
                    return;
                }
                boolean isEligible = StringUtils.verifyPassword(s.toString());
                if (!isEligible) {
                    showInputErrorMsgs(Constant.InputLayoutEntry.PASSWORD_INPUT,
                            "密码为6-16位且不能含有字母、数字、_.!~@#$%^&* 以外的字符");
                    isVerify = false;
                } else {
                    passwordInput.setErrorEnabled(false);
                    isVerify = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听EditText的imeOptions 在password的EditText键盘可以直接按右下角的键 进行登录
        password.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            //登录
                            login();
                            //隐藏软键盘
                            hideSoftInput();
                            return true;
                        }
                        return false;
                    }
                });
    }

    @OnClick(R.id.lf_button_login)
    public void login() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("正在登录...");
        dialog.setCancelable(false);
        String user = username.getText().toString();
        String pasw = password.getText().toString();
        if (user.isEmpty()) {
            showInputErrorMsgs(Constant.InputLayoutEntry.USERNAME_INPUT,"请输入用户名");
            isVerify = false;
        }
        if (pasw.isEmpty()) {
            showInputErrorMsgs(Constant.InputLayoutEntry.PASSWORD_INPUT,"请输入密码");
            isVerify = false;
        }
        if (isVerify) {
            UserLoginInfo userLoginInfo = new UserLoginInfo(user, pasw, rememberPassword.isChecked());
            loginPresenter.login(userLoginInfo);
        } else {
            toast("请检查你的用户名和密码格式是否有误");
        }
    }

    @OnClick(R.id.lf_text_register)
    public void register() {
        ((LoginActivity) getActivity()).startRegister();
    }

    @OnClick(R.id.lf_text_find_pwd)
    public void findPwd() {
        ((LoginActivity) getActivity()).startFindPwd();
    }

    @Override
    public void onResume() {
        super.onResume();
        loginPresenter.start();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.loginPresenter = Utility.checkNotNull(presenter);
    }

    /**
     * 设置用户的登录信息
     *
     * @param userLoginInfo 用户登录信息
     */
    @Override
    public void setUserLoginInfo(UserLoginInfo userLoginInfo) {
        username.setText(userLoginInfo.getUsername());
        password.setText(userLoginInfo.getPassword());
        rememberPassword.setChecked(userLoginInfo.isRemember());
    }

    /**
     * 显示 dialog
     */
    @Override
    public void showDialog() {
        dialog.show();
    }

    /**
     * 隐藏 dialog
     */
    @Override
    public void hideDialog() {
        dialog.dismiss();
    }

    /**
     * 显示提示信息
     *
     * @param message 要显示的信息
     */
    @Override
    public void showToast(String message) {
        toast(message);
    }


    @Override
    public void toMainActivity() {
        ((LoginActivity) getActivity()).toMainActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loginPresenter.detachView(this);
    }

    @Override
    public void showInputErrorMsgs(int inputLayoutEntry, String message) {
        switch (inputLayoutEntry){
            case Constant.InputLayoutEntry.USERNAME_INPUT:
                usernameInput.setError(message);
                break;
            case Constant.InputLayoutEntry.PASSWORD_INPUT:
                passwordInput.setError(message);
                break;
        }
    }
}
