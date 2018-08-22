package com.letu.app.game.strategy.ui.other.bean;

import java.util.List;

/**
 * Created by ${user} on 2018/7/31
 */
public class PromoterListItemResponse {
    private int id;
    private String clientUserId;
    private String gameId;
    private String state;
    private String code;
    private String gameName;
    private long registNum;
    private long payMoney;
    private List<DownloadBean> downloadBean;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(String clientUserId) {
        this.clientUserId = clientUserId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public long getRegistNum() {
        return registNum;
    }

    public void setRegistNum(long registNum) {
        this.registNum = registNum;
    }

    public long getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(long payMoney) {
        this.payMoney = payMoney;
    }

    public List<DownloadBean> getDownloadBean() {
        return downloadBean;
    }

    public void setDownloadBean(List<DownloadBean> downloadBean) {
        this.downloadBean = downloadBean;
    }

    public class DownloadBean{
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
}
