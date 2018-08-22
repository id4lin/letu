package com.letu.app.game.strategy.ui.strategy.contract;

import com.letu.app.baselib.base.BaseActivityView;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;
import com.letu.app.game.strategy.ui.strategy.bean.CommentListItemResponse;
import com.letu.app.game.strategy.ui.strategy.bean.CommentListResponse;
import com.letu.app.game.strategy.ui.strategy.bean.CommentResponse;
import com.letu.app.game.strategy.ui.strategy.bean.StrategyDetailResponse;

import java.util.List;

import retrofit2.http.Query;

/**
 * Created by ${user} on 2018/7/10
 */
public class StrategyDetailContract {
    public interface View extends BaseActivityView {
        void fetchStrategyDetailSuccess(StrategyDetailResponse strategyDetailResponse);
        void fetchStrategyDetailFails(int code,String msg);


        void fetctCommentListSuccess(CommentListResponse commentListResponse);
        void fetctCommentListFails(int code,String msg);

        void likeSuccess(OtherResponse otherResponse);
        void likeFails(int code,String msg);

        void collectSuccess(OtherResponse otherResponse);
        void collectFails(int code,String msg);

        void removeCollectSuccess(OtherResponse otherResponse);
        void removeCollectFails(int code,String msg);

        void wantPlayGameSuccess(OtherResponse otherResponse);
        void wantPlayGameFails(int code,String msg);

    }
    public interface Present{
        /**
         * 获取攻略详情
         * @param strategyId【攻略ID】
         */
        void fetchStrategyDetail(String strategyId);

        /**
         * 获取所有评论
         * @param strategyId【类型id,（攻略ID,）】
         * @param start
         * @param end
         */
        void fetctCommentList(String strategyId,int start,int end);

        /**
         * 攻略点赞
         * @param strategyId
         */
        void like(String strategyId);

        /**
         * 收藏攻略
         * @param strategyId
         */
        void collect(String strategyId);

        /**
         * 取消攻略收藏
         * @param strategyId
         */
        void removeCollect(String strategyId);

        /**
         * 我要玩
         * @param gameId
         */
        void wantPlayGame(String gameId);

        boolean isLogin();
    }
}
