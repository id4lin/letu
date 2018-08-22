package com.letu.app.baselib.dagger;


import com.letu.app.baselib.base.BaseApplication;
import com.letu.app.baselib.base.BaseDataCache;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;


@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {
    Retrofit provideRetrofit();

    BaseApplication providesApplication();

    BaseDataCache provideDataCache();
}
