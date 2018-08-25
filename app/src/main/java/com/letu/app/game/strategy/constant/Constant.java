package com.letu.app.game.strategy.constant;

import com.letu.app.game.strategy.ui.me.widget.PromoterActivity;

/**
 * Created by ${user} on 2018/7/15
 */
public class Constant {
    public static final int  CODE_REQUEST_LOGIN=1001;
    public static final int  CODE_RESPONSE_LOGIN=1002;

    public static final int  CODE_REQUEST_REGIST=2001;
    public static final int  CODE_RESPONSE_REGIST=2003;

    public static final int  CODE_REQUEST_EDIT_COMMENT=3001;
    public static final int  CODE_RESPONSE_EDIT_COMMENT=3002;

    public static final int  CODE_REQUEST_MODIFY_PASSWORD=4001;
    public static final int  CODE_RESPONSE_MODIFY_PASSWORD=4002;

    public static final int  CODE_REQUEST_MODIFY_NICK_NAME=4003;
    public static final int  CODE_RESPONSE_MODIFY_NICK_NAME=4004;

    public static final int  CODE_REQUEST_MODIFY_AVATAR=4005;
    public static final int  CODE_RESPONSE_MODIFY_AVATAR=4006;

    public static final int  CODE_REQUEST_USER_INFO=5001;
    public static final int  CODE_RESPONSE_USER_INFO=5002;

    public static final int  CODE_REQUEST_PHOTO_CHOOSE=6001;
    public static final int  CODE_RESPONSE_PHOTO_CHOOSE=6002;

    public static final String  OS_NAME_ANDROID="android";
    public static final String  OS_NAME_IOS="ios";

    public static final String  KEY_INTENT_LOGIN_PHONE="KEY_LOGIN_PHONE";
    public static final String  KEY_INTENT_LOGIN_PWD="KEY_INTENT_LOGIN_PWD";

    public static final String  KEY_INTENT_GAME_ID="KEY_INTENT_GAME_ID";
    public static final String  KEY_INTENT_STRATEGY_ID="KEY_INTENT_STRATEGY_ID";

    public static final String  KEY_INTENT_GAME_NAME="KEY_INTENT_GAME_NAME";

    public static final String  KEY_INTENT_ME_WEBVIEW_TITLE="KEY_INTENT_ME_WEBVIEW_TITLE";

    public static final String  KEY_INTENT_ME_WEBVIEW_FILE_NAME="KEY_INTENT_ME_WEBVIEW_FILE_NAME";

    public static final String  KEY_INTENT_PROMOTER_BEAN="KEY_INTENT_PROMOTER_BEAN";

    public static final String  KEY_INTENT_START_TIME="KEY_INTENT_START_TIME";
    public static final String  KEY_INTENT_END_TIME="KEY_INTENT_END_TIME";

    public static final String  KEY_INTENT_POSITION="KEY_INTENT_POSITION";
    public static final String  KEY_INTENT_IMG_LIST="KEY_INTENT_IMG_LIST";

    public static final String  KEY_INTENT_STRATEGY_TITLE="KEY_INTENT_STRATEGY_TITLE";
    public static final String  KEY_INTENT_CREATE_TIME="KEY_INTENT_CREATE_TIME";
    public static final String  KEY_INTENT_CREATE_USER_NAME="KEY_INTENT_CREATE_USER_NAME";


    public static final String  ME_WEBVIEW_FILE_NAME_HELP="me_help.html";
    public static final String  ME_WEBVIEW_FILE_NAME_ABOUT_ME="me_aboutme.html";
    public static final String  ME_WEBVIEW_FILE_NAME_USER_PROTOCOL="me_user_protocol.html";


    public static final String  ME_TEXT_TITLE_GAME="我的游戏";
    public static final String  ME_TEXT_TITLE_COLLECT="我的收藏";
    public static final String  ME_TEXT_TITLE_WELFARE="我的福利";
    public static final String  ME_TEXT_TITLE_HELP="获得帮助";
    public static final String  ME_TEXT_TITLE_BACK_FEED="意见反馈";
    public static final String  ME_TEXT_TITLE_ABOUT_ME="关于我们";
    public static final String  ME_TEXT_TITLE_USER_PROTOCOL="用户协议";
    public static final String  ME_TEXT_TITLE_MODIFY_PASSWORD="修改密码";
    public static final String  ME_TEXT_TITLE_MODIFY_AVATAR="修改头像";
    public static final String  ME_TEXT_TITLE_MODIFY_NICK_NAME="修改昵称";
    public static final String  ME_TEXT_TITLE_PROMOTER="我的推广";


    public static final String ERROR_MSG_NULL_PHONE_NUMBER = "手机号不能为空";
    public static final String ERROR_MSG_NULL_NICK_NAME = "昵称不能为空";
    public static final String ERROR_MSG_NULL_VERIFY_CODE = "验证码不能为空";
    public static final String ERROR_MSG_NULL_PASSWORD = "密码不能为空";
    public static final String ERROR_MSG_VALID_PHONE_NUMBER = "非法的手机号码";
    public static final String ERROR_MSG_VALID_PASSWORD = "密码应该字母或数字";
    public static final String ERROR_MSG_LENGTH_VALID_VERIFY_CODE = "验证码必须为6位";
    public static final String ERROR_MSG_LENGTH_VALID_PASSWORD = "密码字数应在6-18位之间";
    public static final String ERROR_MSG_LENGTH_VALID_NICK_NAME = "昵称字数应在10位以内";


    public static final String  SEND_MSG_TYPE_FORGET_PASSWORD="forgetPassword";



    public static final int  DATA_COUNT_DEFAULT_START=0;
    public static final int  DATA_COUNT_MAX=20;


    public static final int  STATUS_LIKE_NO=0;
    public static final int  STATUS_LIKE_YES=1;
    public static final int  STATUS_COLLECT_NO=0;
    public static final int  STATUS_COLLECT_YES=1;

    public static final int  STATUS_PROMOTER_NO=0;
    public static final int  STATUS_PROMOTER_YES=1;


    public static final int  STATUS_HTTP_COLLECT_EXIST=-3;
    public static final int  STATUS_HTTP_LIKE_EXIST=-3;
    public static final int  STATUS_HTTP_COLLECT_NO_EXIST=-6;






    /**
     * 评论类型-攻略
     */
    public static final String  COMMENT_TYPE_STRATEGY="news";

    /**
     * banner类型-攻略
     */
    public static final String  BANNER_TYPE_STRATEGY="news";
    /**
     * banner类型-游戏
     */
    public static final String  BANNER_TYPE_GAME="game";


    public static final String  IMAGE_TYPE_STRATEGY="news";
    public static final String  IMAGE_TYPE_AVATAR="avatar";




}
