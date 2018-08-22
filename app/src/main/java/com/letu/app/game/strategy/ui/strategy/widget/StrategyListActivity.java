package com.letu.app.game.strategy.ui.strategy.widget;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.letu.app.baselib.base.BaseActivity;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.common.pullloadmoreview.PullLoadMoreRecyclerView;
import com.letu.app.game.strategy.ui.main.adapter.StrategyRecyclerViewAdapter;
import com.letu.app.game.strategy.ui.main.bean.StrategyListResponse;
import com.letu.app.game.strategy.ui.main.bean.StrategyRecyclerViewItemBean;
import com.letu.app.game.strategy.ui.strategy.contract.StrategyListContract;
import com.letu.app.game.strategy.ui.strategy.presenter.StrategyListPresenter;
import com.letu.app.game.strategy.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StrategyListActivity extends BaseActivity<StrategyListPresenter> implements StrategyListContract.View {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;

    private RecyclerView mRecyclerView;
    private StrategyRecyclerViewAdapter mStrategyRecyclerViewAdapter;
    private int currentStartCount=0;

    private String gameId;
    private String gameName;


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
        setContentView(R.layout.activity_strategy_list);
        mPresenter.attachView(this);

        Intent intent = getIntent();
        gameId=intent.getStringExtra(Constant.KEY_INTENT_GAME_ID);
        gameName=intent.getStringExtra(Constant.KEY_INTENT_GAME_NAME);
//        title.setText(gameName+"的攻略");

        StringBuffer sb = new StringBuffer();
        sb.append("<font color='#FFA042'>《"+gameName+"》</font>的攻略");
        title.setText(Html.fromHtml(sb.toString()));

        mPresenter.fetchStrategyList(Constant.DATA_COUNT_DEFAULT_START,Constant.DATA_COUNT_MAX,gameId);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });

        mRecyclerView = mPullLoadMoreRecyclerView.getRecyclerView();
        //代码设置scrollbar无效？未解决！
        mRecyclerView.setVerticalScrollBarEnabled(true);
        //设置下拉刷新是否可见
        //mPullLoadMoreRecyclerView.setRefreshing(true);
        //设置是否可以下拉刷新
        //mPullLoadMoreRecyclerView.setPullRefreshEnable(true);
        //设置是否可以上拉刷新
        //mPullLoadMoreRecyclerView.setPushRefreshEnable(false);
        //显示下拉刷新
        mPullLoadMoreRecyclerView.setRefreshing(true);
        //设置上拉刷新文字
        mPullLoadMoreRecyclerView.setFooterViewText("加载更多中...");
        //设置上拉刷新文字颜色
        //mPullLoadMoreRecyclerView.setFooterViewTextColor(R.color.white);
        //设置加载更多背景色
        //mPullLoadMoreRecyclerView.setFooterViewBackgroundColor(R.color.colorBackground);

        mPullLoadMoreRecyclerView.setLinearLayout();

        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                setRefresh();
                mPresenter.fetchStrategyList(0,20,gameId);
            }

            @Override
            public void onLoadMore() {
                mPresenter.fetchStrategyList(currentStartCount,currentStartCount+20,gameId);
            }
        });
        mStrategyRecyclerViewAdapter = new StrategyRecyclerViewAdapter(getActivity());
        mStrategyRecyclerViewAdapter.setOnItemClickListener(new StrategyRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position,String strategyId) {
                Intent intent=new Intent(mContext,StrategyDetailNewActivity.class);
                intent.putExtra(Constant.KEY_INTENT_STRATEGY_ID,strategyId);
                startActivity(intent);
            }
        });
        mPullLoadMoreRecyclerView.setAdapter(mStrategyRecyclerViewAdapter);


    }

    private void setRefresh() {
        mStrategyRecyclerViewAdapter.clearData();
    }

    private void getData(final StrategyListResponse strategyListResponse) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mStrategyRecyclerViewAdapter.addAllData(setList(strategyListResponse));
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                    }
                });

            }
        }, 1000);

    }

    private List<StrategyRecyclerViewItemBean> setList(StrategyListResponse strategyListResponse) {
        List<StrategyRecyclerViewItemBean> dataList = new ArrayList<>();
        if(null==strategyListResponse||null==strategyListResponse.getNewsList()||0==strategyListResponse.getNewsList().size()){
            return dataList;
        }

        currentStartCount = strategyListResponse.getCount()+currentStartCount;

        List<StrategyListResponse.Strategy> strategyList = strategyListResponse.getNewsList();
        for(StrategyListResponse.Strategy strategy:strategyList){

            StrategyRecyclerViewItemBean strategyRecyclerViewItemBean=new StrategyRecyclerViewItemBean();
            strategyRecyclerViewItemBean.setStrategyId(strategy.getId());
            strategyRecyclerViewItemBean.setTitle(strategy.getTitle());
            strategyRecyclerViewItemBean.setCreateTime(TimeUtils.time2TextTime(strategy.getCreattime()));
            strategyRecyclerViewItemBean.setCreateUser(strategy.getCreatUser());
            strategyRecyclerViewItemBean.setImageView("image");
            dataList.add(strategyRecyclerViewItemBean);
        }

        return dataList;

    }

    @Override
    public void fetchStrategyListSuccess(StrategyListResponse strategyListResponse) {
        getData(strategyListResponse);
    }

    @Override
    public void fetchStrategyListFails(int code, String msg) {

    }

    @Override
    public void fetchStrategyListComplete() {
        getData(new StrategyListResponse());
    }
}
