package com.letu.app.game.strategy.ui.strategy.presenter;

import android.text.TextUtils;

import com.letu.app.baselib.base.BasePresenter;
import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.baselib.net.HttpSubscriber;
import com.letu.app.baselib.utils.RxHelper;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.service.OtherNetApi;
import com.letu.app.game.strategy.service.StrategyNetApi;
import com.letu.app.game.strategy.ui.main.bean.StrategyListResponse;
import com.letu.app.game.strategy.ui.strategy.bean.StrategyResponse;
import com.letu.app.game.strategy.ui.strategy.contract.StrategyEditContract;
import com.letu.app.game.strategy.utils.BitmapUtils;
import com.letu.app.game.strategy.utils.SPManager;

import java.io.File;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by ${user} on 2018/7/18
 */
public class StrategyEditPresenter extends BasePresenter<StrategyEditContract.View> implements StrategyEditContract.Present {
    @Inject
    StrategyEditPresenter() {
    }

    @Inject
    StrategyNetApi strategyNetApi;
    @Inject
    OtherNetApi otherNetApi;

    @Override
    public void addStrategy(String gameId, String title, String content) {
        mView.clearAllError();
        if(TextUtils.isEmpty(title)){
            mView.setNullErrorTitle("标题不能为空");
            return;
        }

        if(TextUtils.isEmpty(content)){
            mView.setNullErrorTitle("内容不能为空");
            return;
        }

        strategyNetApi.addStrategy(SPManager.getInstance().getLoginToken(),gameId,title,null,null,content)
                .compose(mView.getLifecycleProvider().<BaseResponse<StrategyResponse>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<StrategyResponse>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<StrategyResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<StrategyResponse> strategyListResponseBaseResponse) {
                        mView.addStrategySuccess(strategyListResponseBaseResponse.getData());
                    }

                    @Override
                    public void onFail(BaseResponse<StrategyResponse> strategyListResponseBaseResponse) {
                        mView.addStrategyFails(strategyListResponseBaseResponse.getCode(),strategyListResponseBaseResponse.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mView.addStrategyComplete();
                    }

                });
    }

    @Override
    public boolean isLogin() {
        return SPManager.getInstance().isLogin();
    }

    @Override
    public void uploadImage(String imagePath) {
//        File file = BitmapUtils.compressImageByPath(imagePath,200);

        File file =new File(imagePath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        RequestBody token =RequestBody.create(MediaType.parse("multipart/form-data"), SPManager.getInstance().getLoginToken());
        RequestBody type =RequestBody.create(MediaType.parse("multipart/form-data"), Constant.IMAGE_TYPE_STRATEGY);
        otherNetApi.uploadImage(token,type, multipartBody)
                .compose(mView.getLifecycleProvider().<BaseResponse<String>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<String>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<String>>() {
                    @Override
                    public void onSuccess(BaseResponse<String> uploadImageResponseBaseResponse) {
                        SPManager.getInstance().modifyAvatar(uploadImageResponseBaseResponse.getData());
                        if (mView != null) {
                            mView.uploadImageSuccess(uploadImageResponseBaseResponse.getData());
                        }
                    }

                    @Override
                    public void onFail(BaseResponse<String> uploadImageResponseBaseResponse) {
                        mView.uploadImageFails(uploadImageResponseBaseResponse.getCode(), uploadImageResponseBaseResponse.getMessage());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.uploadImageError(e);

                    }
                });
    }
}
