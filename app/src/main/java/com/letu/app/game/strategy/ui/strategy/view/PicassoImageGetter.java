package com.letu.app.game.strategy.ui.strategy.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.letu.app.game.strategy.LetuApplicaiton;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by ${user} on 2018/7/10
 */
public class PicassoImageGetter implements Html.ImageGetter {
    private TextView textView = null;

    public PicassoImageGetter(TextView target) {
        textView = target;
    }

    @Override
    public Drawable getDrawable(final String source) {
        BitmapDrawablePlaceHolder drawable = new BitmapDrawablePlaceHolder();

        Picasso.get()
                .load(source)
//                .fit()
                .tag(LetuApplicaiton.getContext())
//                .placeholder(R.drawable.item_defaut_img)
                //                .error(R.drawable.)
                .into(drawable);

        return drawable;
    }

    class BitmapDrawablePlaceHolder extends BitmapDrawable implements Target {

        protected Drawable drawable;

        @Override
        public void draw(final Canvas canvas) {
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }

        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            if (textView != null) {
                textView.setText(textView.getText());
            }
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            setDrawable(new BitmapDrawable(bitmap));
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            setDrawable(errorDrawable);
        }


        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    }
}
