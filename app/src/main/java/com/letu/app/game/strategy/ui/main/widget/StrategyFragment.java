package com.letu.app.game.strategy.ui.main.widget;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.letu.app.baselib.base.BaseFragment;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.common.pullloadmoreview.PullLoadMoreRecyclerView;
import com.letu.app.game.strategy.ui.main.adapter.StrategyRecyclerViewAdapter;
import com.letu.app.game.strategy.ui.main.bean.StrategyListResponse;
import com.letu.app.game.strategy.ui.main.bean.StrategyRecyclerViewItemBean;
import com.letu.app.game.strategy.ui.main.contract.StrategyContract;
import com.letu.app.game.strategy.ui.main.presenter.StrategyPresenter;
import com.letu.app.game.strategy.ui.other.bean.BannerListItemResponse;
import com.letu.app.game.strategy.ui.strategy.widget.StrategyDetailActivity;
import com.letu.app.game.strategy.ui.strategy.widget.StrategyDetailNewActivity;
import com.letu.app.game.strategy.utils.LeTuUtils;
import com.letu.app.game.strategy.utils.TimeUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ${user} on 2018/7/5
 */
public class StrategyFragment extends BaseFragment<StrategyPresenter> implements StrategyContract.View {
    @BindView(R.id.fragment_test_tv)
    TextView fragmentTestTv;
    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    @BindView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    private int currentStartCount=0;
    private RecyclerView mRecyclerView;
    private StrategyRecyclerViewAdapter mStrategyRecyclerViewAdapter;
    private List<BannerListItemResponse> networkImages=new ArrayList<>();

    public static StrategyFragment newInstance(String name) {

        Bundle args = new Bundle();
        args.putString("name", name);
        StrategyFragment fragment = new StrategyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void daggerInit() {
        DaggerAppBaseComponent.builder()
                .appComponent(getAppComponent())
                .build()
                .inject(StrategyFragment.this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_strategy_layout;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attachView(this);
        mPresenter.fetchStrategyList(0,20);
        mPresenter.fetchBannerList();

        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.get("name").toString();
            fragmentTestTv.setText(name);
        }
//        networkImages= Arrays.asList(images);

        //开始自动翻页
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public NetworkImageHolderView createHolder(View itemView) {
                return new NetworkImageHolderView(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_networkimage;
            }

        },networkImages)
                //设置指示器是否可见
                .setPointViewVisible(true)
                //设置自动切换（同时设置了切换时间间隔）
                .startTurning(3000)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向（左、中、右）
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                //设置点击监听事件
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if(LeTuUtils.isFastClick()){
                            return;
                        }
                        if(null==networkImages||networkImages.isEmpty()){
                            return;
                        }

                        gotoStrategyDetailPage(networkImages.get(position).getHref());
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
                mPresenter.fetchStrategyList(Constant.DATA_COUNT_DEFAULT_START,Constant.DATA_COUNT_MAX);
            }

            @Override
            public void onLoadMore() {
                mPresenter.fetchStrategyList(currentStartCount,currentStartCount+Constant.DATA_COUNT_MAX);
            }
        });
        mStrategyRecyclerViewAdapter = new StrategyRecyclerViewAdapter(getActivity());
        mStrategyRecyclerViewAdapter.setOnItemClickListener(new StrategyRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position,String strategyId) {
                gotoStrategyDetailPage(strategyId);
            }
        });
        mPullLoadMoreRecyclerView.setAdapter(mStrategyRecyclerViewAdapter);

    }

    @Override
    public void fetchStrategyListSuccess(StrategyListResponse strategyListResponse) {
        getData(strategyListResponse);
    }

    @Override
    public void fetchStrategyListFails(int code, String msg) {

    }

    @Override
    public void fetchStrategyListError() {

//        mPullLoadMoreRecyclerView.setRefreshing(false);
//        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        getData(new StrategyListResponse());
    }

    @Override
    public void fetchBannerListSuccess(List<BannerListItemResponse> bannerList) {
        if(null==bannerList||bannerList.isEmpty()){
            return;
        }

//        networkImages.clear();
//
//        for(BannerListItemResponse bannerListItemResponse:bannerList){
//
//            networkImages.add(bannerListItemResponse.getImage());
//        }

        networkImages.addAll(bannerList);
        convenientBanner.notifyDataSetChanged();
    }

    @Override
    public void fetchBannerListFails(int code, String msg) {

    }


    public class NetworkImageHolderView extends Holder<BannerListItemResponse>{
        private ImageView imageView;
        public NetworkImageHolderView(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            imageView=itemView.findViewById(R.id.ivPost);
        }

        @Override
        public void updateUI(BannerListItemResponse data) {
            Picasso.get()
                    .load(data.getImage())
                    .fit()
                    .tag(mContext)
                    .into(imageView);
        }
    }


    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //        //开始自动翻页
        convenientBanner.startTurning();
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //        //停止翻页
        convenientBanner.stopTurning();
    }


    private void setRefresh() {
        mStrategyRecyclerViewAdapter.clearData();
        currentStartCount = 0;
    }

    private void getData(final StrategyListResponse strategyListResponse) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mStrategyRecyclerViewAdapter.addAllData(setList(strategyListResponse));
                            mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                        }catch (Exception e){

                        }
                    }
                });

            }
        });

    }

    private List<StrategyRecyclerViewItemBean> setList(StrategyListResponse strategyListResponse) {
        List<StrategyRecyclerViewItemBean> dataList = new ArrayList<>();
        if(null==strategyListResponse||null==strategyListResponse.getNewsList()||0==strategyListResponse.getNewsList().size()){
            return dataList;
        }

        currentStartCount = strategyListResponse.getNewsList().size()+currentStartCount;

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

    private void gotoStrategyDetailPage(String strategyId){
        Intent intent=new Intent(mContext,StrategyDetailNewActivity.class);
        intent.putExtra(Constant.KEY_INTENT_STRATEGY_ID,strategyId);
        startActivity(intent);
    }

}

