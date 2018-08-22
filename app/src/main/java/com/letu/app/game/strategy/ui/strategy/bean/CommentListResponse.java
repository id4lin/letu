package com.letu.app.game.strategy.ui.strategy.bean;

import java.util.List;

/**
 * Created by ${user} on 2018/7/15
 */
public class CommentListResponse {
    private int count;
    private List<CommentListItemResponse> comments;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CommentListItemResponse> getComments() {
        return comments;
    }

    public void setComments(List<CommentListItemResponse> comments) {
        this.comments = comments;
    }
}
