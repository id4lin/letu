package com.letu.app.game.strategy.ui.login.widget;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.letu.app.baselib.base.BaseActivity;
import com.letu.app.baselib.utils.ToastUtil;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.login.contract.ForgetPasswordContract;
import com.letu.app.game.strategy.ui.login.presenter.ForgetPasswordPresenter;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;
import com.letu.app.game.strategy.ui.other.bean.PromoterListItemResponse;
import com.letu.app.game.strategy.ui.register.utils.VerifyCodeCountDownTimer;
import com.letu.app.game.strategy.utils.LeTuUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPasswordActivity extends BaseActivity<ForgetPasswordPresenter> implements ForgetPasswordContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.regist_phone_edt)
    TextInputEditText registPhoneEdt;
    @BindView(R.id.regist_phone_tiLayout)
    TextInputLayout registPhoneTiLayout;
    @BindView(R.id.regist_verification_code_edt)
    TextInputEditText registVerificationCodeEdt;
    @BindView(R.id.regist_verification_code_tiLayout)
    TextInputLayout registVerificationCodeTiLayout;
    @BindView(R.id.fetch_verification_code_tv)
    TextView fetchVerificationCodeTv;
    @BindView(R.id.auth_code_linearlayout)
    RelativeLayout authCodeLinearlayout;
    @BindView(R.id.regist_pwd_edt)
    TextInputEditText registPwdEdt;
    @BindView(R.id.regist_pwd_tiLayout)
    TextInputLayout registPwdTiLayout;
    @BindView(R.id.regist_btn)
    Button registBtn;
    @BindView(R.id.regist_info_layout)
    LinearLayout registInfoLayout;

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
        setContentView(R.layout.activity_forget_password);
        mPresenter.attachView(this);
        verifyCodeCountDownTimer = new VerifyCodeCountDownTimer(fetchVerificationCodeTv, 60 * 1000, 1000);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @OnClick({R.id.fetch_verification_code_tv, R.id.regist_btn})
    public void onViewClicked(View view) {
        if (LeTuUtils.isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.fetch_verification_code_tv:
                mPresenter.fetchVerifyCode(registPhoneEdt.getText().toString());
                break;
            case R.id.regist_btn:
                mPresenter.forgetPassword(registPhoneEdt.getText().toString(), registVerificationCodeEdt.getText().toString(), registPwdEdt.getText().toString());
                break;
        }
    }

    @Override
    public void forgetPasswordSuccess(OtherResponse otherResponse) {
        ToastUtil.toastShort("新密码设置成功");
        finish();
    }

    @Override
    public void forgetPasswordFails(int code, String msg) {
        registPhoneTiLayout.setError(msg);
    }

    @Override
    public void clearError() {
        registPhoneTiLayout.setError("");
        registVerificationCodeTiLayout.setError("");
        registPwdTiLayout.setError("");

    }

    @Override
    public void setPhoneNullError(String msg) {
        registPhoneTiLayout.setError(msg);
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
    public void setPasswordLengthValidError(String msg) {
        registPwdTiLayout.setError(msg);
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
