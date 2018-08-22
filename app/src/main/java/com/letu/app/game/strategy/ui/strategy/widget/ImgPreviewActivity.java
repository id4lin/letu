package com.letu.app.game.strategy.ui.strategy.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.constant.Constant;

import java.util.List;

public class ImgPreviewActivity extends AppCompatActivity {

    ViewPager vp;
    TextView tv_position;
    List<String> imgList;
    int mPosition;
    LayoutInflater inflater;
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_page);
        Intent intent = getIntent();
        imgList = intent.getStringArrayListExtra(Constant.KEY_INTENT_IMG_LIST);
        mPosition=intent.getIntExtra(Constant.KEY_INTENT_POSITION,0);
        inflater = LayoutInflater.from(this);
        initView();
        initViewPaper();

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
         screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)

    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.vp);
        tv_position = (TextView) findViewById(R.id.tv_position);
        tv_position.setText((mPosition+1)+"/" + imgList.size());
    }

    private void initViewPaper() {
        vp.setAdapter(new ImagePagerAdapter(imgList));
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
                tv_position.setText((mPosition + 1) + "/" + imgList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp.setCurrentItem(mPosition);
    }

    private class ImagePagerAdapter extends PagerAdapter {

        private List<String> images;
        private LayoutInflater inflater;

        ImagePagerAdapter(List<String> images) {
            this.images = images;
            inflater = getLayoutInflater();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
            assert imageLayout != null;
            final ProgressBar progressBar = (ProgressBar) imageLayout.findViewById(R.id.loading);
            PhotoView image = (PhotoView) imageLayout.findViewById(R.id.image);
//            image.setAdjustViewBounds(true);
//            image.setMaxWidth(screenWidth);
//            Picasso.get()
//                    .load(imgList.get(position))
//                    .tag(this)
//                    .fit()
//                    .into(image);
            RequestOptions requestOptions=new RequestOptions()
                    .centerInside();
            Glide.with(ImgPreviewActivity.this)
                    .load(imgList.get(position))
//                    .apply(requestOptions)
                    .into(image);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
