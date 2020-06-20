package com.example.tlocal_linux.ApiServices;

import android.util.Log;

import com.example.tlocal_linux.MODELS.ImageModel;
import com.example.tlocal_linux.MODELS.listItemCategory;
import com.example.tlocal_linux.MODELS.localInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiLocal {
    final static String URL_API = "http://192.168.1.74:5000/";

    @GET("local/categ/{id}")
    Call<List<listItemCategory>> categoriesArrays(@Path("id") String category);

    @GET("local/{idlocal}")
    Call<localInfo> getInfoLocal(@Path("idlocal") String local);

    @POST("local")
    Call<localInfo> sendLocal(@Body localInfo localdata);


    @POST("upload")
    Call<ImageModel> uploadImage(@Body ImageModel Imagen);

    @GET("local")
    Call<List<localInfo>> loadAllInfo();
}
