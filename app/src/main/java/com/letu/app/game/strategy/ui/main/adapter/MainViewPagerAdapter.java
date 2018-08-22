package com.letu.app.game.strategy.ui.main.adapter;

import android.app.Fragment;;
import android.app.FragmentManager;

import com.letu.app.baselib.base.BaseFragment;

import java.util.List;


/**
 * Created by ${user} on 2018/7/5
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public void setList(List<Fragment> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }
}
