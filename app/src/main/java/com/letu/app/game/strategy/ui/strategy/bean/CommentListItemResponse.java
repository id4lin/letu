package com.letu.app.game.strategy.ui.strategy.bean;

/**
 * Created by ${user} on 2018/7/17
 */
public class CommentListItemResponse {
    private long id;
    private String kind_id;
    private String content;
    private int score;
    private String nickName;
    private String avatarUrl;
    private int isMe;
    private int isLiked;
    private String createtime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKind_id() {
        return kind_id;
    }

    public void setKind_id(String kind_id) {
        this.kind_id = kind_id;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getIsMe() {
        return isMe;
    }

    public void setIsMe(int isMe) {
        this.isMe = isMe;
    }

    public int getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
