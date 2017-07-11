package com.example.sasiboy.ocpda.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sasiboy on 6/15/2017.
 */

public class ResponseResult {

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }
}
