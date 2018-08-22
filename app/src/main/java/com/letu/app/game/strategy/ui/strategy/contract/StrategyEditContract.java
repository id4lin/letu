package com.letu.app.game.strategy.ui.strategy.contract;

import com.letu.app.baselib.base.BaseActivityView;
import com.letu.app.game.strategy.ui.strategy.bean.StrategyResponse;

/**
 * Created by ${user} on 2018/7/18
 */
public class StrategyEditContract {
    public interface View extends BaseActivityView {
        void addStrategySuccess(StrategyResponse strategyResponse);
        void addStrategyFails(int code,String msg);
        void addStrategyComplete();

        void setNullErrorTitle(String msg);
        void setNullErrorContent(String msg);
        void clearAllError();
        void setFileSizeError(String msg);

        void uploadImageSuccess(String imagePath);
        void uploadImageFails(int code,String msg);
        void uploadImageError(Throwable e);
    }
    public interface Present{
        void addStrategy(String gameId,String title, String content);
        boolean isLogin();

        void uploadImage(String imagePath);
    }
}
