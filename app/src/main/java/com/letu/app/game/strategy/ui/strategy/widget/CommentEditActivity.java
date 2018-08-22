package com.letu.app.game.strategy.ui.strategy.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.letu.app.baselib.base.BaseActivity;
import com.letu.app.baselib.utils.ToastUtil;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.strategy.bean.CommentResponse;
import com.letu.app.game.strategy.ui.strategy.contract.CommentEditContract;
import com.letu.app.game.strategy.ui.strategy.presenter.CommentEditPresenter;
import com.letu.app.game.strategy.utils.LeTuUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentEditActivity extends BaseActivity<CommentEditPresenter> implements CommentEditContract.View {

    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.comment_text)
    EditText commentText;

    private String strategyId;

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
        setContentView(R.layout.activity_comment_edit);
        mPresenter.attachView(this);

        Intent intent=getIntent();
        strategyId = intent.getStringExtra(Constant.KEY_INTENT_STRATEGY_ID);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.submit)
    public void submitComment() {
        if(LeTuUtils.isFastClick()){
            return;
        }

        if(TextUtils.isEmpty(commentText.getText().toString())){
            ToastUtil.toastShort("评论内容不能为空！");
            return;
        }
        mPresenter.addComment(strategyId,commentText.getText().toString());

    }

    @Override
    public void addCommentSuccess(CommentResponse commentResponse) {
        ToastUtil.toastShort("评论成功！");
        setResult(Constant.CODE_RESPONSE_EDIT_COMMENT);
        finish();
    }

    @Override
    public void addCommentFails(int code, String msg) {
        ToastUtil.toastShort(msg);
    }
}
