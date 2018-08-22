package com.letu.app.game.strategy.ui.game.widget;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.letu.app.baselib.base.BaseActivity;
import com.letu.app.baselib.utils.ToastUtil;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.game.bean.GameDetailResponse;
import com.letu.app.game.strategy.ui.game.contract.GameDetailContract;
import com.letu.app.game.strategy.ui.game.presenter.GameDetailPresenter;
import com.letu.app.game.strategy.ui.login.widget.LoginActivity;
import com.letu.app.game.strategy.ui.other.bean.OtherResponse;
import com.letu.app.game.strategy.ui.strategy.widget.ImgPreviewActivity;
import com.letu.app.game.strategy.ui.strategy.widget.StrategyEditActivity;
import com.letu.app.game.strategy.ui.strategy.widget.StrategyListActivity;
import com.letu.app.game.strategy.utils.LeTuUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GameDetailActivity extends BaseActivity<GameDetailPresenter> implements GameDetailContract.View {
    @BindView(R.id.iv_content)
    ImageView ivContent;
    @BindView(R.id.game_icon)
    ImageView gameIcon;
    @BindView(R.id.game_name)
    TextView gameName;
    @BindView(R.id.game_create_time)
    TextView gameCreateTime;
    @BindView(R.id.game_cp)
    TextView gameCp;
    @BindView(R.id.game_play)
    TextView gamePlay;
    @BindView(R.id.play)
    TextView play;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.game_brief_introduction)
    TextView gameBriefIntroduction;
    @BindView(R.id.game_query_strategy)
    Button gameQueryStrategy;
    @BindView(R.id.game_write_strategy)
    Button gameWriteStrategy;
    @BindView(R.id.game_detail_CollapsingToolbarLayout)
    CollapsingToolbarLayout gameDetailCollapsingToolbarLayout;
    @BindView(R.id.game_screen_shot_1)
    ImageView gameScreenShot1;
    @BindView(R.id.game_screen_shot_2)
    ImageView gameScreenShot2;
    @BindView(R.id.game_screen_shot_3)
    ImageView gameScreenShot3;
    @BindView(R.id.game_screen_shot_4)
    ImageView gameScreenShot4;
    private String gameId;

    private ArrayList<String> mImageUrlList = new ArrayList<>();

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
        setContentView(R.layout.activity_game_detail);
        Intent intent = getIntent();
        gameId = intent.getStringExtra(Constant.KEY_INTENT_GAME_ID);

        mPresenter.attachView(this);

        mPresenter.fetchGameDetail(gameId);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void fetchGameDetailSuccess(GameDetailResponse gameDetailResponse) {
        try {
            gameDetailCollapsingToolbarLayout.setTitle(gameDetailResponse.getGameInfo().getGameName());
            gameName.setText(gameDetailResponse.getGameInfo().getGameName());
            gameCp.setText(gameDetailResponse.getGameInfo().getCompany());
            gameCreateTime.setText(gameDetailResponse.getGameInfo().getOpenTime());

            List<GameDetailResponse.Pic> picList = gameDetailResponse.getPic();
            mImageUrlList.clear();
            for (GameDetailResponse.Pic pic : picList) {
                loadImage(pic.getTypeId(), pic.getUrl());
            }
            gameBriefIntroduction.setText(gameDetailResponse.getGameInfo().getDescription());
        } catch (Exception e) {

        }

    }

    private void loadImage(String type, String url) {
        ImageView imageView1 = null;
        ImageView imageView = null;
        if ("IconPic".equals(type)) {
            imageView = gameIcon;
        } else if ("ScreenShot1".equals(type)) {
            imageView1 = ivContent;
            imageView = gameScreenShot1;

        } else if ("ScreenShot2".equals(type)) {
            imageView = gameScreenShot2;

        } else if ("ScreenShot3".equals(type)) {
            imageView = gameScreenShot3;

        } else if ("ScreenShot4".equals(type)) {
            imageView = gameScreenShot4;
        }

        if(!"IconPic".equals(type)){
            mImageUrlList.add(url);
        }

        if (null == imageView) {
            return;
        }

        if (null != imageView1) {
            Picasso.get()
                    .load(url)
                    //                    .error(R.drawable.ycloud_icon_fullscreen)
                    .fit()
                    //                    .placeholder(R.drawable.ycloud_icon_fullscreen)
                    .tag(mContext)
                    .into(imageView1);
        }

        Picasso.get()
                .load(url)
                //                .error(R.drawable.ycloud_icon_fullscreen)
                .fit()
                //                .placeholder(R.drawable.ycloud_icon_fullscreen)
                .tag(mContext)
                .into(imageView);
    }

    @Override
    public void fetchGameDetailFails(int code, String msg) {

    }

    @Override
    public void wantPlayGameSuccess(OtherResponse otherResponse) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("您已成功预约 " + gameName.getText().toString() + "。\n请注意查收短信！");
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

    @OnClick({R.id.play,R.id.game_play})
    public void wantPlayGame() {
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

    @OnClick(R.id.game_query_strategy)
    public void queryGameStrategy() {
        if (LeTuUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(mContext, StrategyListActivity.class);
        intent.putExtra(Constant.KEY_INTENT_GAME_ID, gameId);
        intent.putExtra(Constant.KEY_INTENT_GAME_NAME, gameName.getText().toString());
        startActivity(intent);
    }

    @OnClick(R.id.game_write_strategy)
    public void writeGameStrategy() {
        if (LeTuUtils.isFastClick()) {
            return;
        }
        if (!mPresenter.isLogin()) {
            gotoLogin();
            return;
        }

        Intent intent = new Intent(mContext, StrategyEditActivity.class);
        intent.putExtra(Constant.KEY_INTENT_GAME_ID, gameId);
        startActivity(intent);

    }

    private void gotoLogin() {
        startActivity(LoginActivity.class);
    }

    @OnClick({R.id.game_screen_shot_1, R.id.game_screen_shot_2, R.id.game_screen_shot_3, R.id.game_screen_shot_4})
    public void onViewClicked(View view) {
        int index=0;
        switch (view.getId()) {
            case R.id.game_screen_shot_1:
                index=0;
                break;
            case R.id.game_screen_shot_2:
                index=1;
                break;
            case R.id.game_screen_shot_3:
                index=2;
                break;
            case R.id.game_screen_shot_4:
                index=3;
                break;
        }

        Intent intent = new Intent();
        intent.putStringArrayListExtra(Constant.KEY_INTENT_IMG_LIST, mImageUrlList);
        intent.putExtra(Constant.KEY_INTENT_POSITION, index);
        intent.setClass(mContext, ImgPreviewActivity.class);
        mContext.startActivity(intent);
    }

}
