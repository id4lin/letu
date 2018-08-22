package com.letu.app.game.strategy.ui.me.widget;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.letu.app.baselib.base.BaseActivity;
import com.letu.app.baselib.utils.ToastUtil;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.me.contract.ModifyPasswordContract;
import com.letu.app.game.strategy.ui.me.presenter.ModifyPasswordPresenter;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;
import com.letu.app.game.strategy.utils.LeTuUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyPasswordActivity extends BaseActivity<ModifyPasswordPresenter> implements ModifyPasswordContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.modify_old_password_edt)
    TextInputEditText modifyOldPasswordEdt;
    @BindView(R.id.modify_old_password_tiLayout)
    TextInputLayout modifyOldPasswordTiLayout;
    @BindView(R.id.modify_new_password_edt)
    TextInputEditText modifyNewPasswordEdt;
    @BindView(R.id.modify_new_password_tiLayout)
    TextInputLayout modifyNewPasswordTiLayout;
    @BindView(R.id.modify_password_btn)
    Button modifyPasswordBtn;

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
        setContentView(R.layout.activity_modify_password);
        mPresenter.attachView(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.modify_password_btn)
    public void onViewClicked() {
        if(LeTuUtils.isFastClick()){
            return;
        }
        mPresenter.modifyPassword(modifyOldPasswordEdt.getText().toString(),modifyNewPasswordEdt.getText().toString());

    }

    @Override
    public void modifyPasswordSuccess(OtherResponse otherResponse) {
        ToastUtil.toastShort("密码修改成功！");
        setResult(Constant.CODE_RESPONSE_MODIFY_PASSWORD);
        finish();
    }

    @Override
    public void modifyPasswordFails(int code, String msg) {
        modifyOldPasswordTiLayout.setError(msg);
    }

    @Override
    public void clearError() {
        modifyOldPasswordTiLayout.setError("");
        modifyNewPasswordTiLayout.setError("");
    }

    @Override
    public void setOldPasswordNullError(String msg) {
        modifyOldPasswordTiLayout.setError(msg);
    }

    @Override
    public void setNewPasswordNullError(String msg) {
        modifyNewPasswordTiLayout.setError(msg);
    }

    @Override
    public void setOldPasswordValidError(String msg) {
        modifyOldPasswordTiLayout.setError(msg);
    }

    @Override
    public void setNewPasswordValidError(String msg) {
        modifyNewPasswordTiLayout.setError(msg);
    }

    @Override
    public void setOldPasswordLengthValidError(String msg) {
        modifyOldPasswordTiLayout.setError(msg);
    }

    @Override
    public void setNewPasswordLengthValidError(String msg) {
        modifyNewPasswordTiLayout.setError(msg);
    }
}
