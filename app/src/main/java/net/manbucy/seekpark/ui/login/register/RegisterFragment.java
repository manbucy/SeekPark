package net.manbucy.seekpark.ui.login.register;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.manbucy.seekpark.R;
import net.manbucy.seekpark.common.BaseFragment;
import net.manbucy.seekpark.common.Constant;
import net.manbucy.seekpark.model.user.User;
import net.manbucy.seekpark.ui.login.LoginActivity;
import net.manbucy.seekpark.util.StringUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 注册 view
 * Created by yang on 2017/6/24.
 */

public class RegisterFragment extends BaseFragment implements RegisterContract.View {
    private RegisterContract.Presenter registerPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rf_input_phone)
    TextInputLayout phoneInputLayout;
    private EditText phone;
    @BindView(R.id.rf_button_send_verify)
    Button verifyButton;
    @BindView(R.id.rf_input_verify)
    TextInputLayout verifyInputLayout;
    private EditText verify;
    @BindView(R.id.rf_input_username)
    TextInputLayout usernameInputLayout;
    private EditText username;
    @BindView(R.id.rf_input_password)
    TextInputLayout passwordInputLayout;
    private EditText password;
    @BindView(R.id.rf_input_password_again)
    TextInputLayout pwdAgainInputLayout;
    private EditText pwdAgain;
    @BindView(R.id.rf_check_merchant)
    CheckBox merchant;
    @BindView(R.id.mp_root_layout)
    LinearLayout merchant_layout;
    /**
     * 手机号码是否有效
     */
    private boolean isPhone = false;
    /**
     * 整体输入是否有效
     */
    private boolean isVerify = false;

    private ProgressDialog dialog;

    public static RegisterFragment getInstance() {
        return new RegisterFragment();
    }

    public RegisterFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.regster_fragment, container, false);
        ButterKnife.bind(this, root);
        initView();
        initListener();
        return root;
    }

    private void initView() {
        toolbar.setTitle("注册");
        setToolbar(toolbar,FRAGMENT_TYPE_SECONDE);
        phone = phoneInputLayout.getEditText();
        verify = verifyInputLayout.getEditText();
        username = usernameInputLayout.getEditText();
        password = passwordInputLayout.getEditText();
        pwdAgain = pwdAgainInputLayout.getEditText();
    }

    private void initListener() {
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isEligible = StringUtils.verifyPhoneNumber(s.toString());
                if (!isEligible) {
                    showInputErrorMsgs(Constant.InputLayoutEntry.PHONE_INPUT, "手机号码为11位数字且首位是1");
                    isPhone = false;
                    isVerify = false;
                } else {
                    isPhone = true;
                    isVerify = true;
                    phoneInputLayout.setErrorEnabled(false);
                }
                if (s.toString().isEmpty()) {
                    isVerify = false;
                    showInputErrorMsgs(Constant.InputLayoutEntry.PHONE_INPUT, "手机号码不能为空");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        verify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isEligible = StringUtils.verifyCAPTCHA(s.toString());
                if (!isEligible) {
                    showInputErrorMsgs(Constant.InputLayoutEntry.VERIFY_INPUT, "请输入6位数字");
                    isVerify = false;
                } else {
                    isVerify = true;
                    verifyInputLayout.setErrorEnabled(false);
                }
                if (s.toString().isEmpty()) {
                    isVerify = false;
                    showInputErrorMsgs(Constant.InputLayoutEntry.VERIFY_INPUT, "验证码不能为空");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isEligible = StringUtils.verifyUsername(s.toString());
                if (!isEligible) {
                    showInputErrorMsgs(Constant.InputLayoutEntry.USERNAME_INPUT,
                            "用户名中汉字不能超过7个单词字符不能超过14个");
                    isVerify = false;
                } else {
                    isVerify = true;
                    usernameInputLayout.setErrorEnabled(false);
                }
                if (s.toString().isEmpty()) {
                    isVerify = false;
                    showInputErrorMsgs(Constant.InputLayoutEntry.USERNAME_INPUT, "用户名不能为空");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    registerPresenter.checkUsername(username.getText().toString());
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isEligible = StringUtils.verifyPassword(s.toString());
                if (!isEligible) {
                    showInputErrorMsgs(Constant.InputLayoutEntry.PASSWORD_INPUT,
                            "密码为6-16位且不能含有字母、数字、_.!~@#$%^&* 以外的字符");
                    isVerify = false;
                } else {
                    isVerify = true;
                    passwordInputLayout.setErrorEnabled(false);
                }
                if (s.toString().isEmpty()) {
                    isVerify = false;
                    showInputErrorMsgs(Constant.InputLayoutEntry.PASSWORD_INPUT, "密码不能为空");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pwdAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isEligible = s.toString().equals(password.getText().toString());
                if (!isEligible) {
                    showInputErrorMsgs(Constant.InputLayoutEntry.PASSWORD_AGAIN_INPUT,
                            "两次密码不一样");
                    isVerify = false;
                } else {
                    isVerify = true;
                    pwdAgainInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        merchant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    merchant_layout.setVisibility(View.VISIBLE);
                } else {
                    merchant_layout.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick(R.id.rf_button_send_verify)
    public void sendVerify() {
        if (!isPhone) {
            showInputErrorMsgs(Constant.InputLayoutEntry.PHONE_INPUT, "请输入正确的手机号码");
        } else {
            registerPresenter.sendVerifyCode(phone.getText().toString());
        }

    }

    @OnClick(R.id.rf_button_register)
    public void register() {
        User user = new User();
        user.setMobilePhoneNumber(phone.getText().toString());
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        user.setCommitAuth(merchant.isChecked());
        checkEmpty();
        if (!isVerify) {
            showToast("填写的信息有误，请检查");
        } else {
            registerPresenter.singOrLogin(user, verify.getText().toString());
            showToast("注册成功");
        }
    }

    /**
     * 检测输入数据是否为空
     */
    private void checkEmpty() {

        if (phone.getText().toString().isEmpty()) {
            isVerify = false;
            showInputErrorMsgs(Constant.InputLayoutEntry.PHONE_INPUT, "手机号码不能为空");
        }
        if (verify.getText().toString().isEmpty()) {
            isVerify = false;
            showInputErrorMsgs(Constant.InputLayoutEntry.VERIFY_INPUT, "验证码不能为空");
        }
        if (username.getText().toString().isEmpty()) {
            isVerify = false;
            showInputErrorMsgs(Constant.InputLayoutEntry.USERNAME_INPUT, "用户名不能为空");
        }
        if (password.getText().toString().isEmpty()) {
            isVerify = false;
            showInputErrorMsgs(Constant.InputLayoutEntry.PASSWORD_INPUT, "密码不能为空");
        }
        if (pwdAgain.getText().toString().isEmpty()) {
            showInputErrorMsgs(Constant.InputLayoutEntry.PASSWORD_AGAIN_INPUT,
                    "两次密码不一样");
            isVerify = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        registerPresenter.start();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        this.registerPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        registerPresenter.detachView(this);
    }

    @Override
    public void showDialog(String message) {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void hideDialog() {
        dialog.dismiss();
        dialog = null;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(_mActivity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInputErrorMsgs(int inputLayoutEntry, String message) {
        switch (inputLayoutEntry) {
            case Constant.InputLayoutEntry.PHONE_INPUT:
                phoneInputLayout.setError(message);
                break;
            case Constant.InputLayoutEntry.VERIFY_INPUT:
                verifyInputLayout.setError(message);
                break;
            case Constant.InputLayoutEntry.USERNAME_INPUT:
                usernameInputLayout.setError(message);
                break;
            case Constant.InputLayoutEntry.PASSWORD_INPUT:
                passwordInputLayout.setError(message);
                break;
            case Constant.InputLayoutEntry.PASSWORD_AGAIN_INPUT:
                pwdAgainInputLayout.setError(message);
                break;
        }
    }

    @Override
    public void startTimer() {
        Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .take(60)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        verifyButton.setText("点击再次获取");
                        verifyButton.setClickable(true);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        verifyButton.setText(59 - aLong + "s后再次获取");
                    }
                });
    }

    @Override
    public void setIsVerify(boolean isVerify) {
        this.isVerify = isVerify;
    }

    @Override
    public void toMainActivity() {
        ((LoginActivity) getActivity()).toMainActivity();
    }
}
