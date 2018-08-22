package com.letu.app.game.strategy.service;

import com.letu.app.baselib.base.BaseResponse;
import com.letu.app.game.strategy.ui.login.bean.LoginResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ${user} on 2018/7/13
 */
public interface LoginNetApi {

    /**
     * 登录
     * @param phone
     * @param password
     * @return
     */
    @GET("user/login?")
    Flowable<BaseResponse<LoginResponse>> login(@Query("phone") String phone, @Query("password") String password);



}
