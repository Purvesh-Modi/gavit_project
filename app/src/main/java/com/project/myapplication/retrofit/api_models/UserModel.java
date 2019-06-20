package com.project.myapplication.retrofit.api_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("user_fname")
    @Expose
    private String  userFname;
    @SerializedName("user_lname")
    @Expose
    private String  userLname;
    @SerializedName("user_phone")
    @Expose
    private Long userPhone;
    @SerializedName("user_email")
    @Expose
    private String  userEmail;
    @SerializedName("user_password")
    @Expose
    private String  userPassword;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserFname() {
        return userFname;
    }

    public void setUserFname(String userFname) {
        this.userFname = userFname;
    }

    public String getUserLname() {
        return userLname;
    }

    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}
