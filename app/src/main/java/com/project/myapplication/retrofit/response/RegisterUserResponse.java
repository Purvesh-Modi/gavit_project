package com.project.myapplication.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisterUserResponse {
    @SerializedName("result")
    @Expose
    private List<UserDataResponse> userList;
}
