package com.letu.app.game.strategy.ui.me.presenter;

import com.letu.app.baselib.base.BasePresenter;
import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.baselib.net.HttpSubscriber;
import com.letu.app.baselib.utils.RxHelper;
import com.letu.app.game.strategy.service.OtherNetApi;
import com.letu.app.game.strategy.ui.me.contract.PromoterContract;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;
import com.letu.app.game.strategy.ui.other.bean.PromoterListItemResponse;
import com.letu.app.game.strategy.utils.SPManager;
import com.letu.app.game.strategy.utils.TimeUtils;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ${user} on 2018/7/30
 */
public class PromoterPresenter extends BasePresenter<PromoterContract.View> implements PromoterContract.Present {
    @Inject
    PromoterPresenter() {

    }

    @Inject
    OtherNetApi otherNetApi;

    @Override
    public void fetchMyPromoterList(Date date) {
        otherNetApi.fetchMyPromoterList(SPManager.getInstance().getLoginToken(),TimeUtils.firstDayOfMonth(date),TimeUtils.lastDayOfMonth(date))
                .compose(mView.getLifecycleProvider().<BaseResponse<List<PromoterListItemResponse>>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<PromoterListItemResponse>>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<List<PromoterListItemResponse>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<PromoterListItemResponse>> otherResponseBaseResponse) {
                        if (mView != null) {
                            mView.fetchMyPromoterListSuccess(otherResponseBaseResponse.getData());
                        }
                    }

                    @Override
                    public void onFail(BaseResponse<List<PromoterListItemResponse>> otherResponseBaseResponse) {
                        mView.fetchMyPromoterListFails(otherResponseBaseResponse.getCode(), otherResponseBaseResponse.getMessage());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.fetchMyPromoterListError();
                    }
                });
    }
}
