package com.letu.app.game.strategy.service;

import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.game.strategy.ui.strategy.bean.CommentListItemResponse;
import com.letu.app.game.strategy.ui.strategy.bean.CommentListResponse;
import com.letu.app.game.strategy.ui.strategy.bean.CommentResponse;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ${user} on 2018/7/15
 */
public interface CommentNetApi {
    /**
     * 获取所有评论列表
     *
     * @param token【填写后可以识别出自己的评论】
     * @param commentType【评论类型：news】
     * @param kindId【类型id,（攻略ID,）】
     * @return
     */
    @GET("comment/get?")
    Flowable<BaseResponse<List<CommentListItemResponse>>> fetctAllCommentList(@Query("token") String token, @Query("commentType") String commentType, @Query("kindid") String kindId);

    /**
     * 获取评论列表
     * @param token【填写后可以识别出自己的评论】
     * @param commentType【评论类型：news】
     * @param kindId【类型id,（攻略ID,）】
     * @param start
     * @param end
     * @return
     */
    @GET("comment/list?")
    Flowable<BaseResponse<CommentListResponse>> fetctCommentList(@Query("token") String token, @Query("commentType") String commentType, @Query("kindid") String kindId, @Query("start") int start, @Query("end") int end);

    /**
     * 添加评论
     *
     * @param token
     * @param token
     * @param commentType【评论类型：news】
     * @param kindId【类型id,（攻略ID,）】
     * @return
     */
    @GET("comment/add?")
    Flowable<BaseResponse<CommentResponse>> addComment(@Query("token") String token, @Query("commentType") String commentType, @Query("kindid") String kindId, @Query("content") String content);

    /**
     * 修改评论
     *
     * @param token
     * @param id【评论ID】
     * @param commentType【评论类型：news】
     * @param content
     * @return
     */
    @GET("comment/update?")
    Flowable<BaseResponse<CommentResponse>> updateComment(@Query("token") String token, @Query("id") String id, @Query("commentType") String commentType, @Query("content") String content);


}
