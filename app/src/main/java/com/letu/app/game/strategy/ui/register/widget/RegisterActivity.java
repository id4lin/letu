package com.letu.app.game.strategy.ui.register.widget;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.letu.app.baselib.base.BaseActivity;
import com.letu.app.baselib.utils.ToastUtil;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.common.snackbar.BaseTransientBottomBar;
import com.letu.app.game.strategy.ui.common.snackbar.TopSnackBar;
import com.letu.app.game.strategy.ui.me.widget.WebViewActivity;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;
import com.letu.app.game.strategy.ui.protocol.widget.ProtocolActivity;
import com.letu.app.game.strategy.ui.register.bean.RegisterResponse;
import com.letu.app.game.strategy.ui.register.contract.RegisterContract;
import com.letu.app.game.strategy.ui.register.presenter.RegisterPresenter;
import com.letu.app.game.strategy.ui.register.utils.VerifyCodeCountDownTimer;
import com.letu.app.game.strategy.utils.LeTuUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {
    private static final String TAG=RegisterActivity.class.getName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.regist_phone_edt)
    TextInputEditText registPhoneEdt;
    @BindView(R.id.regist_pwd_edt)
    TextInputEditText registPwdEdt;
    //    @BindView(R.id.regist_rePwd_edt)
    //    TextInputEditText registRePwdEdt;
    //    @BindView(R.id.regist_rePwd_tiLayout)
    //    TextInputLayout registRePwdTiLayout;
    @BindView(R.id.regist_accept_ckbox)
    CheckBox registAcceptCkbox;
    @BindView(R.id.regist_user_protocol_tv)
    TextView registUserProtocolTv;
    @BindView(R.id.regist_btn)
    Button registBtn;
    @BindView(R.id.regist_goto_login_tv)
    TextView registGotoLoginTv;
    @BindView(R.id.regist_verification_code_edt)
    TextInputEditText registVerificationCodeEdt;
    @BindView(R.id.fetch_verification_code_tv)
    TextView fetchVerificationCodeTv;
    @BindView(R.id.regist_nick_edt)
    TextInputEditText registNickEdt;
    @BindView(R.id.regist_phone_tiLayout)
    TextInputLayout registPhoneTiLayout;
    @BindView(R.id.regist_verification_code_tiLayout)
    TextInputLayout registVerificationCodeTiLayout;
    @BindView(R.id.regist_nick_tiLayout)
    TextInputLayout registNickTiLayout;
    @BindView(R.id.regist_pwd_tiLayout)
    TextInputLayout registPwdTiLayout;


    private VerifyCodeCountDownTimer verifyCodeCountDownTimer = null;

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
        setContentView(R.layout.activity_register);
        mPresenter.attachView(this);

        init();


    }

    private void init(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });

        setUserProtocolTextView();

        verifyCodeCountDownTimer = new VerifyCodeCountDownTimer(fetchVerificationCodeTv, 60 * 1000, 1000);
    }

    private void setUserProtocolTextView(){
        StringBuffer sb = new StringBuffer("已阅读并同意");
        sb.append("<font color='#FFA042'>《用户协议》</font>");
        registUserProtocolTv.setText(Html.fromHtml(sb.toString()));
        registUserProtocolTv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @OnClick(R.id.regist_user_protocol_tv)
    public void go2UserProtocolPage() {
        if(LeTuUtils.isFastClick()){
            return;
        }
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra(Constant.KEY_INTENT_ME_WEBVIEW_TITLE,Constant.ME_TEXT_TITLE_USER_PROTOCOL);
        intent.putExtra(Constant.KEY_INTENT_ME_WEBVIEW_FILE_NAME,Constant.ME_WEBVIEW_FILE_NAME_USER_PROTOCOL);
        startActivity(intent);
    }

    @OnClick(R.id.regist_btn)
    public void onRegistBtnClicked() {
        if(LeTuUtils.isFastClick()){
            return;
        }
      mPresenter.register(registPhoneEdt.getText().toString(),registPwdEdt.getText().toString(),registVerificationCodeEdt.getText().toString(),registNickEdt.getText().toString());
    }

    @OnClick(R.id.regist_goto_login_tv)
    public void onRegistGotoLoginTvClicked() {
        if(LeTuUtils.isFastClick()){
            return;
        }
        finish();
    }


    @OnClick(R.id.fetch_verification_code_tv)
    public void fetchVerificationCode() {
        if(LeTuUtils.isFastClick()){
            return;
        }
       mPresenter.fetchVerifyCode(registPhoneEdt.getText().toString());

    }

    @OnCheckedChanged(R.id.regist_accept_ckbox)
    public void acceptUserProtocolOnCheckedChanged(CompoundButton compoundButton, boolean isChecked){
        if(isChecked){

        }
    }

    @Override
    public void registerSuccess(RegisterResponse response) {

//        TopSnackBar.make(toolbar,"注册成功！", BaseTransientBottomBar.LENGTH_LONG).show();

        ToastUtil.toastShort("注册成功！");
        Intent intent=new Intent();
        intent.putExtra(Constant.KEY_INTENT_LOGIN_PHONE,registPhoneEdt.getText().toString());
        intent.putExtra(Constant.KEY_INTENT_LOGIN_PWD,registPwdEdt.getText().toString());
        setResult(Constant.CODE_RESPONSE_REGIST,intent);
        finish();
    }

    @Override
    public void registerFails(int code, String msg) {
        registPhoneTiLayout.setError(msg);
    }

    @Override
    public void clearError() {
        registPhoneTiLayout.setError("");
        registNickTiLayout.setError("");
        registVerificationCodeTiLayout.setError("");
        registPwdTiLayout.setError("");

    }

    @Override
    public void setPhoneNullError(String msg) {
        registPhoneTiLayout.setError(msg);
    }

    @Override
    public void setNickNullError(String msg) {
        registNickTiLayout.setError(msg);
    }

    @Override
    public void setVerifyCodeNullError(String msg) {
        registVerificationCodeTiLayout.setError(msg);
    }

    @Override
    public void setPasswordNullError(String msg) {
        registPwdTiLayout.setError(msg);
    }

    @Override
    public void setPhoneNumberValidError(String msg) {
        registPhoneTiLayout.setError(msg);
    }

    @Override
    public void setPasswordValidError(String msg) {
        registPwdTiLayout.setError(msg);
    }

    @Override
    public void setVerifyCodeLengthValidError(String msg) {
        registVerificationCodeTiLayout.setError(msg);
    }

    @Override
    public void setNickNameLengthValidError(String msg) {
        registNickTiLayout.setError(msg);
    }

    @Override
    public void setPasswordLengthValidError(String msg) {
        registPwdTiLayout.setError(msg);
    }

    @Override
    public void setAcceptUserProtocolError(String msg) {
        TopSnackBar.make(toolbar,"请先阅读并同意<<用户协议>>！", BaseTransientBottomBar.LENGTH_LONG).show();
    }

    @Override
    public boolean isAcceptUserProtocol() {
        return registAcceptCkbox.isChecked();
    }

    @Override
    public void startVerifyCodeCountDownTimer() {
        if (null == verifyCodeCountDownTimer) {
            return;
        }
        verifyCodeCountDownTimer.start();
    }

    @Override
    public void fetchVerifyCodeSuccess(OtherResponse otherResponse) {
        ToastUtil.toastShort("获取验证码成功！");
    }

    @Override
    public void fetchVerifyCodeFails(int code, String msg) {
        registVerificationCodeTiLayout.setError(msg);
    }
}
