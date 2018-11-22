package com.example.zeeshan.supermario.news.service;

import com.example.zeeshan.supermario.model.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface NewsfeedService {
    @GET("posts")
    Call<List<ResponseModel>> getNewsfeed(@Query("tags") String tag);

    @GET
    Call<ResponseModel> getImageUrl(@Url String url);
}
