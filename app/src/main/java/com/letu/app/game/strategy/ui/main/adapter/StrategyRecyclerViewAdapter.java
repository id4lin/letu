package com.letu.app.game.strategy.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.ui.common.view.TextViewDrawable;
import com.letu.app.game.strategy.ui.main.bean.StrategyRecyclerViewItemBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class StrategyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<StrategyRecyclerViewItemBean> dataList = new ArrayList<>();
    private OnItemClickListener mClickListener;
    private static final int EMPTY_VIEW = 1;
    private String status;


    public void addAllData(List<StrategyRecyclerViewItemBean> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public StrategyRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView createTime;
        public TextView createUser;
        public TextViewDrawable readTimes;
        //        public ImageView imageView;
        private OnItemClickListener mListener;// 声明自定义的接口

        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mListener = listener;
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.title);
            createTime = (TextView) itemView.findViewById(R.id.create_time);
            createUser = (TextView) itemView.findViewById(R.id.create_user);
            readTimes = (TextViewDrawable) itemView.findViewById(R.id.read_times);
            //            imageView = (ImageView) itemView.findViewById(R.id.game_icon);
        }

        @Override
        public void onClick(View view) {
            if (null != mClickListener) {
                mListener.onItemClick(view, getPosition(), dataList.get(getPosition()).getStrategyId());
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.strategy_recycler_view_item, parent, false);
//        return new ViewHolder(v, mClickListener);

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == EMPTY_VIEW) {
            View view = inflater.inflate(R.layout.layout_empty_view, parent, false);
            return new EmptyViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.strategy_recycler_view_item, parent, false);
            return new ViewHolder(view, mClickListener);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder=(ViewHolder)holder;

            viewHolder.title.setText(dataList.get(position).getTitle());
            viewHolder.createTime.setText(dataList.get(position).getCreateTime());
            viewHolder.createUser.setText(dataList.get(position).getCreateUser());
            if(null==dataList.get(position).getReadTimes()||"0".equals(dataList.get(position).getReadTimes())){
                viewHolder.readTimes.setVisibility(View.GONE);
            }else{
                viewHolder.readTimes.setVisibility(View.VISIBLE);
                viewHolder.readTimes.setText(dataList.get(position).getReadTimes());
            }
            if(null==dataList.get(position).getCreateUser()|| TextUtils.isEmpty(dataList.get(position).getCreateUser())){
                viewHolder.createUser.setVisibility(View.INVISIBLE);
            }else{
                viewHolder.createUser.setVisibility(View.VISIBLE);
                viewHolder.createUser.setText(dataList.get(position).getCreateUser());
            }

            //        Picasso.get()
            //                .load(dataList.get(position).imageView)
            //                .error(R.drawable.ycloud_player_logo_youku)
            //                .fit()
            //                .placeholder(R.drawable.ycloud_player_logo_youku)
            //                .tag(mContext)
            //                .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        //        return dataList.size();
        return dataList.size() > 0 ? dataList.size() : 1;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, String strategyId);
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }


    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_empty)
        public ImageView img;
        @BindView(R.id.empty_msg_tv)
        public TextView msgTv;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}