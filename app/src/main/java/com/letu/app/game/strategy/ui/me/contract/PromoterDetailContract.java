package com.letu.app.game.strategy.ui.me.contract;

import com.letu.app.baselib.base.BaseActivityView;
import com.letu.app.game.strategy.ui.other.bean.PromoterIncomeListItemResponse;

import java.util.List;

/**
 * Created by ${user} on 2018/7/31
 */
public class PromoterDetailContract {
    public interface View extends BaseActivityView {

        void fetchPromoterIncomeListSuccess(List<PromoterIncomeListItemResponse> promoterIncomeListItemResponseList);
        void fetchPromoterIncomeListFails(int code,String msg);
        void fetchPromoterIncomeListError();

    }


    public interface Present{
        void fetchPromoterIncomeList(String gameId,String startTime,String endTime);
    }
}
