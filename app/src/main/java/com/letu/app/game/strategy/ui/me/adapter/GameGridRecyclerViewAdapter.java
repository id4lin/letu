package com.letu.app.game.strategy.ui.me.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.letu.app.baselib.utils.ToastUtil;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.ui.common.bottompopfragmentmenu.BottomMenuFragment;
import com.letu.app.game.strategy.ui.common.bottompopfragmentmenu.MenuItem;
import com.letu.app.game.strategy.ui.common.bottompopfragmentmenu.MenuItemOnClickListener;
import com.letu.app.game.strategy.ui.common.view.BadgeView;
import com.letu.app.game.strategy.ui.main.adapter.StrategyRecyclerViewAdapter;
import com.letu.app.game.strategy.ui.me.bean.GameGridRecyclerViewItemBean;
import com.letu.app.game.strategy.utils.DensityUtils;
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
    private GameGridRecyclerViewAdapter.OnItemLongClickListener mOnItemLongClickListener;
    private static final int EMPTY_VIEW = 1;
    private String status;
    private boolean isEdited=false;
    private OnDeleteItemClickListener mOnDeleteItemClickListener;
    private int mCurrentDeletePosition;


    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
        notifyDataSetChanged();
    }

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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        public TextView title;
        public ImageView imageView;
        public ConstraintLayout mGridItemConstraintLayout;
        public BadgeView mBadgeView;
        private GameGridRecyclerViewAdapter.OnItemClickListener mListener;// 声明自定义的接口
        private GameGridRecyclerViewAdapter.OnItemLongClickListener mLongClickListener;// 声明自定义的接口

        public ViewHolder(View itemView, GameGridRecyclerViewAdapter.OnItemClickListener listener,GameGridRecyclerViewAdapter.OnItemLongClickListener longClickListener) {
            super(itemView);
            this.mListener = listener;
            this.mLongClickListener=longClickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            title = (TextView) itemView.findViewById(R.id.game_name);
            imageView = (ImageView) itemView.findViewById(R.id.game_icon);
            mGridItemConstraintLayout=(ConstraintLayout)itemView.findViewById(R.id.grid_item_constraint_layout);
            mBadgeView=new BadgeView(mContext,mGridItemConstraintLayout);
//            mBadgeView.setText("一");
            mBadgeView.setBackgroundResource(R.drawable.ic_sign_delete);
            mBadgeView.setHeight(DensityUtils.dp2px(mContext,20));
            mBadgeView.setWidth(DensityUtils.dp2px(mContext,20));

            mBadgeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showBottomMenu(getPosition(),dataList.get(getPosition()).getGameId());
                }
            });


        }

        @Override
        public void onClick(View view) {
            if(isEdited){
                return;
            }
            if (null != mListener) {
                mListener.onItemClick(view, getPosition(), dataList.get(getPosition()).getGameId());
            }
        }

        @Override
        public boolean onLongClick(View view) {
//            if(!isEdited){
//                return false;
//            }

            if(null==mLongClickListener){
                return false;
            }

            mLongClickListener.onItemClick(view, getPosition(), dataList.get(getPosition()).getGameId());

            return true;
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
            return new GameGridRecyclerViewAdapter.ViewHolder(view, mClickListener,mOnItemLongClickListener);
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

            if(isEdited){
                viewHolder.mBadgeView.show();
                TranslateAnimation animation = new TranslateAnimation(0, -5, 0, 0);
                animation.setInterpolator(new OvershootInterpolator());//OvershootInterpolator
                animation.setDuration(200);
                animation.setRepeatCount(-1);
                animation.setRepeatMode(Animation.REVERSE);
                viewHolder.mGridItemConstraintLayout.startAnimation(animation);

            }else{
                viewHolder.mBadgeView.hide();
            }
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

    public void setOnItemLongClickListener(GameGridRecyclerViewAdapter.OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, String gameId);
    }

    public interface OnItemLongClickListener {
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

    private void showBottomMenu(int position,String gameId) {
        BottomMenuFragment bottomMenuFragment = new BottomMenuFragment();

        List<MenuItem> menuItemList = new ArrayList<MenuItem>();
        MenuItem menuItem1 = new MenuItem();
        menuItem1.setText("确认要删除该游戏吗？");
        menuItem1.setStyle(MenuItem.MenuItemStyle.STRESS);
        MenuItem menuItem2 = new MenuItem();
        menuItem2.setText("确定");
        //        menuItem2.setStyle(MenuItem.MenuItemStyle.COMMON);
        menuItem2.setMenuItemOnClickListener(new MenuItemOnClickListener(bottomMenuFragment, menuItem2) {
            @Override
            public void onClickMenuItem(View v, MenuItem menuItem) {

                if(null==mOnDeleteItemClickListener){
                    return;
                }

                mCurrentDeletePosition=position;
                mOnDeleteItemClickListener.onDeleteItem(position,dataList.size(),gameId);


//                if(mOnDeleteItemClickListener.onDeleteItem(position,dataList.size(),gameId)){
//                    dataList.remove(position);
//                    notifyDataSetChanged();
//                }
            }
        });
        menuItemList.add(menuItem1);
        menuItemList.add(menuItem2);

        bottomMenuFragment.setMenuItems(menuItemList);

        bottomMenuFragment.show(((Activity)mContext).getFragmentManager(), "BottomMenuFragment");
    }

    public interface OnDeleteItemClickListener {
        void onDeleteItem(int position,int size, String gameId);
    }

    public OnDeleteItemClickListener getOnDeleteItemClickListener() {
        return mOnDeleteItemClickListener;
    }

    public void setOnDeleteItemClickListener(OnDeleteItemClickListener onDeleteItemClickListener) {
        this.mOnDeleteItemClickListener = onDeleteItemClickListener;
    }

    public void deleteSuccessCallback(){
        dataList.remove(mCurrentDeletePosition);
        notifyDataSetChanged();
    }
}
