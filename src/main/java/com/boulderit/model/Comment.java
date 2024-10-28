package com.boulderit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document("comments")
public class Comment {
    @Id
    private String id;
    @Field("created_at")
    private Date createdAt;
    private Integer flags;
    private String nickname;
    @DocumentReference
    @Field("problem_id")
    private Problem problem;
    private String text;
    private Integer upvotes;

    public Comment(){

    }

    public Comment(String id, Date createdAt, Integer flags, String nickname, Problem problem, String text, Integer upvotes) {
        this.id = id;
        this.createdAt = createdAt;
        this.flags = flags;
        this.nickname = nickname;
        this.problem = problem;
        this.text = text;
        this.upvotes = upvotes;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", flags=" + flags +
                ", nickname='" + nickname + '\'' +
                ", problem=" + problem +
                ", text='" + text + '\'' +
                ", upvotes=" + upvotes +
                '}';
    }
}
