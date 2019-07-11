package com.tblog.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long contentId;

    private Long commentatorId;

    private Long acceptCommentId;

    private Date createTime;

    private String childComment;

    private Integer upvote;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Long getCommentatorId() {
        return commentatorId;
    }

    public void setCommentatorId(Long commentatorId) {
        this.commentatorId = commentatorId;
    }

    public Long getAcceptCommentId() {
        return acceptCommentId;
    }

    public void setAcceptCommentId(Long acceptCommentId) {
        this.acceptCommentId = acceptCommentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getChildComment() {
        return childComment;
    }

    public void setChildComment(String childComment) {
        this.childComment = childComment == null ? null : childComment.trim();
    }

    public Integer getUpvote() {
        return upvote;
    }

    public void setUpvote(Integer upvote) {
        this.upvote = upvote;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}