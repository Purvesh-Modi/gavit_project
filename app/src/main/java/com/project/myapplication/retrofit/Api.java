package com.project.myapplication.retrofit;

import com.project.myapplication.retrofit.api_models.CFOUserModel;
import com.project.myapplication.retrofit.api_models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface Api {

    //CFO User APIs
    @POST("cfo/create")
    Call<CFOUserModel> registerCFOUser(@Body CFOUserModel userRequest);

    @GET("cfo/view")
    Call<CFOUserModel> getCFOUserById(@Query("id") int cfoUserId);

    @PUT("cfo/update")
    Call<CFOUserModel> updateCFOUserById(@Body CFOUserModel userRequest);

    @GET("cfo/index")
    Call<List<CFOUserModel>> getAllCFOUsers();

    //User APIs
    @POST("user/create")
    Call<UserModel> registerUser(@Body UserModel userModel);

    @GET("user/view")
    Call<UserModel> getUserById(@Query("id") int userId);

    @PUT("user/update")
    Call<UserModel> updateUserById(@Body UserModel userModel);

    @GET("user/index")
    Call<List<UserModel>> getAllUsers();
}
