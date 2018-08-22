package com.letu.app.game.strategy.ui.strategy.bean;

/**
 * Created by ${user} on 2018/7/15
 */
public class StrategyDetailResponse {
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
    private int isLiked;
    private int isCollected;
    private Game game;

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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public class Game{
        private String id;
        private String gameName;
        private String gameTarget;
        private String description;
        private String typeId;
        private String state;
        private String discount;
        private String policy;
        private String openTime;

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
    }
}
