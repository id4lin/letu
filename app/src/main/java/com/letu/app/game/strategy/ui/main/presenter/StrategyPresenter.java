package com.letu.app.game.strategy.ui.main.presenter;

import com.letu.app.baselib.base.BaseFragmentPresenter;
import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.baselib.net.HttpSubscriber;
import com.letu.app.baselib.utils.RxHelper;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.service.OtherNetApi;
import com.letu.app.game.strategy.service.StrategyNetApi;
import com.letu.app.game.strategy.ui.main.bean.StrategyListResponse;
import com.letu.app.game.strategy.ui.main.contract.StrategyContract;
import com.letu.app.game.strategy.ui.other.bean.BannerListItemResponse;
import com.letu.app.game.strategy.ui.other.bean.VerifyTokenResponse;
import com.letu.app.game.strategy.utils.SPManager;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ${user} on 2018/7/5
 */
public class StrategyPresenter extends BaseFragmentPresenter<StrategyContract.View> implements StrategyContract.Present  {
    @Inject
    StrategyPresenter(){

    }
    @Inject
    StrategyNetApi strategyNetApi;
    @Inject
    OtherNetApi otherNetApi;


    @Override
    public void fetchStrategyList(int start, int end) {
        strategyNetApi.fetchStrategyList(start,end)
                .compose(mView.getLifecycleProvider().<BaseResponse<StrategyListResponse>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<StrategyListResponse>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<StrategyListResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<StrategyListResponse> strategyListResponseBaseResponse) {
                        mView.fetchStrategyListSuccess(strategyListResponseBaseResponse.getData());
                    }

                    @Override
                    public void onFail(BaseResponse<StrategyListResponse> strategyListResponseBaseResponse) {
                        mView.fetchStrategyListFails(strategyListResponseBaseResponse.getCode(),strategyListResponseBaseResponse.getMessage());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.fetchStrategyListError();
                    }
                });

    }

    @Override
    public void fetchBannerList() {
        otherNetApi.fetchBannerList(Constant.BANNER_TYPE_STRATEGY)
                .compose(mView.getLifecycleProvider().<BaseResponse<List<BannerListItemResponse>>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<BannerListItemResponse>>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<List<BannerListItemResponse>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<BannerListItemResponse>> bannerListResponseBaseResponse) {
                        mView.fetchBannerListSuccess(bannerListResponseBaseResponse.getData());
                    }

                    @Override
                    public void onFail(BaseResponse<List<BannerListItemResponse>> bannerListResponseBaseResponse) {
                        mView.fetchBannerListFails(bannerListResponseBaseResponse.getCode(),bannerListResponseBaseResponse.getMessage());
                    }

                });

    }
}
