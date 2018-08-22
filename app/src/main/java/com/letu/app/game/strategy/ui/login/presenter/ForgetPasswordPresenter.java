package com.letu.app.game.strategy.ui.login.presenter;

import android.text.TextUtils;

import com.letu.app.baselib.base.BasePresenter;
import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.baselib.net.HttpSubscriber;
import com.letu.app.baselib.utils.RxHelper;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.service.OtherNetApi;
import com.letu.app.game.strategy.ui.login.contract.ForgetPasswordContract;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;
import com.letu.app.game.strategy.ui.register.bean.RegisterResponse;
import com.letu.app.game.strategy.utils.LeTuUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

/**
 * Created by ${user} on 2018/7/26
 */
public class ForgetPasswordPresenter extends BasePresenter<ForgetPasswordContract.View> implements ForgetPasswordContract.Present {

    @Inject
    ForgetPasswordPresenter() {

    }

    @Inject
    OtherNetApi otherNetApi;

    @Override
    public void forgetPassword(String phone, String verifyCode, String password) {
        mView.clearError();

        if (!checkNull(phone, password, verifyCode)) {
            return;
        }

        if (!isPhoneNumberValid(phone)) {
            mView.setPhoneNumberValidError(Constant.ERROR_MSG_VALID_PHONE_NUMBER);
            return;
        }

        if (!isVerifyCodeLengthValid(verifyCode)) {
            mView.setVerifyCodeLengthValidError(Constant.ERROR_MSG_LENGTH_VALID_VERIFY_CODE);
            return;
        }


        if (!isPasswordLengthValid(password)) {
            mView.setPasswordLengthValidError(Constant.ERROR_MSG_LENGTH_VALID_PASSWORD);
            return;
        }

        if (!isPasswordValid(password)) {
            mView.setPasswordValidError(Constant.ERROR_MSG_VALID_PASSWORD);
            return;
        }

        otherNetApi.forgetPassword(phone, verifyCode, password)
                .compose(mView.getLifecycleProvider().<BaseResponse<OtherResponse>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<OtherResponse>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<OtherResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<OtherResponse> otherResponseBaseResponse) {
                        if (mView != null) {
                            mView.forgetPasswordSuccess(otherResponseBaseResponse.getData());
                        }
                    }

                    @Override
                    public void onFail(BaseResponse<OtherResponse> otherResponseBaseResponse) {
                        mView.forgetPasswordFails(otherResponseBaseResponse.getCode(), otherResponseBaseResponse.getMessage());
                    }
                });
    }

    @Override
    public void fetchVerifyCode(String phone) {
        mView.clearError();
        if (isNull(phone)) {
            mView.setPhoneNullError(Constant.ERROR_MSG_NULL_PHONE_NUMBER);
            return;
        }

        if (!isPhoneNumberValid(phone)) {
            mView.setPhoneNumberValidError(Constant.ERROR_MSG_VALID_PHONE_NUMBER);
            return;
        }

        otherNetApi.fetchVerifyCode(phone, Constant.SEND_MSG_TYPE_FORGET_PASSWORD)
                .compose(mView.getLifecycleProvider().<BaseResponse<OtherResponse>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<OtherResponse>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<OtherResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<OtherResponse> otherResponseBaseResponse) {
                        if (mView != null) {
                            mView.startVerifyCodeCountDownTimer();
                            mView.fetchVerifyCodeSuccess(otherResponseBaseResponse.getData());
                        }
                    }

                    @Override
                    public void onFail(BaseResponse<OtherResponse> otherResponseBaseResponse) {
                        mView.fetchVerifyCodeFails(otherResponseBaseResponse.getCode(), otherResponseBaseResponse.getMessage());
                    }
                });

    }

    private boolean isNull(String source) {
        return LeTuUtils.isNull(source);
    }

    private boolean checkNull(String phone, String password, String verifyCode) {
        if (isNull(phone)) {
            mView.setPhoneNullError(Constant.ERROR_MSG_NULL_PHONE_NUMBER);
            return false;
        }
        if (isNull(verifyCode)) {
            mView.setVerifyCodeNullError(Constant.ERROR_MSG_NULL_VERIFY_CODE);
            return false;
        }

        if (isNull(password)) {
            mView.setPasswordNullError(Constant.ERROR_MSG_NULL_PASSWORD);
            return false;
        }

        return true;
    }

    private boolean isPhoneNumberValid(String phone) {
        return LeTuUtils.isPhoneNumberValid(phone);
    }

    /**
     * 密码有字母或数字
     *
     * @param password
     * @return
     */
    private boolean isPasswordValid(String password) {

        return LeTuUtils.isPasswordValid(password);
    }


    /**
     * 检查验证码长度是否有效[6]
     *
     * @param verifyCode
     * @return
     */
    private boolean isVerifyCodeLengthValid(String verifyCode) {
       return LeTuUtils.isVerifyCodeLengthValid(verifyCode);

    }


    /**
     * 检查密码长度是否有效[6-18]
     *
     * @param password
     * @return
     */
    private boolean isPasswordLengthValid(String password) {
        return LeTuUtils.isPasswordLengthValid(password);
    }


}
