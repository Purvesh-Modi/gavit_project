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

public class UserSignUpActivity extends AppCompatActivity implements View.OnClickListener {

    Button usernext;
    EditText firstName, lastName, email, phoneNumber, password, confirmPassword;
    ProgressDialog progressDialog;
    View view;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_user);

        view = findViewById(android.R.id.content);

        firstName = findViewById(R.id.fname);
        lastName = findViewById(R.id.lname);
        email = findViewById(R.id.Email);
        phoneNumber = findViewById(R.id.phn);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.cfm_password);

        usernext = findViewById(R.id.userNext);

        progressDialog = new ProgressDialog(this);

        usernext.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, SelectSignUpActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.userNext:
                mRegisterUser();
                break;
        }
    }

    private void mRegisterUser() {

        if (validateForm()){

            firstName.getText().toString().trim();
            lastName.getText().toString().trim();
            email.getText().toString().trim();
            phoneNumber.getText().toString().trim();
            password.getText().toString().trim();
            confirmPassword.getText().toString().trim();

            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private boolean validateForm() {
        return (validateFName() && validateLName() && validateEmail() && validateEmailMatcher() && validateMobile() && validateMobileDigit() && validatePassword() && validateConfirmPassword());
    }

    private boolean validateFName() {
        if (firstName.getText().toString().trim().isEmpty()) {
            snackbar = Snackbar
                    .make(view, "Required First Name", Snackbar.LENGTH_LONG);
            snackbar.show();
            requestFocus(firstName);
            return false;
        }
        return true;
    }

    private boolean validateLName() {
        if (lastName.getText().toString().trim().isEmpty()) {
            snackbar = Snackbar
                    .make(view, "Required Last Name", Snackbar.LENGTH_LONG);
            snackbar.show();
            requestFocus(lastName);
            return false;
        }
        return true;
    }



    private boolean validateEmail() {
        if (TextUtils.isEmpty(email.getText().toString().trim())){
            snackbar = Snackbar
                    .make(view, "Required Email ID", Snackbar.LENGTH_LONG);
            snackbar.show();
            requestFocus(email);
            return false;
        }
        return true;
    }

    private boolean validateEmailMatcher() {
        if (email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+")){
            snackbar = Snackbar
                    .make(view, "Please Enter Valid EmailID", Snackbar.LENGTH_LONG);
            snackbar.show();
            requestFocus(email);
            return false;
        }
        return true;
    }

    private boolean validateMobile() {
        if (TextUtils.isEmpty(phoneNumber.getText().toString().trim())) {
            snackbar = Snackbar
                    .make(view, "Required Mobile no", Snackbar.LENGTH_LONG);
            snackbar.show();
            requestFocus(phoneNumber);
            return false;
        }
        return true;
    }

    private boolean validateMobileDigit() {
        if (phoneNumber.length() < 10) {
            snackbar = Snackbar
                    .make(view, "Enter 10 Digit mobile no", Snackbar.LENGTH_LONG);
            snackbar.show();
            requestFocus(phoneNumber);
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        if (password.getText().toString().trim().isEmpty()) {
            snackbar = Snackbar
                    .make(view, "Required Password", Snackbar.LENGTH_LONG);
            snackbar.show();
            requestFocus(password);
            return false;
        }
        return true;
    }

    private boolean validateConfirmPassword() {
        if (confirmPassword.getText().toString().trim().isEmpty()) {
            snackbar = Snackbar
                    .make(view, "Password does not match ", Snackbar.LENGTH_LONG);
            snackbar.show();
            requestFocus(confirmPassword);
            return false;
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
