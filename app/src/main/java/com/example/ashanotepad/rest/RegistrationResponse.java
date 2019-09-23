package com.example.ashanotepad.rest;

import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {
    @SerializedName("message")private String message;
    @SerializedName("user_id")private int id;
    @SerializedName("auth_token")private String token;

    public RegistrationResponse(String message, int id, String token) {
        this.message = message;
        this.id = id;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
