package com.project.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.myapplication.retrofit.RetrofitClient;
import com.project.myapplication.retrofit.request.CreateCFOUserRequest;
import com.project.myapplication.retrofit.response.UserDataResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CfoSignUpActivity extends AppCompatActivity implements View.OnClickListener {

    Button   btn_register;
    EditText cfo_fname, cfo_lname, cfo_email, cfo_phn, cfo_qualification, cfo_exprience, cfo_password, cfo_cfm_password;
    View           view;
    Snackbar       snackbar;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        view = findViewById(android.R.id.content);

        mProgressDialog = new ProgressDialog(this);

        cfo_fname = findViewById(R.id.cfo_fname);
        cfo_lname = findViewById(R.id.cfo_lname);
        cfo_email = findViewById(R.id.cfo_email);
        cfo_phn = findViewById(R.id.cfo_phn);
        cfo_qualification = findViewById(R.id.cfo_qualification);
        cfo_exprience = findViewById(R.id.cfo_years_of_exp);
        cfo_password = findViewById(R.id.cfo_password);
        cfo_cfm_password = findViewById(R.id.cfo_cfm_password);

        btn_register = findViewById(R.id.cfo_register);
        btn_register.setOnClickListener(this);
    }

    // Validation for this activity
    private boolean validateForm() {
        return (validateFirstName() && validateLastName() && validateEmail() && validatePhoneNumber() && validateQualification() && validateYearsOfExprience() && validatePassword() && validateConfirmPassword());
    }

    // validating first name
    private boolean validateFirstName() {
        if (TextUtils.isEmpty(cfo_fname.getText().toString().trim())) {
            snackbar = Snackbar
                    .make(view, "First Name Required ", Snackbar.LENGTH_LONG);
            snackbar.show();
            requestFocus(cfo_fname);
            return false;
        }
        return true;
    }

    //  validating last name
    private boolean validateLastName() {
        if (cfo_lname.getText().toString().trim().isEmpty()) {
            snackbar = Snackbar
                    .make(view, "First last Required ", Snackbar.LENGTH_LONG);
            snackbar.show();
            requestFocus(cfo_lname);
            return false;
        }
        return true;
    }

    //  validating email
    private boolean validateEmail() {
        if (cfo_email.getText().toString().trim().isEmpty()) {
            snackbar = Snackbar
                    .make(view, "Email Required ", Snackbar.LENGTH_LONG);
            snackbar.show();
            requestFocus(cfo_email);
            return false;
        }
        return true;
    }

    //  validating phone number
    private boolean validatePhoneNumber() {
        if (cfo_phn.getText().toString().trim().isEmpty()) {
            snackbar = Snackbar
                    .make(view, "Phone number Required ", Snackbar.LENGTH_LONG);
            snackbar.show();
            requestFocus(cfo_phn);
            return false;
        }
        return true;
    }

    //  validating Qualification
    private boolean validateQualification() {
        if (cfo_qualification.getText().toString().trim().isEmpty()) {
            snackbar = Snackbar
                    .make(view, "Qualification Required ", Snackbar.LENGTH_LONG);
            snackbar.show();
            requestFocus(cfo_qualification);
            return false;
        }
        return true;
    }

    //  validating Years of experience
    private boolean validateYearsOfExprience() {
        if (cfo_exprience.getText().toString().trim().isEmpty()) {
            snackbar = Snackbar
                    .make(view, "Years of experience Required ", Snackbar.LENGTH_LONG);
            snackbar.show();
            requestFocus(cfo_exprience);
            return false;
        }
        return true;
    }

    //  validating mPassword
    private boolean validatePassword() {
        if (cfo_password.getText().toString().trim().isEmpty()) {
            snackbar = Snackbar
                    .make(view, "Password Required ", Snackbar.LENGTH_LONG);
            snackbar.show();
            requestFocus(cfo_password);
            return false;
        }
        return true;
    }

    // validating confirm mPassword
    private boolean validateConfirmPassword() {
        if (cfo_cfm_password.getText().toString().trim().equals(cfo_password.getText().toString().trim())){
            snackbar = Snackbar
                    .make(view, "Password does not match", Snackbar.LENGTH_LONG);
            snackbar.show();
            requestFocus(cfo_cfm_password);
            return false;
        }
        return true;
    }

    // focusing cursor based on EditText
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cfo_register:
                mRegisterCfo();
                break;
        }
    }

    private void mRegisterCfo() {
        if (validateForm()) {
            mProgressDialog.show();
            fireSignUpAPICall();
        }
    }

    private void fireSignUpAPICall() {

        CreateCFOUserRequest request = new CreateCFOUserRequest();

        request.setCfoEmail(cfo_email.getText().toString().trim());
        request.setCfoPassword(cfo_cfm_password.getText().toString().trim());
        request.setCfoFname(cfo_fname.getText().toString().trim());
        request.setCfoLname(cfo_lname.getText().toString().trim());
        request.setCfoQualification(cfo_qualification.getText().toString().trim());
        request.setCfoYears_of_exp(cfo_exprience.getText().toString().trim());
        request.setCfoPhone(Long.valueOf(cfo_phn.getText().toString().trim()));

        try {
            Call<UserDataResponse> requestCreateCall = RetrofitClient.getInstance().getApi().registerCFOUser(request);
            requestCreateCall.enqueue(new Callback<UserDataResponse>() {
                @Override
                public void onResponse(Call<UserDataResponse> call, Response<UserDataResponse> response) {
                    finish();
                    startActivity(new Intent(CfoSignUpActivity.this, MainActivity.class));
                    Toast.makeText(CfoSignUpActivity.this, "User created successfully",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<UserDataResponse> call, Throwable t) {
                    Toast.makeText(CfoSignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            Toast.makeText(CfoSignUpActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, SelectSignUpActivity.class));
    }
}
