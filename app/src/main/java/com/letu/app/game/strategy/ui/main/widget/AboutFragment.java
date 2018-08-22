package com.letu.app.game.strategy.ui.main.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.letu.app.baselib.base.BaseFragment;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.main.contract.AboutContract;
import com.letu.app.game.strategy.ui.main.presenter.AboutPresenter;

import butterknife.BindView;

public class AboutFragment extends BaseFragment<AboutPresenter> implements AboutContract.View {
    @BindView(R.id.fragment_test_tv)
    TextView fragmentTestTv;

    public static AboutFragment newInstance(String name) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void daggerInit() {
        DaggerAppBaseComponent.builder()
                .appComponent(getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_strategy_layout;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.get("name").toString();
            fragmentTestTv.setText(name + ", hello");
        }

    }

}
