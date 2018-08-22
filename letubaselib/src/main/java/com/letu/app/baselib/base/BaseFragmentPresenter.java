package com.letu.app.baselib.base;

/**
 * Created by ${user} on 2018/7/5
 */
public class BaseFragmentPresenter<V extends BaseFragmentView> {

    protected V mView;
    public void attachView(V mView){
        this.mView = mView;
    }
    public void destroyView(){
        mView = null;
    }
}

