package com.letu.app.game.strategy.ui.main.presenter;

import com.letu.app.baselib.base.BaseFragmentPresenter;
import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.baselib.net.HttpSubscriber;
import com.letu.app.baselib.utils.RxHelper;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.service.GameNetApi;
import com.letu.app.game.strategy.service.OtherNetApi;
import com.letu.app.game.strategy.ui.main.bean.GameListItemResponse;
import com.letu.app.game.strategy.ui.main.bean.GameListResponse;
import com.letu.app.game.strategy.ui.main.contract.GameContract;
import com.letu.app.game.strategy.ui.other.bean.BannerListItemResponse;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ${user} on 2018/7/5
 */
public class GamePresenter extends BaseFragmentPresenter<GameContract.View> implements GameContract.Present {
    @Inject
    GamePresenter() {

    }

    @Inject
    GameNetApi gameNetApi;

    @Inject
    OtherNetApi otherNetApi;


    @Override
    public void fecthGameList(int start,int end) {
        gameNetApi.fetchGameList(start,end)
                .compose(mView.getLifecycleProvider().<BaseResponse<GameListResponse>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<GameListResponse>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<GameListResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<GameListResponse> gameListResponseBaseResponse) {
                        if (mView != null) {
                            mView.fecthGameListSuccess(gameListResponseBaseResponse.getData());
                        }
                    }

                    @Override
                    public void onFail(BaseResponse<GameListResponse> gameListResponseBaseResponse) {
                        mView.fecthGameListFails(gameListResponseBaseResponse.getCode(), gameListResponseBaseResponse.getMessage());
                    }


                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.fecthGameListError();
                    }
                });
    }

    @Override
    public void fetchBannerList() {
        otherNetApi.fetchBannerList(Constant.BANNER_TYPE_GAME)
                .compose(mView.getLifecycleProvider().<BaseResponse<List<BannerListItemResponse>>>bindToLifecycle())
                .compose(RxHelper.<BaseResponse<List<BannerListItemResponse>>>io_main())
                .subscribe(new HttpSubscriber<BaseResponse<List<BannerListItemResponse>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<BannerListItemResponse>> bannerListResponseBaseResponse) {
                        mView.fetchBannerListSuccess(bannerListResponseBaseResponse.getData());
                    }

                    @Override
                    public void onFail(BaseResponse<List<BannerListItemResponse>> bannerListResponseBaseResponse) {
                        mView.fetchBannerListFails(bannerListResponseBaseResponse.getCode(),bannerListResponseBaseResponse.getMessage());
                    }


                });
    }
}
