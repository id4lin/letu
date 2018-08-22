package com.letu.app.game.strategy.ui.login.contract;

import com.letu.app.baselib.base.BaseActivityView;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;

/**
 * Created by ${user} on 2018/7/26
 */
public class ForgetPasswordContract {
    public interface View extends BaseActivityView {

        void forgetPasswordSuccess(OtherResponse otherResponse);
        void forgetPasswordFails(int code,String msg);

        void clearError();
        void setPhoneNullError(String msg);
        void setVerifyCodeNullError(String msg);
        void setPasswordNullError(String msg);

        void setPhoneNumberValidError(String msg);
        void setPasswordValidError(String msg);

        void setVerifyCodeLengthValidError(String msg);
        void setPasswordLengthValidError(String msg);


        /**
         * 启动验证码获取倒计时计数器
         */
        void startVerifyCodeCountDownTimer();

        /**
         * 获取验证码成功
         * @param otherResponse
         */
        void fetchVerifyCodeSuccess(OtherResponse otherResponse);
        void fetchVerifyCodeFails(int code,String msg);

    }


    public interface Present{
        /**
         * 忘记密码
         * @param phone
         * @param verifyCode
         * @param password
         */
        void forgetPassword(String phone,String verifyCode,String password );

        /**
         * 获取验证码
         * @param phone
         */
        void fetchVerifyCode(String phone);

    }
}
