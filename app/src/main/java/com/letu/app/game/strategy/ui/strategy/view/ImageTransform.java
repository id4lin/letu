package com.letu.app.game.strategy.ui.strategy.view;

import android.graphics.Bitmap;

import com.bigkoo.convenientbanner.utils.ScreenUtil;
import com.letu.app.game.strategy.LetuApplicaiton;
import com.letu.app.game.strategy.utils.DensityUtils;
import com.letu.app.game.strategy.utils.LeTuUtils;
import com.squareup.picasso.Transformation;

public class ImageTransform implements Transformation {

    private String Key = "ImageTransform";

    @Override
    public Bitmap transform(Bitmap source) {//40 是我项目中 的图片间距
        int targetWidth = ScreenUtil.getScreenWidth(LetuApplicaiton.getContext()) - DensityUtils.dp2px(LetuApplicaiton.getContext(), 40);
        if (source.getWidth() == 0) {
            return source;
        }
        //如果图片小于设置的宽度，做处理
        if (source.getWidth() < targetWidth) {
            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetHeight = (int) (targetWidth * aspectRatio);

            if (targetHeight != 0 && targetWidth != 0) {
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            } else {
                return source;
            }
        } else {
            return source;
        }
    }

    @Override
    public String key() {
        return Key;
    }
}
