package com.letu.app.game.strategy.ui.me.presenter;

import com.letu.app.baselib.base.BasePresenter;
import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.baselib.net.HttpSubscriber;
import com.letu.app.baselib.utils.RxHelper;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.service.OtherNetApi;
import com.letu.app.game.strategy.ui.me.bean.CollectListItemResponse;
import com.letu.app.game.strategy.ui.me.bean.GameGridItemResponse;
import com.letu.app.game.strategy.ui.me.contract.CollectListContract;
import com.letu.app.game.strategy.utils.SPManager;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ${user} on 2018/7/18
 */
public class CollectListPresenter extends BasePresenter<CollectListContract.View> implements CollectListContract.Present {
    @Inject
    CollectListPresenter() {

    }

    @Inject
    OtherNetApi otherNetApi;

    @Override
    public void fetchCollectList() {
        otherNetApi.fetchCollectList(SPManager.getInstance().getLoginToken(), Constant.BANNER_TYPE_STRATEGY)
                .compose(mView.getLifecycleProvider().<BaseResponse<List<CollectListItemResponse>>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<CollectListItemResponse>>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<List<CollectListItemResponse>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<CollectListItemResponse>> gameGridItemResponseBaseResponse) {
                        mView.fetchCollectListSuccess(gameGridItemResponseBaseResponse.getData());
                    }

                    @Override
                    public void onFail(BaseResponse<List<CollectListItemResponse>> gameGridItemResponseBaseResponse) {
                        mView.fetchCollectListFails(gameGridItemResponseBaseResponse.getCode(), gameGridItemResponseBaseResponse.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mView.fetchCollectListComplete();
                    }

                });

    }
}
