package com.letu.app.game.strategy.ui.me.presenter;

import com.letu.app.baselib.base.BasePresenter;
import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.baselib.net.HttpSubscriber;
import com.letu.app.baselib.utils.RxHelper;
import com.letu.app.game.strategy.service.OtherNetApi;
import com.letu.app.game.strategy.ui.me.contract.ModifyAvatarContract;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;
import com.letu.app.game.strategy.utils.SPManager;

import net.nightwhistler.htmlspanner.SpanCallback;

import java.io.File;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by ${user} on 2018/7/27
 */
public class ModifyAvatarPresenter extends BasePresenter<ModifyAvatarContract.View> implements ModifyAvatarContract.Present {
    @Inject
    ModifyAvatarPresenter() {

    }

    @Inject
    OtherNetApi otherNetApi;

    @Override
    public void modifyAvatar(String filePath) {
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        RequestBody token =RequestBody.create(MediaType.parse("multipart/form-data"), SPManager.getInstance().getLoginToken());
        otherNetApi.modifyAvatar(token, multipartBody)
                .compose(mView.getLifecycleProvider().<BaseResponse<String>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<String>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(BaseResponse<String> uploadImageResponseBaseResponse) {
                        SPManager.getInstance().modifyAvatar(uploadImageResponseBaseResponse.getData());
                        if (mView != null) {
                            mView.modifyAvatarSuccess(uploadImageResponseBaseResponse.getData());
                        }
                    }

                    @Override
                    public void onFail(BaseResponse<String> uploadImageResponseBaseResponse) {
                        mView.modifyAvatarFails(uploadImageResponseBaseResponse.getCode(), uploadImageResponseBaseResponse.getMessage());
                    }
                });
    }
}
