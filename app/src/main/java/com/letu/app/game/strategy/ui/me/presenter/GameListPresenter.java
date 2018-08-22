package com.letu.app.game.strategy.ui.me.presenter;

import com.letu.app.baselib.base.BasePresenter;
import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.baselib.net.HttpSubscriber;
import com.letu.app.baselib.utils.RxHelper;
import com.letu.app.game.strategy.service.OtherNetApi;
import com.letu.app.game.strategy.ui.main.bean.StrategyListResponse;
import com.letu.app.game.strategy.ui.me.bean.GameGridItemResponse;
import com.letu.app.game.strategy.ui.me.contract.GameListContract;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;
import com.letu.app.game.strategy.utils.SPManager;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ${user} on 2018/7/18
 */
public class GameListPresenter extends BasePresenter<GameListContract.View> implements GameListContract.Present {
    @Inject
    GameListPresenter() {

    }

    @Inject
    OtherNetApi otherNetApi;


    @Override
    public void fetchMyGameList() {
        otherNetApi.fetchMyGameList(SPManager.getInstance().getLoginToken())
                .compose(mView.getLifecycleProvider().<BaseResponse<List<GameGridItemResponse>>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<GameGridItemResponse>>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<List<GameGridItemResponse>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<GameGridItemResponse>> gameGridItemResponseBaseResponse) {
                        mView.fetchMyGameListSuccess(gameGridItemResponseBaseResponse.getData());
                    }

                    @Override
                    public void onFail(BaseResponse<List<GameGridItemResponse>> gameGridItemResponseBaseResponse) {
                        mView.fetchMyGameListFails(gameGridItemResponseBaseResponse.getCode(), gameGridItemResponseBaseResponse.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mView.fetchMyGameListComplete();
                    }

                });
    }
}
