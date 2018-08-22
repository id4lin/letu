package com.letu.app.game.strategy.ui.me.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.ui.main.adapter.StrategyRecyclerViewAdapter;
import com.letu.app.game.strategy.ui.me.bean.GameGridRecyclerViewItemBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${user} on 2018/7/18
 */
public class GameGridRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<GameGridRecyclerViewItemBean> dataList = new ArrayList<>();
    private GameGridRecyclerViewAdapter.OnItemClickListener mClickListener;
    private static final int EMPTY_VIEW = 1;
    private String status;


    public void addAllData(List<GameGridRecyclerViewItemBean> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public GameGridRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public ImageView imageView;
        private GameGridRecyclerViewAdapter.OnItemClickListener mListener;// 声明自定义的接口

        public ViewHolder(View itemView, GameGridRecyclerViewAdapter.OnItemClickListener listener) {
            super(itemView);
            mListener = listener;
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.game_name);
            imageView = (ImageView) itemView.findViewById(R.id.game_icon);
        }

        @Override
        public void onClick(View view) {
            if (null != mClickListener) {
                mListener.onItemClick(view, getPosition(), dataList.get(getPosition()).getGameId());
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == EMPTY_VIEW) {
            View view = inflater.inflate(R.layout.layout_empty_grid_view, parent, false);
            return new GameGridRecyclerViewAdapter.EmptyViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.game_grid_recycler_view_item, parent, false);
            return new GameGridRecyclerViewAdapter.ViewHolder(view, mClickListener);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof GameGridRecyclerViewAdapter.ViewHolder) {
            GameGridRecyclerViewAdapter.ViewHolder viewHolder = (GameGridRecyclerViewAdapter.ViewHolder) holder;
            viewHolder.title.setText(dataList.get(position).getGameName());
            //            viewHolder.createTime.setText(dataList.get(position).getCreateTime());
            //            viewHolder.createUser.setText(dataList.get(position).getCreateUser());
            Picasso.get()
                    .load(dataList.get(position).getGameIcon())
                    .error(R.drawable.ic_gamelib_picture)
                    .fit()
                    .placeholder(R.drawable.ic_gamelib_picture)
                    .tag(mContext)
                    .into(viewHolder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        //        return dataList.size();
        return dataList.size() > 0 ? dataList.size() : 1;
    }

    public void setOnItemClickListener(GameGridRecyclerViewAdapter.OnItemClickListener listener) {
        this.mClickListener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, String gameId);
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
