package com.gharin.sharedprefence.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BaseApiService {

    @GET("pray/{cityName}")
    Call<ResponseBody> getJadwalShalat(@Path("cityName")String cityName);
}
