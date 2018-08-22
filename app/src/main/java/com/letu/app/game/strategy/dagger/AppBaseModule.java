package com.letu.app.game.strategy.dagger;

import com.letu.app.baselib.dagger.PerActivity;
import com.letu.app.game.strategy.service.CommentNetApi;
import com.letu.app.game.strategy.service.GameNetApi;
import com.letu.app.game.strategy.service.LoginNetApi;
import com.letu.app.game.strategy.service.OtherNetApi;
import com.letu.app.game.strategy.service.RegisterNetApi;
import com.letu.app.game.strategy.service.StrategyNetApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AppBaseModule {

    @PerActivity
    @Provides
    RegisterNetApi providesRegistNetApi(Retrofit retrofit) {
        return retrofit.create(RegisterNetApi.class);
    }

    @PerActivity
    @Provides
    LoginNetApi providesLoginNetApi(Retrofit retrofit) {
        return retrofit.create(LoginNetApi.class);
    }

    @PerActivity
    @Provides
    GameNetApi providesGameNetApi(Retrofit retrofit) {
        return retrofit.create(GameNetApi.class);
    }

    @PerActivity
    @Provides
    StrategyNetApi providesStrategyNetApi(Retrofit retrofit) {
        return retrofit.create(StrategyNetApi.class);
    }

    @PerActivity
    @Provides
    CommentNetApi providesCommentNetApi(Retrofit retrofit) {
        return retrofit.create(CommentNetApi.class);
    }

    @PerActivity
    @Provides
    OtherNetApi providesOtherNetApi(Retrofit retrofit) {
        return retrofit.create(OtherNetApi.class);
    }
}
