package com.letu.app.game.strategy.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.ui.main.bean.GameRecyclerViewItemBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WuXiaolong
 * on 2015/7/2.
 */
public class GameRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<GameRecyclerViewItemBean> dataList = new ArrayList<>();
    private OnItemClickListener mClickListener;
    private static final int EMPTY_VIEW = 1;


    public void addAllData(List<GameRecyclerViewItemBean> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public GameRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView subTitle;
        public ImageView imageView;
        public Button button;
        private OnItemClickListener mListener;// 声明自定义的接口


        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mListener = listener;
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.title);
            subTitle = (TextView) itemView.findViewById(R.id.subTitle);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            button = (Button) itemView.findViewById(R.id.op_button);
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
        //        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_recycler_view_item, parent, false);

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == EMPTY_VIEW) {
            View view = inflater.inflate(R.layout.layout_empty_view, parent, false);
            return new EmptyViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.game_recycler_view_item, parent, false);
            return new ViewHolder(view, mClickListener);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof GameRecyclerViewAdapter.ViewHolder) {
            GameRecyclerViewAdapter.ViewHolder viewHolder = (GameRecyclerViewAdapter.ViewHolder) holder;
            viewHolder.title.setText(dataList.get(position).getTitle());
            viewHolder.subTitle.setText(dataList.get(position).getSubTitle());
            Picasso.get()
                    .load(dataList.get(position).getImageView())
                    .error(R.drawable.ic_gamelib_picture)
                    .fit()
                    .placeholder(R.drawable.ic_gamelib_picture)
                    .tag(mContext)
                    .into(viewHolder.imageView);

            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "我要玩", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() > 0 ? dataList.size() : 1;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
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