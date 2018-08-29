package com.letu.app.game.strategy.ui.me.widget;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.letu.app.baselib.base.BaseActivity;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.main.widget.MainActivity;
import com.letu.app.game.strategy.ui.me.adapter.PromoterListAdapter;
import com.letu.app.game.strategy.ui.me.bean.PromoterBean;
import com.letu.app.game.strategy.ui.me.contract.PromoterContract;
import com.letu.app.game.strategy.ui.me.presenter.PromoterPresenter;
import com.letu.app.game.strategy.ui.other.bean.PromoterListItemResponse;
import com.letu.app.game.strategy.utils.LeTuUtils;
import com.letu.app.game.strategy.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PromoterActivity extends BaseActivity<PromoterPresenter> implements PromoterContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.tv_invis_game_name)
    TextView tvInvisGameName;
    @BindView(R.id.invis_layout)
    LinearLayout invisLayout;
    @BindView(R.id.empty_view_layout)
    RelativeLayout emptyViewLayout;
    @BindView(R.id.promoter_current_time_tv)
    TextView promoterCurrentTimeTv;
    @BindView(R.id.promoter_query_btn)
    TextView promoterQueryBtn;
    @BindView(R.id.promoter_loading_pb)
    ProgressBar promoterLoadingPb;

    private String[] strs;
    private List<PromoterBean> mPromoterBeanList;
    private PromoterListAdapter mPromoterListAdapter;

    private TimePickerView pvTime;
    private Date mSelectDate;

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
        setContentView(R.layout.activity_promoter);
        mPresenter.attachView(this);
        mSelectDate=new Date();
        mPresenter.fetchMyPromoterList(mSelectDate);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        initTimePicker();

        mPromoterBeanList = new ArrayList<>();

        View header = View.inflate(this, R.layout.header_layout, null);

        //        lv.addHeaderView(header);
        //取得ListView条目中的悬浮部分(itemLayout)，并将其添加到头部
        lv.addHeaderView(View.inflate(this, R.layout.invis_layout, null));
        mPromoterListAdapter = new PromoterListAdapter(this, mPromoterBeanList);
        lv.setAdapter(mPromoterListAdapter);
        lv.setEmptyView(emptyViewLayout);
        lv.setVerticalScrollBarEnabled(false);
        //监听listView滚动状态
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            //动态控制是否显示invisLayout
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 1) {
                    invisLayout.setVisibility(View.VISIBLE);
                } else {
                    invisLayout.setVisibility(View.GONE);
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 1) {
                    return;
                }
                view.setSelected(true);

                Intent intent = new Intent(mContext, PromoterDetailActivity.class);
                intent.putExtra(Constant.KEY_INTENT_PROMOTER_BEAN, mPromoterBeanList.get(position - 1));
                intent.putExtra(Constant.KEY_INTENT_START_TIME, TimeUtils.firstDayOfMonth(mSelectDate));
                intent.putExtra(Constant.KEY_INTENT_END_TIME, TimeUtils.lastDayOfMonth(mSelectDate));
                startActivity(intent);
            }
        });

        promoterCurrentTimeTv.setText("选择日期："+getTime(new Date()));
    }


    @Override
    public void fetchMyPromoterListSuccess(List<PromoterListItemResponse> promoterListItemResponseList) {
        promoterLoadingPb.setVisibility(View.GONE);
        setPromoterListData(promoterListItemResponseList);
    }

    @Override
    public void fetchMyPromoterListFails(int code, String msg) {
        promoterLoadingPb.setVisibility(View.GONE);
    }

    @Override
    public void fetchMyPromoterListError() {
        promoterLoadingPb.setVisibility(View.GONE);
    }

    private void setPromoterListData(List<PromoterListItemResponse> promoterListItemResponseList) {
        if (null == promoterListItemResponseList || promoterListItemResponseList.isEmpty()) {
            return;
        }
        mPromoterBeanList.clear();

        for (PromoterListItemResponse promoterListItemResponse : promoterListItemResponseList) {
            PromoterBean promoterBean = new PromoterBean();
            promoterBean.setGameName(promoterListItemResponse.getGameName());
            promoterBean.setGainsharing(promoterListItemResponse.getAgentMoney() + "");
            promoterBean.setRegistNum(promoterListItemResponse.getRegistNum() + "");
            promoterBean.setGameId(promoterListItemResponse.getGameId());
            promoterBean.setPromoterCode(promoterListItemResponse.getCode());
            promoterBean.setPay(promoterListItemResponse.getPayMoney() + "");
            promoterBean.setRadio(promoterListItemResponse.getRadio());
            promoterBean.setBalance("否");

            List<PromoterListItemResponse.DownloadBean> downloadBeanList = promoterListItemResponse.getDownloadBean();
            if (null != downloadBeanList && !downloadBeanList.isEmpty()) {
                List<PromoterBean.GameUrlBean> gameUrlBeanList = new ArrayList<>();

                for (PromoterListItemResponse.DownloadBean downloadBean : downloadBeanList) {
                    PromoterBean.GameUrlBean gameUrlBean = new PromoterBean.GameUrlBean();
                    gameUrlBean.setOs(downloadBean.getOs());
                    gameUrlBean.setUrl(downloadBean.getUrl());
                    gameUrlBeanList.add(gameUrlBean);

                    //                    if(Constant.OS_NAME_ANDROID.equals(downloadBean.getOs())){
                    //                        promoterBean.setApkUrl(downloadBean.getUrl());
                    //                    }else if(Constant.OS_NAME_IOS.equals(downloadBean.getOs())){
                    //                        promoterBean.setIpaUrl(downloadBean.getUrl());
                    //                    }
                }
                promoterBean.setGameUrlBeanList(gameUrlBeanList);
            }

            mPromoterBeanList.add(promoterBean);
        }

        mPromoterListAdapter.addDataSet(mPromoterBeanList);

    }

    @OnClick(R.id.promoter_current_time_tv)
    public void onPromoterCurrentTimeTvClicked() {

        if(null==pvTime){
            return;
        }
        pvTime.show(promoterCurrentTimeTv);
    }

    @OnClick(R.id.promoter_query_btn)
    public void onPromoterQueryBtnClicked() {
        if(LeTuUtils.isFastClick()){
            return;
        }
        mPresenter.fetchMyPromoterList(mSelectDate);
        promoterLoadingPb.setVisibility(View.VISIBLE);
    }


    private void initTimePicker() {//Dialog 模式下，在底部弹出

        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                promoterCurrentTimeTv.setText("选择日期："+getTime(date));
                mSelectDate=date;
            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                    }
                })
                .setType(new boolean[]{true, true, false, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        return format.format(date);
    }
}
