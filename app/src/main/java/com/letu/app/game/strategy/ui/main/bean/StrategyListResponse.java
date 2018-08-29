package com.letu.app.game.strategy.ui.main.bean;

import java.util.List;

/**
 * Created by ${user} on 2018/7/15
 */
public class StrategyListResponse {
    private List<Strategy> newsList;
    private int count;

    public List<Strategy> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<Strategy> newsList) {
        this.newsList = newsList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public class Strategy{
        private String id;
        private String gameId;
        private String title;
        private String creatUser;
        private long creattime;
        private int score;
        private int isLiked;
        private int isCollected;
        private int orderNum;
        private long readTimes;
        private String content;
        private String gamePic;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGameId() {
            return gameId;
        }

        public void setGameId(String gameId) {
            this.gameId = gameId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public String getCreatUser() {
            return creatUser;
        }

        public void setCreatUser(String creatUser) {
            this.creatUser = creatUser;
        }

        public long getCreattime() {
            return creattime;
        }

        public void setCreattime(long creattime) {
            this.creattime = creattime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getIsLiked() {
            return isLiked;
        }

        public void setIsLiked(int isLiked) {
            this.isLiked = isLiked;
        }

        public int getIsCollected() {
            return isCollected;
        }

        public void setIsCollected(int isCollected) {
            this.isCollected = isCollected;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public long getReadTimes() {
            return readTimes;
        }

        public void setReadTimes(long readTimes) {
            this.readTimes = readTimes;
        }

        public String getGamePic() {
            return gamePic;
        }

        public void setGamePic(String gamePic) {
            this.gamePic = gamePic;
        }
    }
}
