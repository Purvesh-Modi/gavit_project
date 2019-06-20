package com.project.myapplication.retrofit;

import com.project.myapplication.BaseConfiguration;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient mInstance;
    private        Retrofit       retrofit;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BaseConfiguration.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                //.client(httpClient.build())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
