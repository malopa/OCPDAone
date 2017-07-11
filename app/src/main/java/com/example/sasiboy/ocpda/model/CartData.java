package com.example.sasiboy.ocpda.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by malopa on 6/30/2017.
 */
public class CartData {

    @SerializedName("image")
    private String image;

    @SerializedName("price")
    private int price;


    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }
}
