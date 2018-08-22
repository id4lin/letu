package com.letu.app.game.strategy.ui.main.contract;

import com.letu.app.baselib.base.BaseFragmentView;
import com.letu.app.game.strategy.ui.main.bean.StrategyListResponse;
import com.letu.app.game.strategy.ui.other.bean.BannerListItemResponse;

import java.util.List;

import retrofit2.http.Query;

/**
 * Created by ${user} on 2018/7/5
 */
public class StrategyContract {
    public interface View extends BaseFragmentView {
        void fetchStrategyListSuccess(StrategyListResponse strategyListResponse);
        void fetchStrategyListFails(int code,String msg);
        void fetchStrategyListError();

        void fetchBannerListSuccess(List<BannerListItemResponse> bannerListItemResponseList);
        void fetchBannerListFails(int code,String msg);

    }
    public interface Present{
        void fetchStrategyList(int start,int end);

        /**
         * 获取banner列表
         */
        void fetchBannerList();
    }
}
