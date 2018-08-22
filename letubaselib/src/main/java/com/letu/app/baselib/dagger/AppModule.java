package com.letu.app.baselib.dagger;


import com.letu.app.baselib.base.BaseApplication;
import com.letu.app.baselib.base.BaseDataCache;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {
    private BaseApplication baseApplication;

    public AppModule(BaseApplication baseApplication) {
        this.baseApplication = baseApplication;
    }

    @Provides
    @Singleton
    BaseApplication providesApplication() {
        return baseApplication;
    }

    @Provides
    @Singleton
    BaseDataCache provideDataCache(){return BaseDataCache.getInstance();}
}
