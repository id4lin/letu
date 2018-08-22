package com.letu.app.game.strategy.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ${user} on 2018/7/10
 */
public class LeTuUtils {

    private static final int MIN_DELAY_TIME= 1000;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;
    /**
     * 说明：
     * 中国电信号段 133、149、153、173、177、180、181、189、199
     * 中国联通号段 130、131、132、145、155、156、166、175、176、185、186
     * 中国移动号段 134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198
     * 其他号段
     * 14号段以前为上网卡专属号段，如中国联通的是145，中国移动的是147等等。
     * 虚拟运营商
     * 电信：1700、1701、1702
     * 移动：1703、1705、1706
     * 联通：1704、1707、1708、1709、171
     * 卫星通信：1349
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }




    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }


    /**
     * 密码有字母或数字
     *
     * @param password
     * @return
     */
    public static boolean isPasswordValid(String password) {
        boolean isValid = false;
        String expression = "^[a-zA-Z0-9]{6,18}$";
        CharSequence inputStr = password;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    /**
     * 检查验证码长度是否有效[6]
     *
     * @param verifyCode
     * @return
     */
    public static boolean isVerifyCodeLengthValid(String verifyCode) {
        if (belowMinLength(6, verifyCode)) {
            return false;
        }

        if (overMaxLength(6, verifyCode)) {
            return false;
        }
        return true;

    }

    /**
     * 检查昵称长度是否有效[1-10]
     *
     * @param nickName
     * @return
     */
    public static boolean isNickNameLengthValid(String nickName) {
        return !overMaxLength(10, nickName);
    }

    /**
     * 检查密码长度是否有效[6-18]
     *
     * @param password
     * @return
     */
    public static boolean isPasswordLengthValid(String password) {
        if (belowMinLength(6, password)) {
            return false;
        }

        if (overMaxLength(18, password)) {
            return false;
        }

        return true;
    }


    /**
     * 长度超过最大值
     *
     * @param max
     * @param source
     * @return
     */
    public static boolean overMaxLength(int max, String source) {

        return source.length() > max;
    }

    /**
     * 长度小于最小值
     *
     * @param min
     * @param source
     * @return
     */
    public static boolean belowMinLength(int min, String source) {
        return source.length() < min;
    }

    public static boolean isNull(String source) {
        return null == source || TextUtils.isEmpty(source);
    }


    public static List<String> fetchImageUrlsFromHtml(String htmlContent) {
        if(isNull(htmlContent)){
            return null;
        }
        List<String> imageSrcList = new ArrayList<String>();
        Pattern p = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic|\\b)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(htmlContent);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("//s+")[0] : m.group(2);
            imageSrcList.add(src);
        }
        if (imageSrcList == null || imageSrcList.size() == 0) {
            Log.e("imageSrcList","资讯中未匹配到图片链接");
            return null;
        }
        return imageSrcList;
    }


    /**
     * 网络连接是否正常
     *
     * @return true:有网络    false:无网络
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


}
