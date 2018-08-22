package com.letu.app.game.strategy.ui.main.presenter;

import com.letu.app.baselib.base.BaseFragmentPresenter;
import com.letu.app.game.strategy.bean.UserInfo;
import com.letu.app.game.strategy.ui.main.contract.MeContract;
import com.letu.app.game.strategy.utils.SPManager;

import javax.inject.Inject;

/**
 * Created by ${user} on 2018/7/7
 */
public class MePresenter extends BaseFragmentPresenter<MeContract.View> implements MeContract.Present  {
    @Inject
    MePresenter(){

    }

    @Override
    public boolean isLogin(){
        return SPManager.getInstance().isLogin();
    }
    @Override
    public UserInfo getUserInfo(){
        return SPManager.getInstance().getUserInfo();
    }

    @Override
    public void logout() {
        if(isLogin()){
            SPManager.getInstance().clearUserInfo();
            mView.logoutSuccess();
        }
    }

    @Override
    public boolean isPromoter() {
        return SPManager.getInstance().getUserInfo().isPromoter();
    }


}
