package com.example.github.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class GithubIssue {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("user")
    @Expose
    private GithubProfile user;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("comments")
    @Expose
    private Integer comments;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public GithubIssue() {
    }

    public GithubIssue(Integer id, Integer number, String title, GithubProfile user, String state, Integer comments, String createdAt) {
        super();
        this.id = id;
        this.number = number;
        this.title = title;
        this.user = user;
        this.state = state;
        this.comments = comments;
        this.createdAt = createdAt;


    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GithubProfile getUser() {
        return user;
    }

    public void setUser(GithubProfile user) {
        this.user = user;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


}


