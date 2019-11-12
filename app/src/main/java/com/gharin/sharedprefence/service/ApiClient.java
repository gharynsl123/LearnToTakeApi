package com.gharin.sharedprefence.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit getClient() {

        return new Retrofit.Builder()
                .baseUrl("https://time.siswadi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }


    public static BaseApiService getJadwal(){
        return getClient().create(BaseApiService.class);
    }
}
