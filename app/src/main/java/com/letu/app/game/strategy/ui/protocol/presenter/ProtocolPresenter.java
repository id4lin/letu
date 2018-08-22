package com.letu.app.game.strategy.ui.protocol.presenter;

import com.letu.app.baselib.base.BaseActivity;
import com.letu.app.baselib.base.BasePresenter;
import com.letu.app.game.strategy.ui.protocol.contract.ProtocolContract;

import javax.inject.Inject;

/**
 * Created by ${user} on 2018/7/7
 */
public class ProtocolPresenter extends BasePresenter<ProtocolContract.View> implements ProtocolContract.Present  {
    @Inject
    ProtocolPresenter(){

    }
}
