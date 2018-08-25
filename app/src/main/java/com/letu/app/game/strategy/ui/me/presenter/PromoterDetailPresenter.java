package com.letu.app.game.strategy.ui.me.presenter;

import com.letu.app.baselib.base.BasePresenter;
import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.baselib.net.HttpSubscriber;
import com.letu.app.baselib.utils.RxHelper;
import com.letu.app.game.strategy.service.OtherNetApi;
import com.letu.app.game.strategy.ui.me.contract.PromoterDetailContract;
import com.letu.app.game.strategy.ui.other.bean.PromoterIncomeListItemResponse;
import com.letu.app.game.strategy.ui.other.bean.PromoterListItemResponse;
import com.letu.app.game.strategy.utils.SPManager;
import com.letu.app.game.strategy.utils.TimeUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ${user} on 2018/7/31
 */
public class PromoterDetailPresenter extends BasePresenter<PromoterDetailContract.View> implements PromoterDetailContract.Present {
    @Inject
    PromoterDetailPresenter() {

    }

    @Inject
    OtherNetApi otherNetApi;

    @Override
    public void fetchPromoterIncomeList(String gameId, String startTime, String endTime) {

        otherNetApi.fetchPromoterIncomeList(SPManager.getInstance().getLoginToken(),gameId,startTime,endTime)
                .compose(mView.getLifecycleProvider().<BaseResponse<List<PromoterIncomeListItemResponse>>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<PromoterIncomeListItemResponse>>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<List<PromoterIncomeListItemResponse>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<PromoterIncomeListItemResponse>> otherResponseBaseResponse) {
                        if (mView != null) {
                            mView.fetchPromoterIncomeListSuccess(otherResponseBaseResponse.getData());
                        }
                    }

                    @Override
                    public void onFail(BaseResponse<List<PromoterIncomeListItemResponse>> otherResponseBaseResponse) {
                        if (mView != null) {
                            mView.fetchPromoterIncomeListFails(otherResponseBaseResponse.getCode(), otherResponseBaseResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (mView != null) {
                            mView.fetchPromoterIncomeListError();
                        }
                    }
                });

    }
}
