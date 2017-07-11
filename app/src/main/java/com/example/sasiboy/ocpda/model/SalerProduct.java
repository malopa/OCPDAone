package com.example.sasiboy.ocpda.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by malopa on 7/11/2017.
 */
public class SalerProduct {

    @SerializedName("data")
    private List<ProductList> data;

    public List<ProductList> getData() {
        return data;
    }
}
