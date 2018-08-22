package com.letu.app.game.strategy.ui.login.contract;

import com.letu.app.baselib.base.BaseActivityView;
import com.letu.app.game.strategy.ui.login.bean.LoginResponse;

/**
 * Created by ${user} on 2018/7/7
 */
public class LoginContract {

    public interface View extends BaseActivityView {
        void loginSuccess(LoginResponse loginResponse);
        void loginFails(int code,String msg);

        void clearError();
        void setPhoneNullError(String msg);
        void setPasswordNullError(String msg);

        void setPhoneNumberValidError(String msg);

        void setPasswordLengthValidError(String msg);

    }


    public interface Present{
        void login(String phone,String password);

    }
}
