package com.letu.app.baselib.base;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;


public interface BaseActivityView{
    BaseActivity getActivity();
    LifecycleProvider<ActivityEvent> getLifecycleProvider();
}
