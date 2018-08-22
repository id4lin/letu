package com.letu.app.game.strategy;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.letu.app.baselib.base.BaseApplication;
import com.letu.app.game.strategy.utils.SPManager;

/**
 * Created by ${user} on 2018/7/5
 */
public class LetuApplicaiton extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        SPManager.getInstance().init(this,null);
        Fresco.initialize(this);

    }


}
