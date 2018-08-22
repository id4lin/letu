package com.letu.app.game.strategy.ui.strategy.webview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.letu.app.baselib.utils.ToastUtil;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.ui.strategy.widget.ImgPreviewActivity;

import java.util.ArrayList;

/**
 * Created by ${user} on 2018/8/1
 */
public class MJavascriptInterface {
    private Activity mContext;
    private ArrayList<String> mImageUrlList=new ArrayList<>();
    private int index = 0;
    public MJavascriptInterface(Activity context) {
        this.mContext = context;
    }

    @JavascriptInterface
    public void openImage(String img,String[] imgs) {
        mImageUrlList.clear();

        for (int i = 0; i < imgs.length; i++) {

            if (imgs[i].equals(img)){

                index = i;
            }
            mImageUrlList.add(imgs[i]);
        }
        Log.i("openImage","img:"+img);
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.putStringArrayListExtra(Constant.KEY_INTENT_IMG_LIST, mImageUrlList);
                intent.putExtra(Constant.KEY_INTENT_POSITION, index);
                intent.setClass(mContext, ImgPreviewActivity.class);
                mContext.startActivity(intent);
            }
        });

    }
}
