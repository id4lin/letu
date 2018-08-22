package com.letu.app.game.strategy.ui.strategy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.ui.common.transformation.CircleTransform;
import com.letu.app.game.strategy.ui.strategy.bean.CommentListItemBean;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${user} on 2018/7/11
 */
public class CommentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<CommentListItemBean> mDataList = new ArrayList<>();
    private OnItemClickListener mClickListener;
    private boolean isLoading;


    public CommentListAdapter(Context context, List<CommentListItemBean> dataList,RecyclerView recyclerView) {
        mContext = context;
        mDataList = dataList;
        addRecyclerViewScrollListener(recyclerView);
    }

    public CommentListAdapter(Context context) {
        mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public void addAllData(List<CommentListItemBean> dataList) {

        int lastIndex = this.mDataList.size();
        if (this.mDataList.addAll(dataList)) {
            notifyItemRangeInserted(lastIndex, dataList.size());
        }

        notifyDataSetChanged();
    }

    public void clearData() {
        this.mDataList.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView comment;
        public TextView createTime;
        public ImageView imageView;
        private OnItemClickListener mListener;// 声明自定义的接口


        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mListener = listener;
            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.name);
            comment = (TextView) itemView.findViewById(R.id.comment);
            createTime = (TextView) itemView.findViewById(R.id.create_time);
            imageView = (ImageView) itemView.findViewById(R.id.icon);
        }

        @Override
        public void onClick(View view) {
            if (null != mClickListener) {
                mListener.onItemClick(view, getPosition());
            }
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //
        //        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent, false);
        //        return new ViewHolder(v, mClickListener);
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent, false);
            return new ViewHolder(v, mClickListener);
        } else if (viewType == TYPE_FOOTER) {
            //脚布局
            //                View view = View.inflate(mContext,R.layout.recycler_load_more_layout,null);
            View footView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_load_more_layout, parent, false);
            return new FootViewHolder(footView);
        }


        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).name.setText(mDataList.get(position).getName());
            ((ViewHolder) holder).comment.setText(mDataList.get(position).getComment());
            ((ViewHolder) holder).createTime.setText(mDataList.get(position).getCreateTime());
            String icon = mDataList.get(position).getIcon();
            if (TextUtils.isEmpty(icon)) {
                icon = "icon";
            }
            Picasso.get()
                    .load(icon)
                    .error(R.drawable.user_default_photo)
                    .fit()
                    .placeholder(R.drawable.user_default_photo)
                    .tag(mContext)
                    .into(((ViewHolder) holder).imageView);

            RequestCreator requestCreator=Picasso.get()
                    .load(icon);

            requestCreator.transform(new CircleTransform())
                    .error(R.drawable.user_default_photo)
                    .fit()
                    .placeholder(R.drawable.user_default_photo)
                    .tag(mContext)
                    .into(((ViewHolder) holder).imageView);
        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            if (position == 0) {//如果第一个就是脚布局,,那就让他隐藏
                footViewHolder.mProgressBar.setVisibility(View.GONE);
                footViewHolder.tv_line1.setVisibility(View.GONE);
                footViewHolder.tv_line2.setVisibility(View.GONE);
                footViewHolder.tv_state.setText("");
            }

            footViewHolder.tv_state.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnLoadMoreClickListener.onLoadMore();
                    notifyDataSetChanged();
                }
            });

            switch (footer_state) {//根据状态来让脚布局发生改变
                case PULL_LOAD_MORE://上拉加载
                    footViewHolder.mProgressBar.setVisibility(View.GONE);
                    footViewHolder.tv_line1.setVisibility(View.GONE);
                    footViewHolder.tv_line2.setVisibility(View.GONE);
                    footViewHolder.tv_state.setText("点击加载更多");
                    break;
                case LOADING_MORE:
                    footViewHolder.mProgressBar.setVisibility(View.VISIBLE);
                    footViewHolder.tv_line1.setVisibility(View.GONE);
                    footViewHolder.tv_line2.setVisibility(View.GONE);
                    footViewHolder.tv_state.setText("正在加载...");
                    break;
                case NO_MORE:
                    footViewHolder.mProgressBar.setVisibility(View.GONE);
                    footViewHolder.tv_line1.setVisibility(View.VISIBLE);
                    footViewHolder.tv_line2.setVisibility(View.VISIBLE);
                    footViewHolder.tv_state.setText("没有更多数据了");
                    footViewHolder.tv_state.setTextColor(Color.parseColor("#ff00ff"));
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return null != mDataList ? mDataList.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        //如果position正好等于所有mDataList.size(),说明是最后一个item,将它设置为脚布局
        if (position == mDataList.size()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    //普通布局的type
    static final int TYPE_ITEM = 0;
    //脚布局
     static final int TYPE_FOOTER = 1;
    //上拉加载更多
    public static final int PULL_LOAD_MORE = 0;
    //正在加载更多
    public static final int LOADING_MORE = 1;
    //没有更多
    public static final int NO_MORE = 2;
    //脚布局当前的状态,默认为没有更多
    int footer_state = 0;

    /**
     * 改变脚布局的状态的方法,在activity根据请求数据的状态来改变这个状态
     *
     * @param state
     */
    public void changeState(int state) {
        this.footer_state = state;
        notifyDataSetChanged();

    }

    /**
     * 脚布局的ViewHolder
     */
    public static class FootViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar mProgressBar;
        private TextView tv_state;
        private TextView tv_line1;
        private TextView tv_line2;


        public FootViewHolder(View itemView) {
            super(itemView);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
            tv_state = (TextView) itemView.findViewById(R.id.foot_view_item_tv);
            tv_line1 = (TextView) itemView.findViewById(R.id.tv_line1);
            tv_line2 = (TextView) itemView.findViewById(R.id.tv_line2);

        }
    }


    public interface OnLoadMoreClickListener {
        void onLoadMore();
    }

    private OnLoadMoreClickListener mOnLoadMoreClickListener;

    public void setOnLoadMoreClickListener(OnLoadMoreClickListener onLoadMoreClickListener) {
        mOnLoadMoreClickListener = onLoadMoreClickListener;

    }


    private void addRecyclerViewScrollListener(RecyclerView recyclerView){
        if(null==recyclerView){
            return;
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int state) {
                super.onScrollStateChanged(recyclerView, state);
                if (state==RecyclerView.SCROLL_STATE_IDLE){
                    Log.d("LoadMoreRecyclerView","run in onScrollStateChanged");
                    RecyclerView.LayoutManager layoutManager=recyclerView.getLayoutManager();
                    int lastVisiblePosition;
                    if (layoutManager instanceof GridLayoutManager){
                        lastVisiblePosition=((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }else if (layoutManager instanceof StaggeredGridLayoutManager){
                        int into[]=new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager)layoutManager).findLastVisibleItemPositions(into);
                        lastVisiblePosition=findMax(into);
                    }else {
                        lastVisiblePosition= ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }

                    Log.d("LoadMoreRecyclerView","ChildCount: "+layoutManager.getChildCount()+" lastvisiblePosition: "
                            +lastVisiblePosition+" ItemCount: "+layoutManager.getItemCount());

                    if (layoutManager.getChildCount()>0             //当当前显示的item数量>0
                            &&lastVisiblePosition>=layoutManager.getItemCount()-1           //当当前屏幕最后一个加载项位置>=所有item的数量
                            &&layoutManager.getItemCount()>layoutManager.getChildCount()) { // 当当前总Item数大于可见Item数
                        Log.d("LoadMoreRecyclerView","run onLoadMore");
                        if (mOnLoadMoreClickListener!=null){
                            mOnLoadMoreClickListener.onLoadMore();
                        }
                    }

                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }
    //To find the maximum value in the array
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

}
