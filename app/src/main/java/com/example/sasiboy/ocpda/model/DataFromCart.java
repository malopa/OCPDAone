package com.example.sasiboy.ocpda.model;

import android.content.Context;

import com.example.sasiboy.ocpda.UserViewActivity;
import com.example.sasiboy.ocpda.adapter.DataInCart;

import java.util.List;

public class DataFromCart {

    public static DataFromCart mIstance=null;
    private static Context _context;

    private List<Integer>  price ;
    private List<String>   image_url;

    private DataFromCart(Context context){
        _context = context;
    }

    public DataFromCart(List<Integer> price, List<String> image_url) {
        this.price = price;
        this.image_url = image_url;
    }



    public static DataFromCart getmIstance(Context context) {
        if (mIstance == null)
            mIstance = new DataFromCart(context);
        return mIstance;
    }

    public List<Integer> getPrice() {
        return price;
    }

    public void setPrice(List<Integer> price) {
        this.price = price;
    }

    public List<String> getImage_url() {
        return image_url;
    }

    public void setImage_url(List<String> image_url) {
        this.image_url = image_url;
    }
}
