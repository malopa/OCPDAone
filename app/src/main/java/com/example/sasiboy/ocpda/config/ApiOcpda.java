package com.example.sasiboy.ocpda.config;

import com.example.sasiboy.ocpda.model.LoginResponse;
import com.example.sasiboy.ocpda.model.ResponseCart;
import com.example.sasiboy.ocpda.model.ResponseResult;
import com.example.sasiboy.ocpda.model.ResultData;
import com.example.sasiboy.ocpda.model.SalerProduct;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

import static android.R.attr.path;


public interface ApiOcpda {
    @Multipart
    @POST("upload.php")
    Call<ResponseResult> uploadProfile(
            @Part("picName") RequestBody picName,
            @Part("price") RequestBody price,
            @Part("description") RequestBody description,
            @Part("id") RequestBody id,
            @Part MultipartBody.Part photo

            );

    @GET("retrieve.php")
    Call<ResultData> retriveData();

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
            @Field("username") String username,
            @Field("password") String  password
    );

    @FormUrlEncoded
    @POST("Registration.php")
    Call<LoginResponse> register(
            @Field("firstname") String fname,
            @Field("lastname") String lname,
            @Field("username") String username,
            @Field("phone") String phone,
            @Field("password") String  password,
            @Field("location") String location,
            @Field("status") String  status
    );

    @FormUrlEncoded
    @POST("getcartdata.php")
    Call<ResponseCart>  cartData(@Field("cart_data")List<Integer> cart_data);


    @GET("product.php/{id}")
    Call<SalerProduct> getProduct(@Path("id") int id);

}
