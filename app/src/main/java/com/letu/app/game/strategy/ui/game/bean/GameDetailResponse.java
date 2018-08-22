package com.letu.app.game.strategy.ui.game.bean;

import java.util.List;

/**
 * Created by ${user} on 2018/7/15
 */
public class GameDetailResponse {
    private GameInfo gameInfo;
    private List<Pic> pic;

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public List<Pic> getPic() {
        return pic;
    }

    public void setPic(List<Pic> pic) {
        this.pic = pic;
    }

    public class GameInfo{
        private String id;
        private String gameName;
        private String gameTarget;
        private String description;
        private String typeId;
        private String state;
        private String discount;
        private String policy;
        private String openTime;
        private String company;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public String getGameTarget() {
            return gameTarget;
        }

        public void setGameTarget(String gameTarget) {
            this.gameTarget = gameTarget;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getPolicy() {
            return policy;
        }

        public void setPolicy(String policy) {
            this.policy = policy;
        }

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }
    }

    public class Pic{
        private String typeId;
        private String url;

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
