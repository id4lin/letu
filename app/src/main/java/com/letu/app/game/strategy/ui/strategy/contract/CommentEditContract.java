package com.letu.app.game.strategy.ui.strategy.contract;

import com.letu.app.baselib.base.BaseActivityView;
import com.letu.app.game.strategy.ui.strategy.bean.CommentResponse;

/**
 * Created by ${user} on 2018/7/11
 */
public class CommentEditContract {
    public interface View extends BaseActivityView {
        void addCommentSuccess(CommentResponse commentResponse);
        void addCommentFails(int code,String msg);
    }
    public interface Present{
        void addComment(String strategyId, String content);
    }
}
