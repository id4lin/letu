package com.letu.app.game.strategy.ui.strategy.widget;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.letu.app.baselib.base.BaseActivity;
import com.letu.app.baselib.utils.ToastUtil;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.common.view.BadgeView;
import com.letu.app.game.strategy.ui.login.widget.LoginActivity;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;
import com.letu.app.game.strategy.ui.strategy.adapter.CommentListAdapter;
import com.letu.app.game.strategy.ui.strategy.bean.CommentListItemBean;
import com.letu.app.game.strategy.ui.strategy.bean.CommentListItemResponse;
import com.letu.app.game.strategy.ui.strategy.bean.CommentListResponse;
import com.letu.app.game.strategy.ui.strategy.bean.StrategyDetailResponse;
import com.letu.app.game.strategy.ui.strategy.contract.StrategyDetailContract;
import com.letu.app.game.strategy.ui.strategy.presenter.StrategyDetailPresenter;
import com.letu.app.game.strategy.utils.DensityUtils;
import com.letu.app.game.strategy.utils.LeTuUtils;

import net.nightwhistler.htmlspanner.HtmlSpanner;
import net.nightwhistler.htmlspanner.LinkMovementMethodExt;
import net.nightwhistler.htmlspanner.MyImageSpan;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class StrategyDetailActivity extends BaseActivity<StrategyDetailPresenter> implements StrategyDetailContract.View {

    @BindView(R.id.strategy_contents)
    TextView strategyContents;
    @BindView(R.id.strategy_like)
    TextView strategyLike;
    @BindView(R.id.play)
    TextView play;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.comment_text)
    TextView commentText;
    @BindView(R.id.comment_list_title)
    TextView commentListTitle;
    @BindView(R.id.strategy_collect)
    ImageView strategyCollect;
    @BindView(R.id.comment_text_list)
    ImageView commentTextList;
    @BindView(R.id.comment_list_recyclerView)
    RecyclerView commentListRecyclerView;
    @BindView(R.id.strategy_contents_scroll_view)
    ScrollView strategyContentsScrollView;

    private int width;
    private HtmlSpanner htmlSpanner;
    private ArrayList<String> imglist;
    private CommentListAdapter mCommentListAdapter;
    private int mCount = 1;
    //    private RecyclerView mRecyclerView;

    int lastVisibleItem;
    int page = 0;
    boolean isLoading = false;//用来控制进入getdata()的次数
    int totlePage = 5;//模拟请求的一共的页数
    LinearLayoutManager linearLayoutManager;

    private String strategyId;
    private String gameId;
    private String gameName;
    private Spannable mSpannable = null;

    private boolean isLike;
    private boolean isCollect;
    private int likeCount;

    private int currentStartCount=0;
    private BadgeView mBadgeView;
    private boolean isTop;

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
        setContentView(R.layout.activity_strategy_detail);

        mPresenter.attachView(this);

        Intent intent = getIntent();
        strategyId = intent.getStringExtra(Constant.KEY_INTENT_STRATEGY_ID);

        mPresenter.fetchStrategyDetail(strategyId);

        mPresenter.fetctCommentList(strategyId,Constant.DATA_COUNT_DEFAULT_START,10);
//        mPresenter.fetctCommentList(strategyId,Constant.DATA_COUNT_DEFAULT_START,Constant.DATA_COUNT_MAX);

        play.setVisibility(View.INVISIBLE);

        mBadgeView = new BadgeView(this, commentTextList);
        mBadgeView.setText("0");
        mBadgeView.setTextSize(10);
        mBadgeView.show();


        //        width = getResources().getDisplayMetrics().widthPixels;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth=DensityUtils.getDisplayWidth(mContext)-DensityUtils.dp2px(mContext,40);

        imglist = new ArrayList<>();
        htmlSpanner = new HtmlSpanner(this, screenWidth, handler);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });

        strategyContents.setText(mSpannable);
        strategyContents.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
        strategyContents.setMovementMethod(LinkMovementMethodExt.getInstance(handler, ImageSpan.class));


        linearLayoutManager = new LinearLayoutManager(this);

        commentListRecyclerView.setLayoutManager(linearLayoutManager);

        mCommentListAdapter = new CommentListAdapter(getActivity(), mDataList,commentListRecyclerView);
        mCommentListAdapter.setOnItemClickListener(new CommentListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //                Toast.makeText(mContext, "item "+position, Toast.LENGTH_LONG).show();
            }
        });
        commentListRecyclerView.setAdapter(mCommentListAdapter);

        mCommentListAdapter.setOnLoadMoreClickListener(new CommentListAdapter.OnLoadMoreClickListener() {
            @Override
            public void onLoadMore() {
//                mDataList.clear();
                mPresenter.fetctCommentList(strategyId,currentStartCount,currentStartCount+10);
                mCommentListAdapter.changeState(CommentListAdapter.LOADING_MORE);
//                mPresenter.fetctCommentList(strategyId,currentStartCount,currentStartCount+Constant.DATA_COUNT_MAX);

            }
        });
    }


    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://获取图片路径列表
                    String url = (String) msg.obj;
                    Log.e("jj", "url>>" + url);
                    imglist.add(url);
                    break;
                case 2://图片点击事件
                    int position = 0;
                    MyImageSpan span = (MyImageSpan) msg.obj;
                    for (int i = 0; i < imglist.size(); i++) {
                        if (span.getUrl().equals(imglist.get(i))) {
                            position = i;
                            break;
                        }
                    }
                    Log.e("jj", "position>>" + position);
                    Intent intent = new Intent(mContext, ImgPreviewActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("position", position);
                    b.putStringArrayList("imglist", imglist);
                    intent.putExtra("b", b);
                    startActivity(intent);
                    break;
            }
        }
    };

    @OnClick(R.id.play)
    void go2PlayPage() {
        if (LeTuUtils.isFastClick()) {
            return;
        }
        if (!mPresenter.isLogin()) {
            gotoLogin();
            return;
        }
        // 我要玩
        mPresenter.wantPlayGame(gameId);


    }


    @OnClick(R.id.strategy_collect)
    void strategyCollect() {
        if (LeTuUtils.isFastClick()) {
            return;
        }
        if (!mPresenter.isLogin()) {
            gotoLogin();
            return;
        }
        if (isCollect) {
            mPresenter.removeCollect(strategyId);
            return;
        }

        mPresenter.collect(strategyId);

    }

    @OnClick(R.id.strategy_like)
    void strategyLike() {
        if (LeTuUtils.isFastClick()) {
            return;
        }

        if (!mPresenter.isLogin()) {
            gotoLogin();
            return;
        }

        if (isLike) {
            ToastUtil.toastShort("已点赞");
            return;
        }

        mPresenter.like(strategyId);
    }

    @OnClick(R.id.comment_text)
    void go2CommentEditPage() {
        if (LeTuUtils.isFastClick()) {
            return;
        }
        if (!mPresenter.isLogin()) {
            gotoLogin();
            return;
        }

        Intent intent = new Intent(mContext, CommentEditActivity.class);
        intent.putExtra(Constant.KEY_INTENT_STRATEGY_ID, strategyId);
        startActivityForResult(intent, Constant.CODE_REQUEST_EDIT_COMMENT);
    }

    @OnClick(R.id.comment_text_list)
    void scrollToCommentArea(){
        if(isTop){
            strategyContentsScrollView.smoothScrollTo(0,commentListTitle.getTop());
            isTop=false;
        }else{

            strategyContentsScrollView.fullScroll(ScrollView.FOCUS_UP);
            isTop=true;
        }
    }

    private void setRefresh() {
        mCommentListAdapter.clearData();

    }

    /**
     * 图片上色
     */
    public void setDrawableColor(int color) {
        Drawable[] drawables = strategyLike.getCompoundDrawables();
        for (int i = 0, size = drawables.length; i < size; i++) {
            if (null != drawables[i]) {
                drawables[i].setBounds(0, 0, drawables[i].getIntrinsicWidth(),
                        drawables[i].getIntrinsicHeight());

                drawables[i].setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_IN);
                //                drawables[i].setColorFilter(new PorterDuffColorFilter(color,
                //                        PorterDuff.Mode.SRC_IN));
            }
        }
    }

    private List<CommentListItemBean> mDataList = new ArrayList<>();

    private void getData(CommentListResponse commentListResponse) {
        List<CommentListItemBean> dataList = new ArrayList<>();
        if (null == commentListResponse ||null==commentListResponse.getComments()|| commentListResponse.getComments().isEmpty()) {
            mCommentListAdapter.changeState(CommentListAdapter.NO_MORE);
            return;
        }
        mBadgeView.setText(commentListResponse.getCount()+"");
        currentStartCount = commentListResponse.getComments().size()+currentStartCount;
        for (CommentListItemResponse commentListItemResponse : commentListResponse.getComments()) {
            CommentListItemBean commentListItemBean = new CommentListItemBean();
            commentListItemBean.setName(commentListItemResponse.getNickName());
            commentListItemBean.setCreateTime(commentListItemResponse.getCreatetime());
            commentListItemBean.setComment(commentListItemResponse.getContent());
            commentListItemBean.setIcon(commentListItemResponse.getAvatarUrl());
            dataList.add(commentListItemBean);
        }


        mDataList.addAll(dataList);
        mCommentListAdapter.changeState(CommentListAdapter.PULL_LOAD_MORE);

    }

    @Override
    public void fetchStrategyDetailSuccess(final StrategyDetailResponse strategyDetailResponse) {
        if (null == strategyDetailResponse || TextUtils.isEmpty(strategyDetailResponse.getContent())) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                mSpannable = htmlSpanner.fromHtml(strategyDetailResponse.getContent());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        strategyContents.setText(mSpannable);
                    }
                });
            }
        }).start();
        strategyLike.setText(strategyDetailResponse.getScore() + "人喜欢");
        likeCount = strategyDetailResponse.getScore();
        setStrategyLikeStyle(strategyDetailResponse.getIsLiked());
        setStrategyCollectStyle(strategyDetailResponse.getIsCollected());

        gameId = strategyDetailResponse.getGameId();
        gameName = strategyDetailResponse.getGame().getGameName();

        if("0".equals(gameId)){
            play.setVisibility(View.INVISIBLE);
        }else{
            play.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void fetchStrategyDetailFails(int code, String msg) {

    }

    @Override
    public void fetctCommentListSuccess(CommentListResponse commentListResponse) {
        getData(commentListResponse);
    }

    @Override
    public void fetctCommentListFails(int code, String msg) {
        //        ToastUtil.toastShort(msg);
    }

    @Override
    public void likeSuccess(OtherResponse otherResponse) {
        ToastUtil.toastShort("已点赞");
        //        strategyLike.setBackgroundResource(R.drawable.corners_bg_red_like_light);
        //        strategyLike.setTextColor(getResources().getColor(R.color.white));
        //        strategyLike.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_quanzi_zan), null, null, null);
        //        setDrawableColor(R.color.white);

        strategyLike.setText((likeCount + 1) + "人喜欢");
        setStrategyLikeStyle(Constant.STATUS_LIKE_YES);

    }

    @Override
    public void likeFails(int code, String msg) {
        ToastUtil.toastShort(msg);
        if (Constant.STATUS_HTTP_LIKE_EXIST == code) {
            //            strategyLike.setText((likeCount+1) + "人喜欢");
            setStrategyLikeStyle(Constant.STATUS_LIKE_YES);
        } else {
            setStrategyLikeStyle(Constant.STATUS_LIKE_NO);
        }

    }

    @Override
    public void collectSuccess(OtherResponse otherResponse) {
        ToastUtil.toastShort("已收藏");
        setStrategyCollectStyle(Constant.STATUS_LIKE_YES);
    }

    @Override
    public void collectFails(int code, String msg) {
        ToastUtil.toastShort(msg);
        if (Constant.STATUS_HTTP_COLLECT_EXIST == code) {
            setStrategyCollectStyle(Constant.STATUS_COLLECT_YES);
        } else {
            setStrategyCollectStyle(Constant.STATUS_COLLECT_NO);
        }
    }

    @Override
    public void removeCollectSuccess(OtherResponse otherResponse) {
        ToastUtil.toastShort("收藏已取消");
        setStrategyCollectStyle(Constant.STATUS_COLLECT_NO);
    }

    @Override
    public void removeCollectFails(int code, String msg) {
        ToastUtil.toastShort(msg);
        if (Constant.STATUS_HTTP_COLLECT_NO_EXIST == code) {
            setStrategyCollectStyle(Constant.STATUS_COLLECT_NO);
        } else {
            setStrategyCollectStyle(Constant.STATUS_COLLECT_YES);
        }
    }

    @Override
    public void wantPlayGameSuccess(OtherResponse otherResponse) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("您已成功预约 "+gameName+"。\n请注意查收短信！");
        builder.setCancelable(false);
        builder.setPositiveButton("知道了！", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public void wantPlayGameFails(int code, String msg) {
        ToastUtil.toastShort(msg);
    }

    private void gotoLogin() {
        startActivity(LoginActivity.class);
    }

    private void setStrategyLikeStyle(int isLiked) {
        if (Constant.STATUS_LIKE_NO == isLiked) {
            strategyLike.setBackgroundResource(R.drawable.corners_bg_red_like);
            strategyLike.setTextColor(getResources().getColor(R.color.orange));
            strategyLike.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_quanzi_zan_light), null, null, null);
            setDrawableColor(R.color.orange);
            isLike = false;
        } else {
            strategyLike.setBackgroundResource(R.drawable.corners_bg_red_like_light);
            strategyLike.setTextColor(getResources().getColor(R.color.white));
            strategyLike.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_quanzi_zan), null, null, null);
            setDrawableColor(R.color.white);
            isLike = true;
        }
    }

    private void setStrategyCollectStyle(int isCollected) {
        if (Constant.STATUS_COLLECT_NO == isCollected) {
            strategyCollect.setImageResource(R.drawable.ic_collect_border_white);
            isCollect = false;
        } else {
            strategyCollect.setImageResource(R.drawable.ic_collect_yellow);
            isCollect = true;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Constant.CODE_REQUEST_EDIT_COMMENT == requestCode && Constant.CODE_RESPONSE_EDIT_COMMENT == resultCode) {
            mDataList.clear();
            currentStartCount=0;
            mPresenter.fetctCommentList(strategyId,Constant.DATA_COUNT_DEFAULT_START,Constant.DATA_COUNT_MAX);
        }

    }
}
