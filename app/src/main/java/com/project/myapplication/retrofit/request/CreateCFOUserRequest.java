package com.project.myapplication.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateCFOUserRequest {

    @SerializedName("cfo_id")
    @Expose
    private Integer cfoId;
    @SerializedName("cfo_fname")
    @Expose
    private String  cfoFname;
    @SerializedName("cfo_lname")
    @Expose
    private String  cfoLname;
    @SerializedName("cfo_phone")
    @Expose
    private Long cfoPhone;
    @SerializedName("cfo_email")
    @Expose
    private String  cfoEmail;
    @SerializedName("cfo_password")
    @Expose
    private String  cfoPassword;
    @SerializedName("qualification")
    @Expose
    private String  cfoQualification;
    @SerializedName("years_of_exp")
    @Expose
    private String  cfoYears_of_exp;
    @SerializedName("rating")
    @Expose
    private String  cfoRating;

    public Integer getCfoId() {
        return cfoId;
    }

    public void setCfoId(Integer cfoId) {
        this.cfoId = cfoId;
    }

    public String getCfoFname() {
        return cfoFname;
    }

    public void setCfoFname(String cfoFname) {
        this.cfoFname = cfoFname;
    }

    public String getCfoLname() {
        return cfoLname;
    }

    public void setCfoLname(String cfoLname) {
        this.cfoLname = cfoLname;
    }

    public Long getCfoPhone() {
        return cfoPhone;
    }

    public void setCfoPhone(Long cfoPhone) {
        this.cfoPhone = cfoPhone;
    }

    public String getCfoEmail() {
        return cfoEmail;
    }

    public void setCfoEmail(String cfoEmail) {
        this.cfoEmail = cfoEmail;
    }

    public String getCfoPassword() {
        return cfoPassword;
    }

    public void setCfoPassword(String cfoPassword) {
        this.cfoPassword = cfoPassword;
    }

    public String getCfoQualification() {
        return cfoQualification;
    }

    public void setCfoQualification(String cfoQualification) {
        this.cfoQualification = cfoQualification;
    }

    public String getCfoYears_of_exp() {
        return cfoYears_of_exp;
    }

    public void setCfoYears_of_exp(String cfoYears_of_exp) {
        this.cfoYears_of_exp = cfoYears_of_exp;
    }

    public String getCfoRating() {
        return cfoRating;
    }

    public void setCfoRating(String cfoRating) {
        this.cfoRating = cfoRating;
    }
}
