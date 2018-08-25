package com.letu.app.game.strategy.ui.me.widget;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.letu.app.baselib.base.BaseActivity;
import com.letu.app.baselib.utils.ToastUtil;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.common.pullloadmoreview.PullLoadMoreRecyclerView;
import com.letu.app.game.strategy.ui.game.widget.GameDetailActivity;
import com.letu.app.game.strategy.ui.main.adapter.StrategyRecyclerViewAdapter;
import com.letu.app.game.strategy.ui.main.bean.StrategyListResponse;
import com.letu.app.game.strategy.ui.main.bean.StrategyRecyclerViewItemBean;
import com.letu.app.game.strategy.ui.me.adapter.GameGridRecyclerViewAdapter;
import com.letu.app.game.strategy.ui.me.bean.GameGridItemResponse;
import com.letu.app.game.strategy.ui.me.bean.GameGridRecyclerViewItemBean;
import com.letu.app.game.strategy.ui.me.contract.GameListContract;
import com.letu.app.game.strategy.ui.me.presenter.GameListPresenter;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;
import com.letu.app.game.strategy.ui.strategy.widget.StrategyDetailActivity;
import com.letu.app.game.strategy.utils.LeTuUtils;
import com.letu.app.game.strategy.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameListActivity extends BaseActivity<GameListPresenter> implements GameListContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    @BindView(R.id.game_list_edit_action_tv)
    TextView editActionTv;

    private RecyclerView mRecyclerView;
    private GameGridRecyclerViewAdapter mGameGridRecyclerViewAdapter;
    private GridLayoutManager mLayoutManager;
    private int currentStartCount = 0;

    private boolean isEditState = false;

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
        setContentView(R.layout.activity_game_list);
        mPresenter.attachView(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mPresenter.fetchMyGameList();

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

        //        mPullLoadMoreRecyclerView.setLinearLayout();
        mPullLoadMoreRecyclerView.setGridLayout(1);
        mPullLoadMoreRecyclerView.setDividerPadding(5);

        //        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
        //            @Override
        //            public void onRefresh() {
        //                setRefresh();
        ////                mPresenter.fetchStrategyList(0,20,gameId);
        //            }
        //
        //            @Override
        //            public void onLoadMore() {
        ////                mPresenter.fetchStrategyList(currentStartCount,currentStartCount+20,gameId);
        //            }
        //        });
        mGameGridRecyclerViewAdapter = new GameGridRecyclerViewAdapter(getActivity());
        mGameGridRecyclerViewAdapter.setOnItemClickListener(new GameGridRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position, String gameId) {
                Intent intent = new Intent(mContext, GameDetailActivity.class);
                intent.putExtra(Constant.KEY_INTENT_GAME_ID, gameId);
                startActivity(intent);
            }
        });
        mGameGridRecyclerViewAdapter.setOnItemLongClickListener(new GameGridRecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public void onItemClick(View view, int position, String gameId) {
                if(isEditState){
                    return;
                }
                editActionTv.setText("完成");
                isEditState = true;
                mGameGridRecyclerViewAdapter.setEdited(isEditState);
            }
        });
        mGameGridRecyclerViewAdapter.setOnDeleteItemClickListener(new GameGridRecyclerViewAdapter.OnDeleteItemClickListener() {
            @Override
            public void onDeleteItem(int position, int size, String gameId) {
                ToastUtil.toastShort("已删除");
                if (1 >= size) {
                    mPullLoadMoreRecyclerView.setGridLayout(1);
                }
                mGameGridRecyclerViewAdapter.deleteSuccessCallback();
            }
        });
        mPullLoadMoreRecyclerView.setAdapter(mGameGridRecyclerViewAdapter);
    }

    @OnClick(R.id.game_list_edit_action_tv)
    public void editActionClick() {

        if (isEditState) {
            editActionTv.setText("编辑");
            isEditState = false;
        } else {
            editActionTv.setText("完成");
            isEditState = true;
        }
        mGameGridRecyclerViewAdapter.setEdited(isEditState);

    }


    private void setRefresh() {
        mGameGridRecyclerViewAdapter.clearData();
    }

    private void getData(final List<GameGridItemResponse> gameGridItemListResponse) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (null == gameGridItemListResponse || gameGridItemListResponse.isEmpty()) {
                            mPullLoadMoreRecyclerView.setGridLayout(1);
                        } else {
                            mPullLoadMoreRecyclerView.setGridLayout(3);
                        }
                        mGameGridRecyclerViewAdapter.addAllData(setList(gameGridItemListResponse));
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                    }
                });

            }
        });

    }

    private List<GameGridRecyclerViewItemBean> setList(List<GameGridItemResponse> gameGridItemListResponse) {
        List<GameGridRecyclerViewItemBean> dataList = new ArrayList<>();
        if (null == gameGridItemListResponse || 0 == gameGridItemListResponse.size()) {
            return dataList;
        }

        //        currentStartCount = gameGridItemListResponse.size()+currentStartCount;

        for (GameGridItemResponse gameGridItemResponse : gameGridItemListResponse) {
            GameGridRecyclerViewItemBean gameGridRecyclerViewItemBean = new GameGridRecyclerViewItemBean();
            gameGridRecyclerViewItemBean.setGameId(gameGridItemResponse.getGameid());
            gameGridRecyclerViewItemBean.setGameName(gameGridItemResponse.getGameName());
            gameGridRecyclerViewItemBean.setGameIcon(gameGridItemResponse.getIcon());
            dataList.add(gameGridRecyclerViewItemBean);
        }

        return dataList;

    }


    @Override
    public void fetchMyGameListSuccess(List<GameGridItemResponse> gameGridItemListResponse) {
        getData(gameGridItemListResponse);
    }

    @Override
    public void fetchMyGameListFails(int code, String msg) {

    }

    @Override
    public void fetchMyGameListComplete() {
        //        getData(new ArrayList<GameGridItemResponse>());
    }
}
