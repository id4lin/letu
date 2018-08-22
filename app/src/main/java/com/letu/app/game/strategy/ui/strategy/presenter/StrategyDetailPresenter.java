package com.letu.app.game.strategy.ui.strategy.presenter;

import android.text.TextUtils;

import com.letu.app.baselib.base.BasePresenter;
import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.baselib.net.HttpSubscriber;
import com.letu.app.baselib.utils.RxHelper;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.service.CommentNetApi;
import com.letu.app.game.strategy.service.OtherNetApi;
import com.letu.app.game.strategy.service.StrategyNetApi;
import com.letu.app.game.strategy.ui.main.bean.GameListItemResponse;
import com.letu.app.game.strategy.ui.main.bean.StrategyListResponse;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;
import com.letu.app.game.strategy.ui.strategy.bean.CommentListItemResponse;
import com.letu.app.game.strategy.ui.strategy.bean.CommentListResponse;
import com.letu.app.game.strategy.ui.strategy.bean.CommentResponse;
import com.letu.app.game.strategy.ui.strategy.bean.StrategyDetailResponse;
import com.letu.app.game.strategy.ui.strategy.contract.StrategyDetailContract;
import com.letu.app.game.strategy.utils.SPManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.FlowableTransformer;

/**
 * Created by ${user} on 2018/7/10
 */
public class StrategyDetailPresenter extends BasePresenter<StrategyDetailContract.View> implements StrategyDetailContract.Present {
    @Inject
    StrategyDetailPresenter() {
    }

    @Inject
    StrategyNetApi strategyNetApi;
    @Inject
    CommentNetApi commentNetApi;
    @Inject
    OtherNetApi otherNetApi;

    @Override
    public void fetchStrategyDetail(String strategyId) {
        strategyNetApi.fetchStrategyDetail(strategyId,SPManager.getInstance().getLoginToken())
                .compose(mView.getLifecycleProvider().<BaseResponse<StrategyDetailResponse>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<StrategyDetailResponse>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<StrategyDetailResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<StrategyDetailResponse> strategyDetailResponseBaseResponse) {
                        mView.fetchStrategyDetailSuccess(strategyDetailResponseBaseResponse.getData());
                    }

                    @Override
                    public void onFail(BaseResponse<StrategyDetailResponse> strategyDetailResponseBaseResponse) {
                        mView.fetchStrategyDetailFails(strategyDetailResponseBaseResponse.getCode(),strategyDetailResponseBaseResponse.getMessage());
                    }

                });
    }

    @Override
    public void fetctCommentList( String strategyId,int start,int end) {
        commentNetApi.fetctCommentList(SPManager.getInstance().getLoginToken(),Constant.COMMENT_TYPE_STRATEGY,strategyId,start,end)
                .compose(mView.getLifecycleProvider().<BaseResponse<CommentListResponse>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<CommentListResponse>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<CommentListResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<CommentListResponse> commentListResponseBaseResponse) {
                        mView.fetctCommentListSuccess(commentListResponseBaseResponse.getData());
                    }

                    @Override
                    public void onFail(BaseResponse<CommentListResponse> commentListResponseBaseResponse) {
                        mView.fetctCommentListFails(commentListResponseBaseResponse.getCode(),commentListResponseBaseResponse.getMessage());
                    }

                });
    }

    @Override
    public void like(String strategyId) {
        otherNetApi.like(SPManager.getInstance().getLoginToken(),Constant.COMMENT_TYPE_STRATEGY,strategyId)
                .compose(mView.getLifecycleProvider().<BaseResponse<OtherResponse>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<OtherResponse>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<OtherResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<OtherResponse> otherResponseBaseResponse) {
                        mView.likeSuccess(otherResponseBaseResponse.getData());
                    }

                    @Override
                    public void onFail(BaseResponse<OtherResponse> otherResponseBaseResponse) {
                        mView.likeFails(otherResponseBaseResponse.getCode(),otherResponseBaseResponse.getMessage());
                    }

                });
    }

    @Override
    public void collect(String strategyId) {
        otherNetApi.collect(SPManager.getInstance().getLoginToken(),Constant.COMMENT_TYPE_STRATEGY,strategyId)
                .compose(mView.getLifecycleProvider().<BaseResponse<OtherResponse>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<OtherResponse>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<OtherResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<OtherResponse> otherResponseBaseResponse) {
                        mView.collectSuccess(otherResponseBaseResponse.getData());
                    }

                    @Override
                    public void onFail(BaseResponse<OtherResponse> otherResponseBaseResponse) {
                        mView.collectFails(otherResponseBaseResponse.getCode(),otherResponseBaseResponse.getMessage());
                    }

                });
    }

    @Override
    public void removeCollect(String strategyId) {
        otherNetApi.removeCollect(SPManager.getInstance().getLoginToken(),Constant.COMMENT_TYPE_STRATEGY,strategyId)
                .compose(mView.getLifecycleProvider().<BaseResponse<OtherResponse>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<OtherResponse>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<OtherResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<OtherResponse> otherResponseBaseResponse) {
                        mView.removeCollectSuccess(otherResponseBaseResponse.getData());
                    }

                    @Override
                    public void onFail(BaseResponse<OtherResponse> otherResponseBaseResponse) {
                        mView.removeCollectFails(otherResponseBaseResponse.getCode(),otherResponseBaseResponse.getMessage());
                    }

                });
    }

    @Override
    public void wantPlayGame(String gameId) {
        if(TextUtils.isEmpty(gameId)){
            return;
        }
        otherNetApi.wantPlayGame(SPManager.getInstance().getLoginToken(),Constant.OS_NAME_ANDROID,gameId)
                .compose(mView.getLifecycleProvider().<BaseResponse<OtherResponse>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<OtherResponse>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<OtherResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<OtherResponse> otherResponseBaseResponse) {
                        mView.wantPlayGameSuccess(otherResponseBaseResponse.getData());
                    }

                    @Override
                    public void onFail(BaseResponse<OtherResponse> otherResponseBaseResponse) {
                        mView.wantPlayGameFails(otherResponseBaseResponse.getCode(),otherResponseBaseResponse.getMessage());
                    }

                });
    }

    @Override
    public boolean isLogin() {
        return SPManager.getInstance().isLogin();
    }
}
