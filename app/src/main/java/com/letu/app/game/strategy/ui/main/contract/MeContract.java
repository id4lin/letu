package com.letu.app.game.strategy.ui.main.contract;

import com.letu.app.baselib.base.BaseFragmentView;
import com.letu.app.game.strategy.bean.UserInfo;

/**
 * Created by ${user} on 2018/7/7
 */
public class MeContract {
    public interface View extends BaseFragmentView {

        void initUserInfo();
        void logoutSuccess();
    }
    public interface Present{
        boolean isLogin();
        UserInfo getUserInfo();

        void logout();
        boolean isPromoter();
    }
}
