package com.example.sasiboy.ocpda.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by malopa on 6/28/2017.
 */
public class LoginResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("user")
    private User user;

    @SerializedName("message")
    private  String message;

    @SerializedName("registered_user")
    private User registered_user;

    public User getUser() {
        return user;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getRegistered_user() {
        return registered_user;
    }
}
