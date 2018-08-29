package com.letu.app.game.strategy.ui.me.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${user} on 2018/7/30
 */
public class PromoterBean implements Serializable{
    private String gameName;
    private String pay;
    private String gainsharing;
    private String registNum;
    private String promoterCode;
    private String gameId;
    private float radio;
    private String balance;
//    private String apkUrl;
//    private String ipaUrl;
    private List<GameUrlBean> gameUrlBeanList;

    public static class GameUrlBean implements Serializable{
        private String os;
        private String url;

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }


    public float getRadio() {
        return radio;
    }

    public void setRadio(float radio) {
        this.radio = radio;
    }

    public String getPromoterCode() {
        return promoterCode;
    }

    public void setPromoterCode(String promoterCode) {
        this.promoterCode = promoterCode;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

//    public String getApkUrl() {
//        return apkUrl;
//    }
//
//    public void setApkUrl(String apkUrl) {
//        this.apkUrl = apkUrl;
//    }
//
//    public String getIpaUrl() {
//        return ipaUrl;
//    }
//
//    public void setIpaUrl(String ipaUrl) {
//        this.ipaUrl = ipaUrl;
//    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getGainsharing() {
        return gainsharing;
    }

    public void setGainsharing(String gainsharing) {
        this.gainsharing = gainsharing;
    }

    public String getRegistNum() {
        return registNum;
    }

    public void setRegistNum(String registNum) {
        this.registNum = registNum;
    }

    public List<GameUrlBean> getGameUrlBeanList() {
        return gameUrlBeanList;
    }

    public void setGameUrlBeanList(List<GameUrlBean> gameUrlBeanList) {
        this.gameUrlBeanList = gameUrlBeanList;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
