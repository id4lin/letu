package com.letu.app.game.strategy.ui.main.widget;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.letu.app.baselib.base.BaseActivity;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.main.adapter.MainViewPagerAdapter;
import com.letu.app.game.strategy.ui.main.contract.MainContract;
import com.letu.app.game.strategy.ui.main.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnPageChange;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.mainPageContents)
    ViewPager mainPageContents;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;

    private MainViewPagerAdapter mainViewPagerAdapter;
    private MenuItem menuItem;

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
        setContentView(R.layout.activity_main);
        mPresenter.attachView(this);
//        ButterKnife.bind(this);
        mPresenter.verifyToken();

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        mainViewPagerAdapter = new MainViewPagerAdapter(getFragmentManager());
        mainPageContents.setAdapter(mainViewPagerAdapter);
        List<Fragment> list = new ArrayList<Fragment>();
        list.add(StrategyFragment.newInstance("首页"));
        list.add(GameFragment.newInstance("游戏"));
//        list.add(AboutFragment.newInstance("关于我们"));
        list.add(MeFragment.newInstance("我的"));
        mainViewPagerAdapter.setList(list);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            menuItem = item;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mainPageContents.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    mainPageContents.setCurrentItem(1);
                    return true;
//                case R.id.navigation_notifications:
//                    mainPageContents.setCurrentItem(2);
//                    return true;
                case R.id.navigation_person:
                    mainPageContents.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @OnPageChange(value = R.id.mainPageContents,callback = OnPageChange.Callback.PAGE_SELECTED)
    public void onPageSelected(int position) {
        if (menuItem != null) {
            menuItem.setChecked(false);
        } else {
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
        }
        menuItem = bottomNavigationView.getMenu().getItem(position);
        menuItem.setChecked(true);
    }

}

