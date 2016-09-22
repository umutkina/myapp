package com.umutkina.findunfollowersapp.modals;

import java.io.Serializable;

import twitter4j.User;

/**
 * Created by umutkina on 28/08/16.
 */
public class ModelUser implements Serializable {
    private static final long serialVersionUID = -3122257732635068779L;

    String name ;
    String profileUrl;
    String token;
    String secret;
    String userId;

    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
