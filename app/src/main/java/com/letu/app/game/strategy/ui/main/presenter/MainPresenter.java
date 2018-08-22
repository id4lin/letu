package com.letu.app.game.strategy.ui.main.presenter;

import android.util.Log;

import com.letu.app.baselib.base.BasePresenter;
import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.baselib.net.HttpSubscriber;
import com.letu.app.baselib.utils.RxHelper;
import com.letu.app.game.strategy.bean.UserInfo;
import com.letu.app.game.strategy.service.OtherNetApi;
import com.letu.app.game.strategy.ui.login.bean.LoginResponse;
import com.letu.app.game.strategy.ui.main.contract.MainContract;
import com.letu.app.game.strategy.ui.other.bean.VerifyTokenResponse;
import com.letu.app.game.strategy.ui.register.bean.RegisterResponse;
import com.letu.app.game.strategy.utils.SPManager;

import javax.inject.Inject;

/**
 * Created by ${user} on 2018/7/5
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Present {
    private static final String TAG=MainPresenter.class.getName();
    @Inject
    MainPresenter() {
    }

    @Inject
    OtherNetApi otherNetApi;

    @Override
    public void verifyToken(){
        if(SPManager.getInstance().isLogin()){
            String token = SPManager.getInstance().getLoginToken();
            otherNetApi.verifyToken(token)
                    .compose(mView.getLifecycleProvider().<BaseResponse<VerifyTokenResponse>>bindToLifecycle())
                    .compose(RxHelper.<BaseResponse<VerifyTokenResponse>>io_main())
                    .subscribe(new HttpSubscriber<BaseResponse<VerifyTokenResponse>>() {
                        @Override
                        public void onSuccess(BaseResponse<VerifyTokenResponse> verifyTokenResponseBaseResponse) {
                            if(null==verifyTokenResponseBaseResponse||null==verifyTokenResponseBaseResponse.getData()){
                                return;
                            }
                            UserInfo userInfo=SPManager.getInstance().getUserInfo();
                            userInfo.setIsPromoter(verifyTokenResponseBaseResponse.getData().getIsSpreader());
                            userInfo.setAvatarUrl(verifyTokenResponseBaseResponse.getData().getAvatarUrl());
                            userInfo.setNickName(verifyTokenResponseBaseResponse.getData().getNickName());
                            SPManager.getInstance().putUserInfo(userInfo);

                        }

                        @Override
                        public void onFail(BaseResponse<VerifyTokenResponse> verifyTokenResponseBaseResponse) {
                           SPManager.getInstance().clearUserInfo();
                        }

                    });
        }

    }



}
