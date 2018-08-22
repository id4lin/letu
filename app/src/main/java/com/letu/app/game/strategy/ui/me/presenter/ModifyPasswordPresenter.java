package com.letu.app.game.strategy.ui.me.presenter;

import com.letu.app.baselib.base.BasePresenter;
import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.baselib.net.HttpSubscriber;
import com.letu.app.baselib.utils.RxHelper;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.service.OtherNetApi;
import com.letu.app.game.strategy.ui.me.contract.ModifyPasswordContract;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;
import com.letu.app.game.strategy.utils.LeTuUtils;
import com.letu.app.game.strategy.utils.SPManager;

import javax.inject.Inject;

/**
 * Created by ${user} on 2018/7/27
 */
public class ModifyPasswordPresenter extends BasePresenter<ModifyPasswordContract.View> implements ModifyPasswordContract.Present {
    @Inject
    ModifyPasswordPresenter() {

    }

    @Inject
    OtherNetApi otherNetApi;

    @Override
    public void modifyPassword(String oldPassword, String newPassword) {
        mView.clearError();
        if (!checkNull(oldPassword, newPassword)) {
            return;
        }

        if (!isPasswordLengthValid(oldPassword)) {
            mView.setOldPasswordLengthValidError(Constant.ERROR_MSG_LENGTH_VALID_PASSWORD);
            return;
        }
        if (!isPasswordLengthValid(newPassword)) {
            mView.setNewPasswordLengthValidError(Constant.ERROR_MSG_LENGTH_VALID_PASSWORD);
            return;
        }

        if (!isPasswordValid(oldPassword)) {
            mView.setOldPasswordValidError(Constant.ERROR_MSG_VALID_PASSWORD);
            return;
        }
        if (!isPasswordValid(oldPassword)) {
            mView.setNewPasswordValidError(Constant.ERROR_MSG_VALID_PASSWORD);
            return;
        }

        otherNetApi.modifyPassword(SPManager.getInstance().getLoginToken(), oldPassword, newPassword)
                .compose(mView.getLifecycleProvider().<BaseResponse<OtherResponse>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<OtherResponse>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<OtherResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<OtherResponse> otherResponseBaseResponse) {
                        if (mView != null) {
                            mView.modifyPasswordSuccess(otherResponseBaseResponse.getData());
                        }
                    }

                    @Override
                    public void onFail(BaseResponse<OtherResponse> otherResponseBaseResponse) {
                        mView.modifyPasswordFails(otherResponseBaseResponse.getCode(), otherResponseBaseResponse.getMessage());
                    }
                });
    }

    private boolean isNull(String source) {
        return LeTuUtils.isNull(source);
    }

    private boolean checkNull(String oldPassword, String newPassword) {
        if (isNull(oldPassword)) {
            mView.setOldPasswordNullError(Constant.ERROR_MSG_NULL_PASSWORD);
            return false;
        }

        if (isNull(newPassword)) {
            mView.setNewPasswordNullError(Constant.ERROR_MSG_NULL_PASSWORD);
            return false;
        }

        return true;
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
     * 检查密码长度是否有效[6-18]
     *
     * @param password
     * @return
     */
    private boolean isPasswordLengthValid(String password) {
        return LeTuUtils.isPasswordLengthValid(password);
    }
}
