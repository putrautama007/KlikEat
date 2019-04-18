package com.klikeat.p2p.klikeat.model;

public class CommentModel {
    String userName,userId,userComment,commentDate,produkId, commentId;

    public CommentModel(String userName, String userId, String userComment, String commentDate,
                        String produkId, String commentId) {
        this.userName = userName;
        this.userId = userId;
        this.userComment = userComment;
        this.commentDate = commentDate;
        this.produkId = produkId;
        this.commentId = commentId;
    }

    public CommentModel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getProdukId() {
        return produkId;
    }

    public void setProdukId(String produkId) {
        this.produkId = produkId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
}
