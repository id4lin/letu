package com.letu.app.game.strategy.ui.me.widget;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.letu.app.baselib.base.BaseActivity;
import com.letu.app.baselib.utils.ToastUtil;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.me.adapter.PromoterListRecyclerViewAdapter;
import com.letu.app.game.strategy.ui.me.bean.PromoterBean;
import com.letu.app.game.strategy.ui.me.bean.PromoterListRecyclerViewItemBean;
import com.letu.app.game.strategy.ui.me.contract.PromoterDetailContract;
import com.letu.app.game.strategy.ui.me.presenter.PromoterDetailPresenter;
import com.letu.app.game.strategy.ui.other.bean.PromoterIncomeListItemResponse;
import com.letu.app.game.strategy.utils.LeTuUtils;
import com.letu.app.game.strategy.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PromoterDetailActivity extends BaseActivity<PromoterDetailPresenter> implements PromoterDetailContract.View {

    @BindView(R.id.game_name_tv)
    TextView gameNameTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.promoter_code_tv)
    TextView promoterCodeTv;
    @BindView(R.id.promoter_url_copy_tv)
    TextView promoterUrlCopyTv;
    @BindView(R.id.promoter_url_tv)
    TextView promoterUrlTv;
    @BindView(R.id.promoter_list_rv)
    RecyclerView promoterListRv;
    @BindView(R.id.promoter_game_name_tv)
    TextView promoterGameNameTv;
    @BindView(R.id.promoter_pay_num_tv)
    TextView promoterPayNumTv;
    @BindView(R.id.promoter_gainsharing_tv)
    TextView promoterGainsharingTv;
    @BindView(R.id.promoter_balance_tv)
    TextView promoterBalanceTv;
    @BindView(R.id.promoter_regist_num_tv)
    TextView promoterRegistNumTv;
    @BindView(R.id.promoter_divide_info_tv)
    TextView promoterDivideInfoTv;
    private String promoterCode;
    private String androidUrl;
    private String iosUrl;
    private String gameName;
    private String promoterUrl = "暂无<br>";
    private PromoterListRecyclerViewAdapter mPromoterListRecyclerViewAdapter;
    private List<PromoterListRecyclerViewItemBean> dataList = new ArrayList<>();
    private String mStartTime;
    private String mEndTime;
    private String gameId;

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
        setContentView(R.layout.activity_promoter_detail_n);
        mPresenter.attachView(this);
        initData();

        mPresenter.fetchPromoterIncomeList(gameId,mStartTime,mEndTime);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @OnClick(R.id.promoter_url_copy_tv)
    public void onViewClicked(View view) {
        if (LeTuUtils.isFastClick()) {
            return;
        }
        putTextIntoClip();
        ToastUtil.toastShort("已复制");
    }

    private void initData() {
        Intent intent = getIntent();
        PromoterBean promoterBean = (PromoterBean) intent.getSerializableExtra(Constant.KEY_INTENT_PROMOTER_BEAN);
        if (null == promoterBean) {
            return;
        }

        mStartTime=intent.getStringExtra(Constant.KEY_INTENT_START_TIME);
        mEndTime=intent.getStringExtra(Constant.KEY_INTENT_END_TIME);

        gameId=promoterBean.getGameId();
        promoterCode = promoterBean.getPromoterCode();
        gameName = promoterBean.getGameName();
        //        androidUrl = promoterBean.getApkUrl();
        //        iosUrl = promoterBean.getIpaUrl();
        //        promoterUrl
        List<PromoterBean.GameUrlBean> gameUrlBeanList = promoterBean.getGameUrlBeanList();
        if (null != gameUrlBeanList && !gameUrlBeanList.isEmpty()) {
            StringBuffer sb = new StringBuffer("");
            for (PromoterBean.GameUrlBean gameUrlBean : gameUrlBeanList) {
                if (LeTuUtils.isNull(gameUrlBean.getOs())) {
                    continue;
                }
                sb.append("").append(gameUrlBean.getOs()).append("地址:<br>").append(gameUrlBean.getUrl()).append("<br>");
            }
            if (0 == sb.length()) {
                promoterUrlCopyTv.setVisibility(View.GONE);
            } else {
                promoterUrl = sb.toString();
            }
        }

        promoterCodeTv.setText(promoterCode);
        promoterUrlTv.setText(Html.fromHtml(promoterUrl));
        promoterGameNameTv.setText(gameName);
        promoterBalanceTv.setText("否");
        promoterGainsharingTv.setText(promoterBean.getGainsharing());
        promoterPayNumTv.setText(promoterBean.getPay());
        promoterRegistNumTv.setText(promoterBean.getRegistNum());
        promoterDivideInfoTv.setText(promoterBean.getRadio()+"");

//        setTitle();
        gameNameTv.setText(TimeUtils.dateFormat(mStartTime,TimeUtils.FORMAT_YYYY_MM_DD_HH_MM_SS,TimeUtils.FORMAT_YYYY_MM_C)+" - 推广信息");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        promoterListRv.setLayoutManager(layoutManager);
        mPromoterListRecyclerViewAdapter = new PromoterListRecyclerViewAdapter(this);
        promoterListRv.setAdapter(mPromoterListRecyclerViewAdapter);
    }

    private void setTitle() {
        StringBuffer sb = new StringBuffer();
        sb.append("<font color='#FFA042'>《" + gameName + "》</font>的推广信息");
        gameNameTv.setText(Html.fromHtml(sb.toString()));
    }

    public void putTextIntoClip() {
        ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        //创建ClipData对象
        StringBuffer sb = new StringBuffer();
        sb.append("欢迎下载体验<font color='#FFA042'>《").append(gameName).append("》</font><br>");
        sb.append("推广码:").append(promoterCode).append("<br>");
        sb.append("推广链接:").append("<br>");
        sb.append(promoterUrl);
        ClipData clipData = ClipData.newPlainText("推广信息", Html.fromHtml(sb.toString()));

        // 添加ClipData对象到剪切板中
        clipboardManager.setPrimaryClip(clipData);
    }

    private void setPromoterListData(List<PromoterIncomeListItemResponse> promoterIncomeListItemResponseList) {
        if(null==promoterIncomeListItemResponseList||promoterIncomeListItemResponseList.isEmpty()){
            return;
        }

        for(PromoterIncomeListItemResponse promoterIncomeListItemResponse:promoterIncomeListItemResponseList){
            PromoterListRecyclerViewItemBean promoterListRecyclerViewItemBean = new PromoterListRecyclerViewItemBean();
            promoterListRecyclerViewItemBean.setPromoterName(promoterIncomeListItemResponse.getChannelUserName());
            promoterListRecyclerViewItemBean.setPayNum(promoterIncomeListItemResponse.getPayMoney()+"");
            dataList.add(promoterListRecyclerViewItemBean);
        }

        mPromoterListRecyclerViewAdapter.addAllData(dataList);

    }


    @Override
    public void fetchPromoterIncomeListSuccess(List<PromoterIncomeListItemResponse> promoterIncomeListItemResponseList) {
        setPromoterListData(promoterIncomeListItemResponseList);
    }

    @Override
    public void fetchPromoterIncomeListFails(int code, String msg) {

    }

    @Override
    public void fetchPromoterIncomeListError() {

    }
}
