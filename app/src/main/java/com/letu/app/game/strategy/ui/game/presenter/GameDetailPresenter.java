package com.letu.app.game.strategy.ui.game.presenter;

import android.text.TextUtils;

import com.letu.app.baselib.base.BasePresenter;
import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.baselib.net.HttpSubscriber;
import com.letu.app.baselib.utils.RxHelper;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.service.GameNetApi;
import com.letu.app.game.strategy.service.OtherNetApi;
import com.letu.app.game.strategy.ui.game.bean.GameDetailResponse;
import com.letu.app.game.strategy.ui.game.contract.GameDetailContract;
import com.letu.app.game.strategy.ui.login.bean.LoginResponse;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;
import com.letu.app.game.strategy.utils.SPManager;

import javax.inject.Inject;

/**
 * Created by ${user} on 2018/7/12
 */
public class GameDetailPresenter extends BasePresenter<GameDetailContract.View> implements GameDetailContract.Present {
    @Inject
    GameDetailPresenter() {

    }

    @Inject
    GameNetApi gameNetApi;
    @Inject
    OtherNetApi otherNetApi;

    @Override
    public void fetchGameDetail(String gameId) {
        gameNetApi.fetchGameDetail(gameId, Constant.OS_NAME_ANDROID)
                .compose(mView.getLifecycleProvider().<BaseResponse<GameDetailResponse>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<GameDetailResponse>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<GameDetailResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<GameDetailResponse> gameDetailResponseBaseResponse) {
                        if (mView != null) {
                            mView.fetchGameDetailSuccess(gameDetailResponseBaseResponse.getData());
                        }
                    }

                    @Override
                    public void onFail(BaseResponse<GameDetailResponse> gameDetailResponseBaseResponse) {
                        mView.fetchGameDetailFails(gameDetailResponseBaseResponse.getCode(), gameDetailResponseBaseResponse.getMessage());
                    }
                });
    }

    @Override
    public void wantPlayGame(String gameId) {
        if(TextUtils.isEmpty(gameId)){
            return;
        }
        otherNetApi.wantPlayGame(SPManager.getInstance().getLoginToken(),Constant.OS_NAME_ANDROID,gameId)
                .compose(mView.getLifecycleProvider().<BaseResponse<OtherResponse>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<OtherResponse>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<OtherResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<OtherResponse> otherResponseBaseResponse) {
                        mView.wantPlayGameSuccess(otherResponseBaseResponse.getData());
                    }

                    @Override
                    public void onFail(BaseResponse<OtherResponse> otherResponseBaseResponse) {
                        mView.wantPlayGameFails(otherResponseBaseResponse.getCode(),otherResponseBaseResponse.getMessage());
                    }

                });
    }

    @Override
    public boolean isLogin() {
        return SPManager.getInstance().isLogin();
    }
}
