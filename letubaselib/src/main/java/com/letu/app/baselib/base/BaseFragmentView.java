package com.letu.app.baselib.base;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * Created by ${user} on 2018/7/5
 */
public interface BaseFragmentView {
    BaseFragment getFragment();
    LifecycleProvider<FragmentEvent> getLifecycleProvider();
}
