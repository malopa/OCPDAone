package com.example.sasiboy.ocpda.config;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sasiboy on 6/15/2017.
 */

public class ServiceGenerator {
    static private String API_BASE_URL= "http://192.168.137.1/ocpda/public/";


    static private Retrofit.Builder builder  = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    static private Retrofit retrofit = builder.build();

    static private OkHttpClient.Builder client = new OkHttpClient.Builder();

    static private HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    public static <S> S createService(
            Class<S> serviceClass
    )
    {
        if(!client.interceptors().contains(logging)){
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            client.addInterceptor(logging);
            builder.client(client.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }
}
