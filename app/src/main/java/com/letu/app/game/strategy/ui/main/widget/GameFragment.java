package com.letu.app.game.strategy.ui.main.widget;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.letu.app.baselib.base.BaseFragment;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.common.pullloadmoreview.PullLoadMoreRecyclerView;
import com.letu.app.game.strategy.ui.game.widget.GameDetailActivity;
import com.letu.app.game.strategy.ui.main.adapter.GameRecyclerViewAdapter;
import com.letu.app.game.strategy.ui.main.adapter.StrategyRecyclerViewAdapter;
import com.letu.app.game.strategy.ui.main.bean.GameListItemResponse;
import com.letu.app.game.strategy.ui.main.bean.GameListResponse;
import com.letu.app.game.strategy.ui.main.bean.GameRecyclerViewItemBean;
import com.letu.app.game.strategy.ui.main.contract.GameContract;
import com.letu.app.game.strategy.ui.main.presenter.GamePresenter;
import com.letu.app.game.strategy.ui.other.bean.BannerListItemResponse;
import com.letu.app.game.strategy.utils.LeTuUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class GameFragment extends BaseFragment<GamePresenter> implements GameContract.View {
    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    @BindView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    private RecyclerView mRecyclerView;
    private GameRecyclerViewAdapter mGameRecyclerViewAdapter;

    private int currentStartCount=0;

    private List<BannerListItemResponse> networkImages=new ArrayList<>();

    public static GameFragment newInstance(String name) {

        Bundle args = new Bundle();
        args.putString("name", name);
        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void daggerInit() {
        DaggerAppBaseComponent.builder()
                .appComponent(getAppComponent())
                .build()
                .inject(GameFragment.this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_game;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attachView(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.get("name").toString();
//            fragmentTestTv.setText(name+", hello");
        }
        mPresenter.fecthGameList(Constant.DATA_COUNT_DEFAULT_START,Constant.DATA_COUNT_MAX);
        mPresenter.fetchBannerList();

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
                        gotoGameDetailPage(networkImages.get(position).getHref());
                    }
                });

        mRecyclerView = mPullLoadMoreRecyclerView.getRecyclerView();
        //代码设置scrollbar无效？未解决！
        mRecyclerView.setVerticalScrollBarEnabled(true);
        //设置下拉刷新是否可见
        //mPullLoadMoreRecyclerView.setRefreshing(true);
        //设置是否可以下拉刷新
        //mPullLoadMoreRecyclerView.setPullRefreshEnable(true);
//        设置是否可以上拉刷新
//        mPullLoadMoreRecyclerView.setPushRefreshEnable(true);
        //显示下拉刷新
        mPullLoadMoreRecyclerView.setRefreshing(true);
        //设置上拉刷新文字
        mPullLoadMoreRecyclerView.setFooterViewText("loading");
        //设置上拉刷新文字颜色
        //mPullLoadMoreRecyclerView.setFooterViewTextColor(R.color.white);
        //设置加载更多背景色
        //mPullLoadMoreRecyclerView.setFooterViewBackgroundColor(R.color.colorBackground);
        mPullLoadMoreRecyclerView.setLinearLayout();

        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                setRefresh();
                mPresenter.fecthGameList(Constant.DATA_COUNT_DEFAULT_START,Constant.DATA_COUNT_MAX);
            }

            @Override
            public void onLoadMore() {
                mPresenter.fecthGameList(currentStartCount,currentStartCount+Constant.DATA_COUNT_MAX);
            }
        });
        mGameRecyclerViewAdapter = new GameRecyclerViewAdapter(getActivity());
        mGameRecyclerViewAdapter.setOnItemClickListener(new GameRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position,String gameId) {
                gotoGameDetailPage(gameId);
            }
        });
        mPullLoadMoreRecyclerView.setAdapter(mGameRecyclerViewAdapter);


    }

    @Override
    public void fecthGameListSuccess(GameListResponse gameListResponse) {

        getData(gameListResponse);
    }

    @Override
    public void fecthGameListFails(int code, String msg) {

    }

    @Override
    public void fecthGameListError() {
        getData(null);
    }

    @Override
    public void fetchBannerListSuccess(List<BannerListItemResponse> bannerList) {
        networkImages.addAll(bannerList);

        convenientBanner.notifyDataSetChanged();
    }

    @Override
    public void fetchBannerListFails(int code, String msg) {

    }


    public class NetworkImageHolderView extends Holder<BannerListItemResponse> {
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
        mGameRecyclerViewAdapter.clearData();
        currentStartCount = 0;
    }

    private void getData(final GameListResponse gameListResponse) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mGameRecyclerViewAdapter.addAllData(setList(gameListResponse));
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                    }
                });

            }
        });

    }

    private List<GameRecyclerViewItemBean> setList(GameListResponse gameListResponse) {
        List<GameRecyclerViewItemBean> dataList = new ArrayList<>();
        if(null==gameListResponse||null==gameListResponse.getGames()||gameListResponse.getGames().isEmpty()){
            return dataList;
        }
        currentStartCount = gameListResponse.getGames().size()+currentStartCount;

        for(GameListResponse.Game game:gameListResponse.getGames()){

            GameRecyclerViewItemBean gameRecyclerViewItemBean=new GameRecyclerViewItemBean();
            gameRecyclerViewItemBean.setGameId(game.getGameId());
            gameRecyclerViewItemBean.setTitle(game.getName());
            gameRecyclerViewItemBean.setSubTitle(null==game.getDescription()?"":game.getDescription().trim());
            gameRecyclerViewItemBean.setImageView(game.getIcon());
            dataList.add(gameRecyclerViewItemBean);
        }

        return dataList;

    }

    private void gotoGameDetailPage(String gameId){
        Intent intent = new Intent(mContext,GameDetailActivity.class);
        intent.putExtra(Constant.KEY_INTENT_GAME_ID,gameId);
        startActivity(intent);
    }
}
