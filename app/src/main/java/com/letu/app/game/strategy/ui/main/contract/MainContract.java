package com.letu.app.game.strategy.ui.main.contract;

import com.letu.app.baselib.base.BaseActivityView;

/**
 * Created by ${user} on 2018/7/5
 */
public class MainContract {
    public interface View extends BaseActivityView {

    }

    public interface Present{
        /**
         * 检查token的有效性
         */
        void verifyToken();
    }
}
