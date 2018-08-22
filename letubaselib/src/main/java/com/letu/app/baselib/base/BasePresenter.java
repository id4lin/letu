package com.letu.app.baselib.base;



public class BasePresenter<V extends BaseActivityView>{
    protected V mView;
    public void attachView(V mView){
        this.mView = mView;
    }
    public void destroyView(){
        mView = null;
    }
}
