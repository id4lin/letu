package com.letu.app.game.strategy.ui.other.bean;

/**
 * Created by ${user} on 2018/7/16
 */
public class VerifyTokenResponse {
    private String phone;
    private String avatarUrl;
    private String nickName;
    private String userId;
    private int isSpreader;

    public int getIsSpreader() {
        return isSpreader;
    }

    public void setIsSpreader(int isSpreader) {
        this.isSpreader = isSpreader;
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

    @Override
    public String toString() {
        return "VerifyTokenResponse\n[\nphone:" + phone + "\navatarUrl:" + avatarUrl + "\nnickName:" + nickName + "\nuserId:" + userId + "\n]";
    }
}
