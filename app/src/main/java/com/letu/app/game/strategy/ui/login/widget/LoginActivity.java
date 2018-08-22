package com.letu.app.game.strategy.ui.login.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.letu.app.baselib.base.BaseActivity;
import com.letu.app.baselib.utils.ToastUtil;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.login.bean.LoginResponse;
import com.letu.app.game.strategy.ui.login.contract.LoginContract;
import com.letu.app.game.strategy.ui.login.presenter.LoginPresenter;
import com.letu.app.game.strategy.ui.register.widget.RegisterActivity;
import com.letu.app.game.strategy.utils.LeTuUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.username)
    TextInputEditText username;
    @BindView(R.id.login_phone_tilayout)
    TextInputLayout phoneTilayout;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.login_password_tilayout)
    TextInputLayout passwordTilayout;
    @BindView(R.id.forget)
    TextView forget;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.regist)
    TextView regist;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.progressBar_loading)
    RelativeLayout progressBarLoading;

    @Override
    protected void daggerInit() {
        DaggerAppBaseComponent.builder()
                .appComponent(getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPresenter.attachView(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });

    }

    @OnClick(R.id.forget)
    public void onForgetClicked() {
        if(LeTuUtils.isFastClick()){
           return;
        }
        startActivity(ForgetPasswordActivity.class);
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClicked() {
        if(LeTuUtils.isFastClick()){
            return;
        }
        mPresenter.login(username.getText().toString(),password.getText().toString());
    }

//    @OnClick(R.id.toolbar)
//    public void onToolbarClicked() {
//        this.finish();
//    }

    @OnClick(R.id.regist)
    public void onRegistClicked() {
        if(LeTuUtils.isFastClick()){
            return;
        }
        startActivityForResult(new Intent(this,RegisterActivity.class),Constant.CODE_REQUEST_REGIST);
    }

    @Override
    public void loginSuccess(LoginResponse loginResponse) {
        Log.i(TAG,loginResponse.toString());
        ToastUtil.toastShort("登录成功！");
        setResult(Constant.CODE_RESPONSE_LOGIN);
        finish();
    }

    @Override
    public void loginFails(int code, String msg) {
        phoneTilayout.setError(msg);
    }

    @Override
    public void clearError() {
        phoneTilayout.setError("");
        passwordTilayout.setError("");
    }

    @Override
    public void setPhoneNullError(String msg) {
        phoneTilayout.setError(msg);
    }

    @Override
    public void setPasswordNullError(String msg) {
        passwordTilayout.setError(msg);
    }

    @Override
    public void setPhoneNumberValidError(String msg) {
        phoneTilayout.setError(msg);
    }

    @Override
    public void setPasswordLengthValidError(String msg) {
        passwordTilayout.setError(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Constant.CODE_REQUEST_REGIST&&resultCode==Constant.CODE_RESPONSE_REGIST){
            String phone = data.getStringExtra(Constant.KEY_INTENT_LOGIN_PHONE);
            String pwd = data.getStringExtra(Constant.KEY_INTENT_LOGIN_PWD);
            username.setText(phone);
            password.setText(pwd);

        }
    }
}

