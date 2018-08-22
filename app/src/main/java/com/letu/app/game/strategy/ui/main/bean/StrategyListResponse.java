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
        private String typeOne;
        private String typeTwo;
        private String source;
        private String creatUser;
        private long creattime;
        private String content;
        private int score;
        private String game;

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

        public String getTypeOne() {
            return typeOne;
        }

        public void setTypeOne(String typeOne) {
            this.typeOne = typeOne;
        }

        public String getTypeTwo() {
            return typeTwo;
        }

        public void setTypeTwo(String typeTwo) {
            this.typeTwo = typeTwo;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
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

        public String getGame() {
            return game;
        }

        public void setGame(String game) {
            this.game = game;
        }
    }
}
