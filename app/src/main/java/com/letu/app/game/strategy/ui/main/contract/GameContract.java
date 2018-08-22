package com.letu.app.game.strategy.ui.main.contract;

import com.letu.app.baselib.base.BaseFragmentView;
import com.letu.app.game.strategy.ui.main.bean.GameListItemResponse;
import com.letu.app.game.strategy.ui.main.bean.GameListResponse;
import com.letu.app.game.strategy.ui.other.bean.BannerListItemResponse;

import java.util.List;

/**
 * Created by ${user} on 2018/7/5
 */
public class GameContract {
    public interface View extends BaseFragmentView {
        void fecthGameListSuccess(GameListResponse gameListResponse);
        void fecthGameListFails(int code,String msg);
        void fecthGameListError();

        void fetchBannerListSuccess(List<BannerListItemResponse> bannerList);
        void fetchBannerListFails(int code,String msg);

    }
    public interface Present{
        void fecthGameList(int start,int end);

        /**
         * 获取banner列表
         */
        void fetchBannerList();
    }
}
