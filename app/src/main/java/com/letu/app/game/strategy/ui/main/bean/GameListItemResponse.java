package com.letu.app.game.strategy.ui.main.bean;

/**
 * Created by ${user} on 2018/7/15
 */
public class GameListItemResponse {
    private String gameId;
    private String name;
    private String icon;
    private String description;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
