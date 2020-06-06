package com.example.tlocal_linux.ApiServices;

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
    final static String URL_API = "http://192.168.1.67:3000/";

    @GET("local/categories/{id}")
    Call<List<listItemCategory>> categoriesArrays(@Path("id") String category);

    @GET("local/info/{idlocal}")
    Call<localInfo> getInfoLocal(@Path("idlocal") String local);

    @POST("local/register")
    Call<localInfo> sendLocal(@Body localInfo localdata);

    @POST("upload")
    Call<ImageModel> uploadImage(@Body ImageModel Imagen);

    @GET("local/infoAll")
    Call<List<localInfo>> loadAllInfo();
}
