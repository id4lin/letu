package com.letu.app.game.strategy.ui.register.contract;

import com.letu.app.baselib.base.BaseActivityView;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;
import com.letu.app.game.strategy.ui.register.bean.RegisterResponse;

import retrofit2.http.Query;

/**
 * Created by ${user} on 2018/7/7
 */
public class RegisterContract {
    public interface View extends BaseActivityView {
        void registerSuccess(RegisterResponse response);
        void registerFails(int code,String msg);

        void clearError();
        void setPhoneNullError(String msg);
        void setNickNullError(String msg);
        void setVerifyCodeNullError(String msg);
        void setPasswordNullError(String msg);

        void setPhoneNumberValidError(String msg);
        void setPasswordValidError(String msg);

        void setVerifyCodeLengthValidError(String msg);
        void setNickNameLengthValidError(String msg);
        void setPasswordLengthValidError(String msg);

        void setAcceptUserProtocolError(String msg);

        boolean isAcceptUserProtocol();

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
         * 注册
         * @param phone
         * @param password
         * @param verifyCode
         * @param nick
         */
        void register(String phone,String password,String verifyCode,String nick );

        /**
         * 获取验证码
         * @param phone
         */
        void fetchVerifyCode(String phone);
    }
}
