package com.letu.app.game.strategy.ui.me.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.letu.app.baselib.base.BaseActivity;
import com.letu.app.baselib.utils.ToastUtil;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.me.adapter.PromoterListAdapter;
import com.letu.app.game.strategy.ui.me.bean.PromoterBean;
import com.letu.app.game.strategy.ui.me.contract.PromoterContract;
import com.letu.app.game.strategy.ui.me.presenter.PromoterPresenter;
import com.letu.app.game.strategy.ui.other.bean.PromoterListItemResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private String[] strs;
    private List<PromoterBean> mPromoterBeanList;
    private PromoterListAdapter mPromoterListAdapter;

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
        mPresenter.fetchMyPromoterList();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mPromoterBeanList = new ArrayList<>();

        View header = View.inflate(this, R.layout.header_layout, null);

//        lv.addHeaderView(header);
        //取得ListView条目中的悬浮部分(itemLayout)，并将其添加到头部
        lv.addHeaderView(View.inflate(this, R.layout.invis_layout, null));
        mPromoterListAdapter=new PromoterListAdapter(this,mPromoterBeanList);
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
            public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
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
                if(position<1){
                    return;
                }
                view.setSelected(true);

                Intent intent=new Intent(mContext,PromoterDetailActivity.class);
                intent.putExtra(Constant.KEY_INTENT_PROMOTER_BEAN,mPromoterBeanList.get(position-1));
                startActivity(intent);
            }
        });
    }


    @Override
    public void fetchMyPromoterListSuccess(List<PromoterListItemResponse> promoterListItemResponseList) {
        setPromoterListData(promoterListItemResponseList);
    }

    @Override
    public void fetchMyPromoterListFails(int code, String msg) {

    }

    private void setPromoterListData(List<PromoterListItemResponse> promoterListItemResponseList){
        if(null==promoterListItemResponseList||promoterListItemResponseList.isEmpty()){
            return;
        }

        for(PromoterListItemResponse promoterListItemResponse:promoterListItemResponseList){
            PromoterBean promoterBean = new PromoterBean();
            promoterBean.setGameName(promoterListItemResponse.getGameName());
            promoterBean.setGainsharing(promoterListItemResponse.getPayMoney()+"");
            promoterBean.setRegistNum(promoterListItemResponse.getRegistNum()+"");
            promoterBean.setGameId(promoterListItemResponse.getGameId());
            promoterBean.setPromoterCode(promoterListItemResponse.getCode());

            List<PromoterListItemResponse.DownloadBean> downloadBeanList=promoterListItemResponse.getDownloadBean();
            if(null!=downloadBeanList&&!downloadBeanList.isEmpty()){
                List<PromoterBean.GameUrlBean> gameUrlBeanList=new ArrayList<>();

                for (PromoterListItemResponse.DownloadBean downloadBean:downloadBeanList){
                    PromoterBean.GameUrlBean gameUrlBean=new PromoterBean.GameUrlBean();
                    gameUrlBean.setOs(downloadBean.getOs());
                    gameUrlBean.setUrl(downloadBean.getUrl());
                    gameUrlBeanList.add(gameUrlBean);

//                    if(Constant.OS_NAME_ANDROID.equals(downloadBean.getOs())){
//                        promoterBean.setApkUrl(downloadBean.getUrl());
//                    }else if(Constant.OS_NAME_IOS.equals(downloadBean.getOs())){
//                        promoterBean.setIpaUrl(downloadBean.getUrl());
//                    }
                }
                promoterBean.setGameUrlBean(gameUrlBeanList);
            }

            mPromoterBeanList.add(promoterBean);
        }

        mPromoterListAdapter.addDataSet(mPromoterBeanList);

    }
}
