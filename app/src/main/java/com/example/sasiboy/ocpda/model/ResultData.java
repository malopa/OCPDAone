package com.example.sasiboy.ocpda.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by malopa on 6/19/2017.
 */

public class ResultData {

    @SerializedName("data")
    @Expose
    private List<DataR> data;

    public List<DataR> getData() {
        return data;
    }
}
