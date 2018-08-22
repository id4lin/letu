package com.letu.app.game.strategy.ui.me.widget;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.letu.app.baselib.base.BaseActivity;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.common.pullloadmoreview.PullLoadMoreRecyclerView;
import com.letu.app.game.strategy.ui.main.adapter.StrategyRecyclerViewAdapter;
import com.letu.app.game.strategy.ui.main.bean.StrategyListResponse;
import com.letu.app.game.strategy.ui.main.bean.StrategyRecyclerViewItemBean;
import com.letu.app.game.strategy.ui.me.adapter.GameGridRecyclerViewAdapter;
import com.letu.app.game.strategy.ui.me.bean.CollectListItemResponse;
import com.letu.app.game.strategy.ui.me.contract.CollectListContract;
import com.letu.app.game.strategy.ui.me.presenter.CollectListPresenter;
import com.letu.app.game.strategy.ui.strategy.widget.StrategyDetailActivity;
import com.letu.app.game.strategy.ui.strategy.widget.StrategyDetailNewActivity;
import com.letu.app.game.strategy.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectListActivity extends BaseActivity<CollectListPresenter> implements CollectListContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;

    private RecyclerView mRecyclerView;
    private StrategyRecyclerViewAdapter mStrategyRecyclerViewAdapter;

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
        setContentView(R.layout.activity_collect_list);
        mPresenter.attachView(this);

        mPresenter.fetchCollectList();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRecyclerView = mPullLoadMoreRecyclerView.getRecyclerView();
        //代码设置scrollbar无效？未解决！
        mRecyclerView.setVerticalScrollBarEnabled(true);
        //设置下拉刷新是否可见
        mPullLoadMoreRecyclerView.setRefreshing(false);
        //设置是否可以下拉刷新
        mPullLoadMoreRecyclerView.setPullRefreshEnable(false);
        //设置是否可以上拉刷新
        mPullLoadMoreRecyclerView.setPushRefreshEnable(false);
        //显示下拉刷新
        mPullLoadMoreRecyclerView.setRefreshing(false);
        //设置上拉刷新文字
        //        mPullLoadMoreRecyclerView.setFooterViewText("加载更多中...");
        //设置上拉刷新文字颜色
        //mPullLoadMoreRecyclerView.setFooterViewTextColor(R.color.white);
        //设置加载更多背景色
        //mPullLoadMoreRecyclerView.setFooterViewBackgroundColor(R.color.colorBackground);

        mPullLoadMoreRecyclerView.setLinearLayout();

        mStrategyRecyclerViewAdapter = new StrategyRecyclerViewAdapter(getActivity());
        mStrategyRecyclerViewAdapter.setOnItemClickListener(new StrategyRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position, String strategyId) {
                Intent intent = new Intent(mContext, StrategyDetailNewActivity.class);
                intent.putExtra(Constant.KEY_INTENT_STRATEGY_ID, strategyId);
                startActivity(intent);
            }
        });
        mPullLoadMoreRecyclerView.setAdapter(mStrategyRecyclerViewAdapter);
    }

    @Override
    public void fetchCollectListSuccess(List<CollectListItemResponse> collectListItemResponseResponse) {
        getData(collectListItemResponseResponse);
    }

    @Override
    public void fetchCollectListFails(int code, String msg) {

    }

    @Override
    public void fetchCollectListComplete() {

    }

    private void setRefresh() {
        mStrategyRecyclerViewAdapter.clearData();
    }

    private void getData(final List<CollectListItemResponse> collectListItemResponseResponse) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mStrategyRecyclerViewAdapter.addAllData(setList(collectListItemResponseResponse));
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                    }
                });

            }
        }, 1000);

    }

    private List<StrategyRecyclerViewItemBean> setList(List<CollectListItemResponse> collectListItemResponseResponse) {
        List<StrategyRecyclerViewItemBean> dataList = new ArrayList<>();
        if (null == collectListItemResponseResponse || collectListItemResponseResponse.isEmpty()) {
            return dataList;
        }


        for (CollectListItemResponse collectListItemResponse : collectListItemResponseResponse) {

            StrategyRecyclerViewItemBean strategyRecyclerViewItemBean = new StrategyRecyclerViewItemBean();
            strategyRecyclerViewItemBean.setStrategyId(collectListItemResponse.getId());
            strategyRecyclerViewItemBean.setTitle(collectListItemResponse.getTitle());
            strategyRecyclerViewItemBean.setCreateTime(TimeUtils.time2TextTime(collectListItemResponse.getCreattime()));
            strategyRecyclerViewItemBean.setCreateUser(collectListItemResponse.getCreatUser());
            strategyRecyclerViewItemBean.setImageView("image");
            dataList.add(strategyRecyclerViewItemBean);
        }

        return dataList;

    }
}
