package com.letu.app.game.strategy.bean;

import com.letu.app.game.strategy.constant.Constant;

/**
 * Created by ${user} on 2018/7/15
 */
public class UserInfo {
    private String phone;
    private String avatarUrl;
    private String nickName;
    private String userId;
    private String token;
    private boolean isPromoter;

    public boolean isPromoter() {
        return isPromoter;
    }

    public void setIsPromoter(int promoter) {
        isPromoter=Constant.STATUS_PROMOTER_YES==promoter;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserInfo\n[\nphone:" + phone + "\navatarUrl" + avatarUrl + "\nnickName" + nickName + "\nuserId" + userId + "\ntoken" + token + "\n]";
    }
}
