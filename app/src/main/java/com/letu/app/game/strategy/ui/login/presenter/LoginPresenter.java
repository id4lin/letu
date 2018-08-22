package com.letu.app.game.strategy.ui.login.presenter;

import android.text.TextUtils;

import com.letu.app.baselib.base.BasePresenter;
import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.baselib.net.HttpSubscriber;
import com.letu.app.baselib.utils.RxHelper;
import com.letu.app.game.strategy.bean.UserInfo;
import com.letu.app.game.strategy.service.LoginNetApi;
import com.letu.app.game.strategy.ui.login.bean.LoginResponse;
import com.letu.app.game.strategy.ui.login.contract.LoginContract;
import com.letu.app.game.strategy.ui.register.bean.RegisterResponse;
import com.letu.app.game.strategy.utils.LeTuUtils;
import com.letu.app.game.strategy.utils.SPManager;

import javax.inject.Inject;

/**
 * Created by ${user} on 2018/7/7
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Present {
    @Inject
    LoginPresenter() {

    }
    private static final String ERROR_MSG_NULL_PHONE_NUMBER = "手机号不能为空";
    private static final String ERROR_MSG_NULL_PASSWORD = "密码不能为空";
    private static final String ERROR_MSG_VALID_PHONE_NUMBER = "非法的手机号码";
    private static final String ERROR_MSG_LENGTH_VALID_PASSWORD = "密码字数应在6-18位之间";

    @Inject
    LoginNetApi loginNetApi;

    @Override
    public void login(String phone, String password) {
        mView.clearError();
        if(!checkNull(phone,password)){
            return;
        }

        if(!isPhoneNumberValid(phone)){
            mView.setPhoneNumberValidError(ERROR_MSG_VALID_PHONE_NUMBER);
            return;
        }

        if(!isPasswordLengthValid(password)){
            mView.setPasswordLengthValidError(ERROR_MSG_LENGTH_VALID_PASSWORD);
            return;
        }

        loginNetApi.login(phone,password)
                .compose(mView.getLifecycleProvider().<BaseResponse<LoginResponse>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<LoginResponse>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<LoginResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<LoginResponse> loginResponseBaseResponse) {
                        if (mView != null) {
                            saveUserLoginInfos(loginResponseBaseResponse.getData());
                            mView.loginSuccess(loginResponseBaseResponse.getData());
                        }
                    }

                    @Override
                    public void onFail(BaseResponse<LoginResponse> loginResponseBaseResponse) {
                        mView.loginFails(loginResponseBaseResponse.getCode(), loginResponseBaseResponse.getMessage());
                    }
                });
    }


    private boolean isNull(String source){
        return null == source || TextUtils.isEmpty(source);
    }

    private boolean checkNull(String phone, String password) {
        if (isNull(phone)) {
            mView.setPhoneNullError(ERROR_MSG_NULL_PHONE_NUMBER);
            return false;
        }

        if (isNull(password)) {
            mView.setPasswordNullError(ERROR_MSG_NULL_PASSWORD);
            return false;
        }

        return true;
    }

    private boolean isPhoneNumberValid(String phone) {
        return LeTuUtils.isPhoneNumberValid(phone);
    }

    /**
     * 检查密码长度是否有效[6-18]
     * @param password
     * @return
     */
    private boolean isPasswordLengthValid(String password){
        if(belowMinLength(6,password)){
            return false;
        }

        if(overMaxLength(18,password)){
            return false;
        }

        return true;
    }

    /**
     * 长度超过最大值
     * @param max
     * @param source
     * @return
     */
    private boolean overMaxLength(int max,String source){

        return source.length()>max;
    }

    /**
     * 长度小于最小值
     * @param min
     * @param source
     * @return
     */
    private boolean belowMinLength(int min,String source){
        return source.length()<min;
    }

    /**
     * 保存用户登录信息
     * @param loginResponse
     */
    private void saveUserLoginInfos(LoginResponse loginResponse){
        SPManager.getInstance().saveUserLoginInfos(loginResponse);
    }


}
