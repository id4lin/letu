package com.letu.app.game.strategy.ui.me.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.ui.me.bean.GameGridRecyclerViewItemBean;
import com.letu.app.game.strategy.ui.me.bean.PromoterListRecyclerViewItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${user} on 2018/8/20
 */
public class PromoterListRecyclerViewAdapter extends RecyclerView.Adapter<PromoterListRecyclerViewAdapter.ViewHolder> {
    private List<PromoterListRecyclerViewItemBean> dataList = new ArrayList<>();
    private Context mContext;

    public PromoterListRecyclerViewAdapter(Context context) {
        mContext = context;
    }


    public void addAllData(List<PromoterListRecyclerViewItemBean> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.promoter_list_recycler_view_item_layout, parent, false);
        return new PromoterListRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder instanceof PromoterListRecyclerViewAdapter.ViewHolder) {
            holder.promoterName.setText(dataList.get(position).getPromoterName());
            holder.payNum.setText(dataList.get(position).getPayNum());
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView promoterName;
        public TextView payNum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            promoterName = (TextView) itemView.findViewById(R.id.promoter_name);
            payNum = (TextView) itemView.findViewById(R.id.pay_num);
        }
    }
}
