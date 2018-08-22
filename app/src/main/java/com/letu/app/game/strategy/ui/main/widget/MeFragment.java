package com.letu.app.game.strategy.ui.main.widget;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.letu.app.baselib.base.BaseFragment;
import com.letu.app.baselib.utils.ToastUtil;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.common.transformation.CircleTransform;
import com.letu.app.game.strategy.ui.common.view.OneLineView;
import com.letu.app.game.strategy.ui.login.widget.LoginActivity;
import com.letu.app.game.strategy.ui.main.contract.MeContract;
import com.letu.app.game.strategy.ui.main.presenter.MePresenter;
import com.letu.app.game.strategy.ui.me.widget.CollectListActivity;
import com.letu.app.game.strategy.ui.me.widget.GameListActivity;
import com.letu.app.game.strategy.ui.me.widget.ModifyPasswordActivity;
import com.letu.app.game.strategy.ui.me.widget.PromoterActivity;
import com.letu.app.game.strategy.ui.me.widget.UserInfoActivity;
import com.letu.app.game.strategy.ui.me.widget.WebViewActivity;
import com.letu.app.game.strategy.utils.LeTuUtils;
import com.letu.app.game.strategy.utils.SPManager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MeFragment extends BaseFragment<MePresenter> implements MeContract.View {


    @BindView(R.id.tv_photo)
    ImageView tvPhoto;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.me_edit_des_tv)
    TextView editDesTv;
    @BindView(R.id.userinfo_linearLayout)
    LinearLayout userinfoLinearLayout;
    @BindView(R.id.root)
    LinearLayout root;

    @BindView(R.id.my_game)
    OneLineView myGame;
    @BindView(R.id.my_strategy)
    OneLineView myStrategy;
    @BindView(R.id.my_promoter)
    OneLineView myPromoter;
    @BindView(R.id.my_welfare)
    OneLineView myWelfare;
    @BindView(R.id.my_help)
    OneLineView myHelp;
    @BindView(R.id.my_modify_password)
    OneLineView myModifyPassword;
    @BindView(R.id.my_back_feed)
    OneLineView myBackFeed;
    @BindView(R.id.my_about_me)
    OneLineView myAboutMe;
    @BindView(R.id.logout)
    TextView logout;
    @BindView(R.id.logout_linearLayout)
    LinearLayout logoutLinearLayout;

    public MeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance(String name) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void daggerInit() {
        DaggerAppBaseComponent.builder()
                .appComponent(getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attachView(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.get("name").toString();
            //            fragmentTestTv.setText(name);
        }

        init();


//        logout.init("退出游戏");
//        logout.showDivider(true,true);
//        logout.showArrow(false);
//        logout.showLeftIcon(false);
//        logout.setTextContentColor(R.color.orange);

    }

    private void init(){
        myGame.initMine(R.drawable.icon_youxi_select,Constant.ME_TEXT_TITLE_GAME,"",true);
        myGame.showDivider(true,true);

        myStrategy.initMine(R.drawable.ic_collect_red_30dp,Constant.ME_TEXT_TITLE_COLLECT,"",true);
        myStrategy.showDivider(true,true);

        myPromoter.initMine(R.drawable.ic_game_manage,Constant.ME_TEXT_TITLE_PROMOTER,"",true);
        myPromoter.showDivider(true,true);

        myWelfare.initMine(R.drawable.icon_youxi,Constant.ME_TEXT_TITLE_WELFARE,"",true);
        myWelfare.showDivider(true,true);

        myModifyPassword.initMine(R.drawable.ic_mode_edit_black_24dp,Constant.ME_TEXT_TITLE_MODIFY_PASSWORD,"",true);
        myModifyPassword.showDivider(true,true);

        myHelp.initMine(R.drawable.ic_help,Constant.ME_TEXT_TITLE_HELP,"",true);
        myHelp.showDivider(true,true);


        myBackFeed.initMine(R.drawable.ic_help,Constant.ME_TEXT_TITLE_BACK_FEED,"",true);
        myBackFeed.showDivider(true,true);

        myAboutMe.initMine(R.drawable.ic_about_me_black,Constant.ME_TEXT_TITLE_ABOUT_ME,"",true);
        myAboutMe.showDivider(true,true);


        initUserInfo();
    }

    @Override
    public void initUserInfo(){
        if(mPresenter.isLogin()){
            tvName.setVisibility(View.VISIBLE);
            tvName.setText(mPresenter.getUserInfo().getNickName());
            editDesTv.setText("点击编辑");
            logoutLinearLayout.setVisibility(View.VISIBLE);
            myModifyPassword.setVisibility(View.VISIBLE);
            showMyPromoter(mPresenter.isPromoter());

            if(!LeTuUtils.isNull(SPManager.getInstance().getUserInfo().getAvatarUrl())){
                RequestCreator requestCreator=Picasso.get()
                        .load(SPManager.getInstance().getUserInfo().getAvatarUrl());

                requestCreator.transform(new CircleTransform())
                        .error(R.drawable.user_default_photo)
                        .fit()
                        .placeholder(R.drawable.user_default_photo)
                        .tag(mContext)
                        .into(tvPhoto);
            }

        }else{
            tvPhoto.setImageResource(R.drawable.user_default_photo);
            tvName.setVisibility(View.GONE);
            editDesTv.setText("点击登录");
            logoutLinearLayout.setVisibility(View.INVISIBLE);
            myPromoter.setVisibility(View.GONE);
            myModifyPassword.setVisibility(View.GONE);
        }
    }

    @Override
    public void logoutSuccess() {
        logoutLinearLayout.setVisibility(View.INVISIBLE);
        initUserInfo();
        ToastUtil.toastShort("已退出登录！");
    }

    @OnClick({R.id.userinfo_linearLayout,R.id.tv_photo})
    public void onUserInfoLinearLayoutClicked() {
        if(LeTuUtils.isFastClick()){
            return;
        }
        if(mPresenter.isLogin()){
            ///TODO 编辑用户信息页面
//            ToastUtil.toastShort("跳转到用户信息编辑页面");
            Intent intent=new Intent(mContext,UserInfoActivity.class);
            startActivityForResult(intent,Constant.CODE_REQUEST_USER_INFO);
        }else{
            startActivityForResult(new Intent(mContext,LoginActivity.class), Constant.CODE_REQUEST_LOGIN);
        }

    }


    @OnClick(R.id.logout)
    public void logout(){
        if(LeTuUtils.isFastClick()){
            return;
        }
        mPresenter.logout();
    }

    @OnClick(R.id.my_game)
    void gotoMyGamePage(){
        if(!checkLoginStatus()){

            return;
        }

        startActivity(GameListActivity.class);
    }
    @OnClick(R.id.my_strategy)
    void gotoMyCollectPage(){
        if(!checkLoginStatus()){
            return;
        }

        startActivity(CollectListActivity.class);
    }

    @OnClick(R.id.my_promoter)
    void gotoMyPromoterPage(){
        if(!checkLoginStatus()){
            return;
        }

        startActivity(PromoterActivity.class);
    }

    @OnClick(R.id.my_help)
    void gotoMyHelpPage(){

        openWebView(Constant.ME_TEXT_TITLE_HELP,Constant.ME_WEBVIEW_FILE_NAME_HELP);
    }

    @OnClick(R.id.my_modify_password)
    void gotoMyModifyPasswordPage(){
        if(!checkLoginStatus()){
            return;
        }
        Intent intent=new Intent(mContext,ModifyPasswordActivity.class);

        startActivityForResult(intent,Constant.CODE_REQUEST_MODIFY_PASSWORD);

    }

    @OnClick(R.id.my_about_me)
    void gotoAboutMePage(){

        openWebView(Constant.ME_TEXT_TITLE_ABOUT_ME,Constant.ME_WEBVIEW_FILE_NAME_ABOUT_ME);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG,"requestCode:"+requestCode+" , resultCode:"+resultCode);
        if(requestCode==Constant.CODE_REQUEST_LOGIN&&resultCode==Constant.CODE_RESPONSE_LOGIN){
            initUserInfo();
        }else if(requestCode==Constant.CODE_REQUEST_MODIFY_PASSWORD&&resultCode==Constant.CODE_RESPONSE_MODIFY_PASSWORD){
            mPresenter.logout();
        }else if(requestCode==Constant.CODE_REQUEST_USER_INFO&&resultCode==Constant.CODE_RESPONSE_USER_INFO){
            initUserInfo();
        }
    }

    private boolean checkLoginStatus(){
        if(LeTuUtils.isFastClick()){
            return false;
        }
        if(mPresenter.isLogin()){
            return true;
        }
        startActivityForResult(new Intent(mContext,LoginActivity.class), Constant.CODE_REQUEST_LOGIN);
        return false;
    }

    private void openWebView(String title,String fileName){
        if(LeTuUtils.isFastClick()){
            return;
        }
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra(Constant.KEY_INTENT_ME_WEBVIEW_TITLE,title);
        intent.putExtra(Constant.KEY_INTENT_ME_WEBVIEW_FILE_NAME,fileName);
        startActivity(intent);
    }

    void showMyPromoter(boolean isPromoter){
        if(isPromoter){
            myPromoter.setVisibility(View.VISIBLE);
        }else{
            myPromoter.setVisibility(View.GONE);
        }

    }
}
