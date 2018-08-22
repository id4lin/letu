package com.letu.app.game.strategy.service;

import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.game.strategy.ui.game.bean.GameDetailResponse;
import com.letu.app.game.strategy.ui.main.bean.GameListItemResponse;
import com.letu.app.game.strategy.ui.main.bean.GameListResponse;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ${user} on 2018/7/15
 */
public interface GameNetApi {

    /**
     * 获取游戏列表
     * @return
     */
    @GET("game/list?")
    Flowable<BaseResponse<GameListResponse>> fetchGameList(@Query("start") int start,@Query("end") int end);

    /**
     * 获取游戏详情
     * @param gameId
     * @param os
     * @return
     */
    @GET("game/detail?")
    Flowable<BaseResponse<GameDetailResponse>> fetchGameDetail(@Query("gameId") String gameId, @Query("os")String os);



}
