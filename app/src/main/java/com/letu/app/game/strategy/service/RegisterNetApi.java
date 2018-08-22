package com.letu.app.game.strategy.service;

import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.game.strategy.ui.register.bean.RegisterResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ${user} on 2018/7/13
 */
public interface RegisterNetApi {

    /**
     * 注册
     * @param phone
     * @param password
     * @param verifyCode
     * @param nick
     * @return
     */
    @GET("user/regist?")
    Flowable<BaseResponse<RegisterResponse>> regist(@Query("phone") String phone, @Query("password") String password, @Query("verifyCode") String verifyCode, @Query("nickName")String nick);
}
