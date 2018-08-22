package com.letu.app.game.strategy.ui.me.widget;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.letu.app.baselib.base.BaseActivity;
import com.letu.app.baselib.utils.ToastUtil;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.common.view.OneLineView;
import com.letu.app.game.strategy.ui.me.contract.ModifyNickNameContract;
import com.letu.app.game.strategy.ui.me.presenter.ModifyNickNamePresenter;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;
import com.letu.app.game.strategy.utils.LeTuUtils;
import com.letu.app.game.strategy.utils.SPManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyNickNameActivity extends BaseActivity<ModifyNickNamePresenter> implements ModifyNickNameContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.modify_nick_name)
    OneLineView modifyNickName;
    @BindView(R.id.modify_nick_name_submit)
    TextView modifyNickNameSubmit;

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
        setContentView(R.layout.activity_modify_nick_name);
        mPresenter.attachView(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUserInfoPage();
            }
        });

        modifyNickName.initItemWidthEdit(R.drawable.icon_youxi, "", "昵称");
        modifyNickName.showLeftIcon(false);
        modifyNickName.setEditContent(SPManager.getInstance().getUserInfo().getNickName());
    }

    @OnClick(R.id.modify_nick_name_submit)
    public void onViewClicked() {
        if(LeTuUtils.isFastClick()){
            return;
        }
        modifyNickName.setEditContentError(null);
        mPresenter.modifyNickName(modifyNickName.getEditContent());
    }

    @Override
    public void modifyNickNameSuccess(OtherResponse otherResponse) {
        ToastUtil.toastShort("设置成功");
        gotoUserInfoPage();
    }

    @Override
    public void modifyNickNameFails(int code, String msg) {
        modifyNickName.setEditContentError(msg);
    }

    @Override
    public void setNickNameLengthValidError(String msg) {
        modifyNickName.setEditContentError(msg);
    }

    @Override
    public void setErrorNull(String msg) {
        modifyNickName.setEditContentError(msg);
    }

    private void gotoUserInfoPage(){
        setResult(Constant.CODE_RESPONSE_MODIFY_NICK_NAME);
        finish();
    }
}
