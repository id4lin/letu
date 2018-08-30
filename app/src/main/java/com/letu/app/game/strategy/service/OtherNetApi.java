package com.letu.app.game.strategy.service;

import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.game.strategy.ui.me.bean.CollectListItemResponse;
import com.letu.app.game.strategy.ui.me.bean.GameGridItemResponse;
import com.letu.app.game.strategy.ui.other.bean.BannerListItemResponse;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;
import com.letu.app.game.strategy.ui.other.bean.PromoterIncomeListItemResponse;
import com.letu.app.game.strategy.ui.other.bean.PromoterListItemResponse;
import com.letu.app.game.strategy.ui.other.bean.VerifyTokenResponse;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by ${user} on 2018/7/15
 */
public interface OtherNetApi {

    /**
     * 验证token有效性
     * @param token
     * @return
     */
    @GET("user/veryfiyToken?")
    Flowable<BaseResponse<VerifyTokenResponse>> verifyToken(@Query("token") String token);


    /**
     * 我的游戏
     * @param token
     * @return
     */
    @GET("user/listMyGame?")
    Flowable<BaseResponse<List<GameGridItemResponse>>> fetchMyGameList(@Query("token") String token);

    /**
     * 获取我的推广列表
     * @param token
     * @param starTime【格式"2018-08-01"表示"2018-08-01 00:00:00"】
     * @param endTime
     * @return
     */
    @GET("user/spread?")
    Flowable<BaseResponse<List<PromoterListItemResponse>>> fetchMyPromoterList(@Query("token") String token,@Query("startTime")String starTime,@Query("endTime")String endTime);

    /**
     * 获取用户充值明细
     * @param token
     * @param gameId
     * @param starTime【格式"2018-08-01"表示"2018-08-01 00:00:00"】
     * @param endTime
     * @return
     */
    @GET("user/spreadIncome?")
    Flowable<BaseResponse<List<PromoterIncomeListItemResponse>>> fetchPromoterIncomeList(@Query("token") String token, @Query("gameid") String gameId, @Query("startTime")String starTime, @Query("endTime")String endTime);


    /**
     * 修改密码
     * @param token
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @FormUrlEncoded
    @POST("user/updatePassword?")
    Flowable<BaseResponse<OtherResponse>> modifyPassword(@Field("token") String token, @Field("oldPassword")String oldPassword, @Field("newPassword")String newPassword);


    /**
     * 忘记密码
     * @param phone
     * @param verifyCode
     * @param newPassword
     * @return
     */
    @FormUrlEncoded
    @POST("user/forgetPassword?")
    Flowable<BaseResponse<OtherResponse>> forgetPassword(@Field("phone") String phone, @Field("verifyCode")String verifyCode, @Field("newPassword")String newPassword);

    /**
     * 修改用户信息
     * @param token
     * @param nickName
     * @param avatarUrl
     * @return
     */
    @FormUrlEncoded
    @POST("user/updateUserInfo?")
    Flowable<BaseResponse<OtherResponse>> modifyUserInfo(@Field("token") String token, @Field("nickName")String nickName, @Field("avatarUrl")String avatarUrl);

    /**
     * 修改头像
     * @param token
     * @param file
     * @return
     */

    @Multipart
    @POST("upload/userAvatar?")
    Flowable<BaseResponse<String>> modifyAvatar(@Part("token") RequestBody token, @Part MultipartBody.Part file);

    @Multipart
    @POST("upload/image?")
    Flowable<BaseResponse<String>> uploadImage(@Part("token") RequestBody token,@Part("type") RequestBody type,@Part MultipartBody.Part file);


    //========================================发送短信======================================================
    /**
     *
     * @param token
     * @param os
     * @param gameId
     * @return
     */
    @GET("user/wantPlayGame?")
    Flowable<BaseResponse<OtherResponse>> wantPlayGame(@Query("token") String token, @Query("os") String os, @Query("gameid") String gameId);

    /**
     * 获取验证码
     * @param phone
     * @return
     */

    @GET("user/getRegistCode?")
    Flowable<BaseResponse<OtherResponse>> fetchVerifyCode(@Query("phone") String phone);

    /**
     * 获取验证码
     * @param phone
     * @param type【短信类别，（忘记密码：forgetPassword）】
     * @return
     */
    @FormUrlEncoded
    @POST("user/sendVeryfiyMessage?")
    Flowable<BaseResponse<OtherResponse>> fetchVerifyCode(@Field("phone") String phone, @Field("type")String type);

    //========================================发送短信END======================================================

    /**
     * 点赞
     * @param token
     * @param likeType【点赞类别(攻略:news)】
     * @param kindId【攻略ID】
     * @return
     */
    @GET("like/add?")
    Flowable<BaseResponse<OtherResponse>> like(@Query("token") String token, @Query("likeType") String likeType, @Query("kindid") String kindId);
    /**
     * 添加收藏
     * @param token
     * @param collectType【收藏类别(攻略:news,游戏:game)】
     * @param kindId【对应类型ID：攻略ID或游戏ID】
     * @return
     */
    @GET("collect/add?")
    Flowable<BaseResponse<OtherResponse>> collect(@Query("token") String token, @Query("collectType") String collectType, @Query("kindid") String kindId);


    /**
     * 取消收藏
     * @param token
     * @param collectType
     * @param kindId
     * @return
     */
    @GET("collect/remove?")
    Flowable<BaseResponse<OtherResponse>> removeCollect(@Query("token") String token, @Query("collectType") String collectType, @Query("kindid") String kindId);


    /**
     * 获取收藏列表
     * @param token
     * @param collectType
     * @return
     */
    @GET("collect/list?")
    Flowable<BaseResponse<List<CollectListItemResponse>>> fetchCollectList(@Query("token") String token, @Query("collectType") String collectType);


    /**
     * 获取banner图列表
     * @param bannerType【类别(攻略:news,游戏:game)】
     * @return
     */
    @GET("banner/list?")
    Flowable<BaseResponse<List<BannerListItemResponse>>> fetchBannerList(@Query("bannerType") String bannerType);
}
