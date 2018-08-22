package com.letu.app.game.strategy.ui.me.presenter;

import android.util.Log;

import com.letu.app.baselib.base.BasePresenter;
import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.baselib.net.HttpSubscriber;
import com.letu.app.baselib.utils.RxHelper;
import com.letu.app.baselib.utils.ToastUtil;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.service.OtherNetApi;
import com.letu.app.game.strategy.ui.me.contract.ModifyNickNameContract;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;
import com.letu.app.game.strategy.utils.LeTuUtils;
import com.letu.app.game.strategy.utils.SPManager;

import javax.inject.Inject;

/**
 * Created by ${user} on 2018/7/27
 */
public class ModifyNickNamePresenter extends BasePresenter<ModifyNickNameContract.View> implements ModifyNickNameContract.Present {
    @Inject
    ModifyNickNamePresenter() {

    }

    @Inject
    OtherNetApi otherNetApi;

    @Override
    public void modifyNickName(final String nickName) {
        if (LeTuUtils.isNull(nickName)) {
            mView.setErrorNull(Constant.ERROR_MSG_NULL_NICK_NAME);
            return;
        }

        if (!isNickNameLengthValid(nickName)) {
            mView.setNickNameLengthValidError(Constant.ERROR_MSG_LENGTH_VALID_NICK_NAME);
            return;
        }

        Log.i("ModifyNickNamePresenter","==========="+nickName);

        otherNetApi.modifyUserInfo(SPManager.getInstance().getLoginToken(), nickName, "")
                .compose(mView.getLifecycleProvider().<BaseResponse<OtherResponse>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<OtherResponse>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<OtherResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<OtherResponse> otherResponseBaseResponse) {
                        if (mView != null) {
                            SPManager.getInstance().modifyNickName(nickName);
                            mView.modifyNickNameSuccess(otherResponseBaseResponse.getData());
                        }
                    }

                    @Override
                    public void onFail(BaseResponse<OtherResponse> otherResponseBaseResponse) {
                        mView.modifyNickNameFails(otherResponseBaseResponse.getCode(), otherResponseBaseResponse.getMessage());
                    }
                });

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
}
