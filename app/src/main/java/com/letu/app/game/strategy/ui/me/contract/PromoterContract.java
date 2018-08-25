package com.letu.app.game.strategy.ui.me.contract;

import com.letu.app.baselib.base.BaseActivityView;
import com.letu.app.game.strategy.ui.other.bean.PromoterListItemResponse;

import java.util.Date;
import java.util.List;

/**
 * Created by ${user} on 2018/7/30
 */
public class PromoterContract {
    public interface View extends BaseActivityView {
        void fetchMyPromoterListSuccess(List<PromoterListItemResponse> promoterListItemResponseList);
        void fetchMyPromoterListFails(int code,String msg);
        void fetchMyPromoterListError();
    }


    public interface Present{
        /**
         * 获取我的推广列表
         */
        void fetchMyPromoterList(Date date);
    }
}
