package com.letu.app.baselib.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.letu.app.baselib.dagger.AppComponent;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.RxFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ${user} on 2018/7/5
 */
public abstract class BaseFragment <T extends BaseFragmentPresenter> extends RxFragment implements BaseFragmentView {
    @Inject
    protected T mPresenter;

    protected Context mContext;
    protected final String TAG = "Activity";
    private Unbinder unbinder;
    protected abstract void daggerInit();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        daggerInit();

        return view;
    }



    //由子类指定具体类型
    public abstract int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter!=null) {
            mPresenter.destroyView();
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
    protected AppComponent getAppComponent(){
        return BaseApplication.getAppComponent();
    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }

    /**
     * 页面跳转
     * @param activityClass 跳转的Activity
     */
    public void startActivity(Class activityClass) {
        Intent intent = new Intent(mContext, activityClass);
        mContext.startActivity(intent);
    }


    @Override
    public LifecycleProvider<FragmentEvent> getLifecycleProvider() {

        return this;
    }
}


