package com.letu.app.game.strategy.service;

import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.game.strategy.ui.strategy.bean.StrategyDetailResponse;
import com.letu.app.game.strategy.ui.main.bean.StrategyListResponse;
import com.letu.app.game.strategy.ui.strategy.bean.StrategyResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ${user} on 2018/7/15
 */
public interface StrategyNetApi {
    /**
     * 获取攻略列表
     * @param start【起始index】
     * @param end【结束index】
     * @return
     */
    @GET("news/list?")
    Flowable<BaseResponse<StrategyListResponse>> fetchStrategyList(@Query("start") int start, @Query("end") int end);
    /**
     * 获取攻略列表
     * @param start【起始index】
     * @param end【结束index】
     * @param gameId【游戏ID】
     * @return
     */
    @GET("news/list?")
    Flowable<BaseResponse<StrategyListResponse>> fetchStrategyList(@Query("start") int start, @Query("end") int end,@Query("gameId")String gameId);

    /**
     * 获取攻略详情
     * @param id【攻略ID】
     * @return
     */
    @GET("news/get?")
    Flowable<BaseResponse<StrategyDetailResponse>> fetchStrategyDetail(@Query("id") String id);
    /**
     * 获取攻略详情
     * @param id【攻略ID】
     * @param token
     * @return
     */
    @GET("news/get?")
    Flowable<BaseResponse<StrategyDetailResponse>> fetchStrategyDetail(@Query("id") String id,@Query("token")String token);

    /**
     * 添加攻略
     * @param token
     * @param gameId
     * @param title
     * @param typeOne
     * @param typeTwo
     * @param content
     * @return
     */
    @GET("news/add?")
    Flowable<BaseResponse<StrategyResponse>> addStrategy(@Query("token") String token, @Query("gameId") String gameId, @Query("title") String title, @Query("typeOne") String typeOne, @Query("typeTwo") String typeTwo, @Query("content") String content);

    /**
     * 修改攻略
     * @param token
     * @param id【攻略ID】
     * @param gameId
     * @param title
     * @param typeOne
     * @param typeTwo
     * @param content
     * @return
     */
    @GET("news/update?")
    Flowable<BaseResponse<StrategyResponse>> updateStrategy(@Query("token") String token,@Query("id")String id, @Query("gameId") String gameId, @Query("title") String title, @Query("typeOne") String typeOne, @Query("typeTwo") String typeTwo, @Query("content") String content);
}
