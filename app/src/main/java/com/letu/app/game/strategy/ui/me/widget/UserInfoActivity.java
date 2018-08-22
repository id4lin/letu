package com.letu.app.game.strategy.ui.me.widget;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.ui.common.view.OneLineView;
import com.letu.app.game.strategy.utils.LeTuUtils;
import com.letu.app.game.strategy.utils.SPManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends AppCompatActivity {

    @BindView(R.id.userinfo_nick_name)
    OneLineView nickName;
    @BindView(R.id.userinfo_avatar)
    OneLineView avatar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();

            //设置修改状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            //设置状态栏的颜色，和你的app主题或者标题栏颜色设置一致就ok了
            window.setStatusBarColor(Color.BLACK);
        }
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Constant.CODE_RESPONSE_USER_INFO);
                finish();
            }
        });

        avatar.initMineImage(R.drawable.icon_youxi, "头像", SPManager.getInstance().getUserInfo().getAvatarUrl(), true);
        avatar.showLeftIcon(false);
        avatar.showDivider(true, true);


        nickName.initMine(R.drawable.icon_youxi, "昵称", SPManager.getInstance().getUserInfo().getNickName(), true);
        nickName.showLeftIcon(false);
        nickName.showDivider(true, true);
    }

    @OnClick({R.id.userinfo_avatar, R.id.userinfo_nick_name})
    public void onViewClicked(View view) {
        if(LeTuUtils.isFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.userinfo_avatar:
                startActivityForResult(new Intent(this,ModifyAvatarActivity.class),Constant.CODE_REQUEST_MODIFY_AVATAR);
                break;
            case R.id.userinfo_nick_name:
                startActivityForResult(new Intent(this,ModifyNickNameActivity.class),Constant.CODE_REQUEST_MODIFY_NICK_NAME);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Constant.CODE_REQUEST_MODIFY_NICK_NAME&&resultCode==Constant.CODE_RESPONSE_MODIFY_NICK_NAME){
            nickName.setRightText(SPManager.getInstance().getUserInfo().getNickName());
        }else if(requestCode==Constant.CODE_REQUEST_MODIFY_AVATAR&&resultCode==Constant.CODE_RESPONSE_MODIFY_AVATAR){
            avatar.setRightImageByUrl(SPManager.getInstance().getUserInfo().getAvatarUrl());
        }
    }

    @Override
    public void onBackPressed() {
        setResult(Constant.CODE_RESPONSE_USER_INFO);
        finish();
        super.onBackPressed();
    }
}
