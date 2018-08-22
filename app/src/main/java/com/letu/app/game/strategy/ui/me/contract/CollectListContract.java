package com.letu.app.game.strategy.ui.me.contract;

import com.letu.app.baselib.base.BaseActivityView;
import com.letu.app.game.strategy.ui.me.bean.CollectListItemResponse;

import java.util.List;

/**
 * Created by ${user} on 2018/7/18
 */
public class CollectListContract {
    public interface View extends BaseActivityView {
        void fetchCollectListSuccess(List<CollectListItemResponse> collectListItemResponseResponse);
        void fetchCollectListFails(int code,String msg);
        void fetchCollectListComplete();
    }


    public interface Present{
        void fetchCollectList();
    }
}
