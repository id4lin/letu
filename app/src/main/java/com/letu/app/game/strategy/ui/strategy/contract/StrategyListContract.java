package com.letu.app.game.strategy.ui.strategy.contract;

import com.letu.app.baselib.base.BaseActivityView;
import com.letu.app.game.strategy.ui.main.bean.StrategyListResponse;

/**
 * Created by ${user} on 2018/7/17
 */
public class StrategyListContract {
    public interface View extends BaseActivityView {
        void fetchStrategyListSuccess(StrategyListResponse strategyListResponse);
        void fetchStrategyListFails(int code,String msg);
        void fetchStrategyListComplete();
    }
    public interface Present{
        void fetchStrategyList(int start,int end,String gameId);
    }
}
