package com.letu.app.game.strategy.ui.register.presenter;

import android.util.Log;

import com.letu.app.baselib.base.BasePresenter;
import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.baselib.net.HttpSubscriber;
import com.letu.app.baselib.utils.RxHelper;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.service.OtherNetApi;
import com.letu.app.game.strategy.service.RegisterNetApi;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;
import com.letu.app.game.strategy.ui.register.bean.RegisterResponse;
import com.letu.app.game.strategy.ui.register.contract.RegisterContract;
import com.letu.app.game.strategy.utils.LeTuUtils;

import javax.inject.Inject;

/**
 * Created by ${user} on 2018/7/7
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Present {
    private static final String TAG = RegisterPresenter.class.getName();

    @Inject
    RegisterPresenter() {
    }

    @Inject
    RegisterNetApi registerNetApi;

    @Inject
    OtherNetApi otherNetApi;

    @Override
    public void register(String phone, String password, String verifyCode, String nick) {
        Log.i(TAG, phone + "--" + password + "--" + verifyCode + "--" + nick);

        mView.clearError();

        if (!mView.isAcceptUserProtocol()) {
            mView.setAcceptUserProtocolError("");
            return;
        }

        if (!checkNull(phone, password, verifyCode, nick)) {
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

        if (!isNickNameLengthValid(nick)) {
            mView.setNickNameLengthValidError(Constant.ERROR_MSG_LENGTH_VALID_NICK_NAME);
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

        registerNetApi.regist(phone, password, verifyCode, nick)
                .compose(mView.getLifecycleProvider().<BaseResponse<RegisterResponse>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<RegisterResponse>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<RegisterResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<RegisterResponse> registerResponseBaseResponse) {
                        if (mView != null) {
                            mView.registerSuccess(registerResponseBaseResponse.getData());
                        }
                    }

                    @Override
                    public void onFail(BaseResponse<RegisterResponse> registerResponseBaseResponse) {
                        mView.registerFails(registerResponseBaseResponse.getCode(), registerResponseBaseResponse.getMessage());
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

        otherNetApi.fetchVerifyCode(phone)
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


    private boolean checkNull(String phone, String password, String verifyCode, String nick) {
        if (isNull(phone)) {
            mView.setPhoneNullError(Constant.ERROR_MSG_NULL_PHONE_NUMBER);
            return false;
        }
        if (isNull(verifyCode)) {
            mView.setVerifyCodeNullError(Constant.ERROR_MSG_NULL_VERIFY_CODE);
            return false;
        }

        if (isNull(nick)) {
            mView.setNickNullError(Constant.ERROR_MSG_NULL_NICK_NAME);
            return false;
        }
        if (isNull(password)) {
            mView.setPasswordNullError(Constant.ERROR_MSG_NULL_PASSWORD);
            return false;
        }

        return true;
    }



    private boolean isNickNameValid(String nickName) {
        return false;
    }


    /**
     * 检查昵称长度是否有效[1-10]
     *
     * @param nickName
     * @return
     */
    private boolean isNickNameLengthValid(String nickName) {
        return LeTuUtils.isNickNameLengthValid(nickName);
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
