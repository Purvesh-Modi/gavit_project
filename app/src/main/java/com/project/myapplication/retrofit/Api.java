package com.project.myapplication.retrofit;

import com.project.myapplication.retrofit.request.CreateCFOUserRequest;
import com.project.myapplication.retrofit.response.UserDataResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface Api {
    @POST("cfo/create")
    Call<UserDataResponse> registerCFOUser(@Body CreateCFOUserRequest userRequest);

    @GET("cfo/view")
    Call<UserDataResponse> getCFOUserById(@Query("id") int cfoUserId);

    @PUT("cfo/update")
    Call<UserDataResponse> updateCFOUserById(@Body CreateCFOUserRequest userRequest);

    @GET("cfo/index")
    Call<List<UserDataResponse>> getAllCFOUsers();

    @POST("user/create")
    Call<UserDataResponse> registerUser(@Body CreateCFOUserRequest userRequest);

    @GET("user/view")
    Call<UserDataResponse> getUserById(@Query("id") int cfoUserId);

    @PUT("user/update")
    Call<UserDataResponse> updateUserById(@Body CreateCFOUserRequest userRequest);

    @GET("user/index")
    Call<List<UserDataResponse>> getAllUsers();
}
