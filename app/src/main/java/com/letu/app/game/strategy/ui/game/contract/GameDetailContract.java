package com.letu.app.game.strategy.ui.game.contract;

import com.letu.app.baselib.base.BaseActivityView;
import com.letu.app.game.strategy.ui.game.bean.GameDetailResponse;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;

/**
 * Created by ${user} on 2018/7/12
 */
public class GameDetailContract {
    public interface View extends BaseActivityView {
        void fetchGameDetailSuccess(GameDetailResponse gameDetailResponse);
        void fetchGameDetailFails(int code,String msg);

        void wantPlayGameSuccess(OtherResponse otherResponse);
        void wantPlayGameFails(int code,String msg);
    }

    public interface Present{
        void fetchGameDetail(String gameId);

        /**
         * 我要玩
         * @param gameId
         */
        void wantPlayGame(String gameId);

        boolean isLogin();
    }
}
