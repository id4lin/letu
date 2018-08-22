package com.letu.app.game.strategy.ui.me.contract;

import com.letu.app.baselib.base.BaseActivityView;
import com.letu.app.game.strategy.ui.me.bean.GameGridItemResponse;

import java.util.List;

/**
 * Created by ${user} on 2018/7/18
 */
public class GameListContract {

    public interface View extends BaseActivityView {
        void fetchMyGameListSuccess(List<GameGridItemResponse> gameGridItemListResponse);
        void fetchMyGameListFails(int code,String msg);
        void fetchMyGameListComplete();

    }


    public interface Present{
        void fetchMyGameList();
    }
}

