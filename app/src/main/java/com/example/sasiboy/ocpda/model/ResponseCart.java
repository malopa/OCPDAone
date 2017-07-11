package com.example.sasiboy.ocpda.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by malopa on 6/30/2017.
 */
public class ResponseCart {
    @SerializedName("data_in_cart")
    private List<CartData> data_in_cart;

    public List<CartData> getData_in_cart() {
        return data_in_cart;
    }
}
