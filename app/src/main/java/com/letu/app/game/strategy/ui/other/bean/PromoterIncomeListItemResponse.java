package com.letu.app.game.strategy.ui.other.bean;

/**
 * Created by ${user} on 2018/8/24
 */
public class PromoterIncomeListItemResponse {
    private String channelUserName;
    private String channelUserId;
    private String channelRegisterTime;
    private long payMoney;

    public String getChannelUserName() {
        return channelUserName;
    }

    public void setChannelUserName(String channelUserName) {
        this.channelUserName = channelUserName;
    }

    public String getChannelUserId() {
        return channelUserId;
    }

    public void setChannelUserId(String channelUserId) {
        this.channelUserId = channelUserId;
    }

    public String getChannelRegisterTime() {
        return channelRegisterTime;
    }

    public void setChannelRegisterTime(String channelRegisterTime) {
        this.channelRegisterTime = channelRegisterTime;
    }

    public long getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(long payMoney) {
        this.payMoney = payMoney;
    }
}
