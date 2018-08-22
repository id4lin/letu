package com.letu.app.game.strategy.ui.strategy.presenter;

import com.letu.app.baselib.base.BasePresenter;
import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.baselib.net.HttpSubscriber;
import com.letu.app.baselib.utils.RxHelper;
import com.letu.app.game.strategy.service.StrategyNetApi;
import com.letu.app.game.strategy.ui.main.bean.StrategyListResponse;
import com.letu.app.game.strategy.ui.strategy.contract.StrategyListContract;

import javax.inject.Inject;

/**
 * Created by ${user} on 2018/7/17
 */
public class StrategyListPresenter extends BasePresenter<StrategyListContract.View> implements StrategyListContract.Present {
    @Inject
    StrategyListPresenter() {
    }

    @Inject
    StrategyNetApi strategyNetApi;

    @Override
    public void fetchStrategyList(int start, int end, String gameId) {
        strategyNetApi.fetchStrategyList(start,end,gameId)
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
                    public void onComplete() {
                        super.onComplete();
                        mView.fetchStrategyListComplete();
                    }

                });
    }
}
