package com.letu.app.game.strategy.ui.protocol.widget;

import android.os.Bundle;

import com.letu.app.baselib.base.BaseActivity;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.protocol.contract.ProtocolContract;
import com.letu.app.game.strategy.ui.protocol.presenter.ProtocolPresenter;

public class ProtocolActivity extends BaseActivity<ProtocolPresenter> implements ProtocolContract.View {

    @Override
    protected void daggerInit() {
        DaggerAppBaseComponent.builder()
                .appComponent(getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);
    }
}
