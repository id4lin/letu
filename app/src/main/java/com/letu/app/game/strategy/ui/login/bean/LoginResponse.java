package com.letu.app.game.strategy.ui.login.bean;

/**
 * Created by ${user} on 2018/7/13
 */
public class LoginResponse {

    private String phone;
    private String avatarUrl;
    private String nickName;
    private String userId;
    private String token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponse\n[\nphone:" + phone + "\nisSpreader:" + isSpreader + "\navatarUrl:" + avatarUrl + "\nnickName:" + nickName + "\nuserId:" + userId + "\ntoken:" + token + "\n]";
    }
}
