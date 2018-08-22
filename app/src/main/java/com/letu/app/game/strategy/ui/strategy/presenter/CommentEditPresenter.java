package com.letu.app.game.strategy.ui.strategy.presenter;

import com.letu.app.baselib.base.BasePresenter;
import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.baselib.net.HttpSubscriber;
import com.letu.app.baselib.utils.RxHelper;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.service.CommentNetApi;
import com.letu.app.game.strategy.ui.game.bean.GameDetailResponse;
import com.letu.app.game.strategy.ui.strategy.bean.CommentResponse;
import com.letu.app.game.strategy.ui.strategy.contract.CommentEditContract;
import com.letu.app.game.strategy.utils.SPManager;

import javax.inject.Inject;

/**
 * Created by ${user} on 2018/7/11
 */
public class CommentEditPresenter extends BasePresenter<CommentEditContract.View> implements CommentEditContract.Present {
    @Inject
    CommentEditPresenter() {
    }

    @Inject
    CommentNetApi commentNetApi;

    @Override
    public void addComment(String strategyId, String content) {
        commentNetApi.addComment(SPManager.getInstance().getLoginToken(), Constant.COMMENT_TYPE_STRATEGY, strategyId, content)
                .compose(mView.getLifecycleProvider().<BaseResponse<CommentResponse>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<CommentResponse>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<CommentResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<CommentResponse> commentResponseBaseResponse) {
                        if (mView != null) {
                            mView.addCommentSuccess(commentResponseBaseResponse.getData());
                        }
                    }

                    @Override
                    public void onFail(BaseResponse<CommentResponse> commentResponseBaseResponse) {
                        mView.addCommentFails(commentResponseBaseResponse.getCode(), commentResponseBaseResponse.getMessage());
                    }
                });

    }
}
