package com.example.sasiboy.ocpda.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by malopa on 6/19/2017.
 */
public class DataR {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("price")
    @Expose
    private int price;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;


    public DataR(int id,int price,String name,String image,String description){
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;

    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
