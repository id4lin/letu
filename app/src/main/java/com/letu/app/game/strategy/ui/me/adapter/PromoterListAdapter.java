package com.letu.app.game.strategy.ui.me.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.ui.me.bean.PromoterBean;

import java.util.List;

/**
 * Created by ${user} on 2018/7/30
 */
public class PromoterListAdapter extends BaseAdapter {
    private Activity mContext;
    private List<PromoterBean> mDataList;
    private LayoutInflater mInflater;
    public PromoterListAdapter(Activity context,List<PromoterBean> dataList){
        mContext=context;
        mDataList=dataList;
        mInflater = LayoutInflater.from(context);

    }

    public void addDataSet(List<PromoterBean> dataList){
        mDataList=dataList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return null==mDataList?0:mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null==mDataList?null:mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.promoter_list_item_layout, parent, false);
            holder = new ViewHolder();
            holder.gameTv=(TextView)convertView.findViewById(R.id.tv_promoter_game_name);
            holder.payTv=(TextView)convertView.findViewById(R.id.tv_promoter_pay);
            holder.gainsharingTv=(TextView)convertView.findViewById(R.id.tv_promoter_gainsharing);
            holder.registTv=(TextView)convertView.findViewById(R.id.tv_promoter_regist);
            holder.promoterCodeTv=(TextView)convertView.findViewById(R.id.tv_promoter_code);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

//        if(convertView.isSelected()){
//            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
//        }else
        if (position % 2 == 0) {
            convertView.setBackgroundResource(R.drawable.list_bg_selector1);
//            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            convertView.setBackgroundResource(R.drawable.list_bg_selector);
//            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.list_item_color));
        }
        holder.gameTv.setText(mDataList.get(position).getGameName());
//        holder.payTv.setText(mDataList.get(position).getPay());
        holder.gainsharingTv.setText(mDataList.get(position).getGainsharing());
        holder.registTv.setText(mDataList.get(position).getRegistNum());
        holder.promoterCodeTv.setText(mDataList.get(position).getPromoterCode());


        return convertView;
    }

    private class ViewHolder {
        TextView gameTv;
        TextView gainsharingTv;
        TextView payTv;
        TextView registTv;
        TextView promoterCodeTv;
    }

}
