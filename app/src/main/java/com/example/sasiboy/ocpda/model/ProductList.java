package com.example.sasiboy.ocpda.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by malopa on 7/11/2017.
 */

public class ProductList {

    @SerializedName("image")
    private String image;

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private int price;

    @SerializedName("description")
    private String description;


    public ProductList(String image, String name, int price, String description) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
