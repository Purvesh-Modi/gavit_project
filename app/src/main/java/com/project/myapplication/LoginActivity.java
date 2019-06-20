package com.project.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.myapplication.retrofit.RetrofitClient;
import com.project.myapplication.retrofit.response.UserDataResponse;
import com.project.myapplication.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button login, signup;
    EditText username, mPassword;
    Snackbar       snackbar;
    View           view;
    ProgressDialog mProgressDialog;
    private int mCurrentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        view = findViewById(android.R.id.content);

        int getCfoID =
                PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                        .getInt(Constants.KEY_USER_ID, 0);

        if (getCfoID != 0){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }

        username = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        mProgressDialog = new ProgressDialog(this);

        login.setOnClickListener(this);
        signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                mLogin();
                break;

            case R.id.signup:
                mSignUp();
                break;
        }
    }

    // Validation for this activity
    private boolean validateForm() {
        return (validateEmail() && validatePassword());
    }

    // validating Email ID
    private boolean validateEmail() {
        if (TextUtils.isEmpty(username.getText().toString().trim())) {
            snackbar = Snackbar
                    .make(view, "Required Email ID", Snackbar.LENGTH_LONG);
            snackbar.show();
            requestFocus(username);
            mProgressDialog.dismiss();
            return false;
        }
        return true;
    }

    //  validating mPassword
    private boolean validatePassword() {
        if (mPassword.getText().toString().trim().isEmpty()) {
            snackbar = Snackbar
                    .make(view, "Required Password", Snackbar.LENGTH_LONG);
            snackbar.show();
            requestFocus(mPassword);
            mProgressDialog.dismiss();
            return false;
        }
        return true;
    }

    // focusing cursor based on EditText
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    // for signup
    private void mSignUp() {
        finish();
        startActivity(new Intent(this, SelectSignUpActivity.class));
    }

    // for login
    private void mLogin() {
        if (!validateForm()) {
            return;
        }

        final String email    = username.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();

        try {
            Call<List<UserDataResponse>> userListCall = RetrofitClient.getInstance().getApi()
                    .getAllCFOUsers();

            userListCall.enqueue(new Callback<List<UserDataResponse>>() {
                @Override
                public void onResponse(Call<List<UserDataResponse>> call,
                                       Response<List<UserDataResponse>> response) {
                    if (response.body() != null) {
                        if (checkUserLogin(email, password, response.body())) {
                            PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                                    .edit().putInt(Constants.KEY_USER_ID, mCurrentUserId).commit();
                            Constants.IS_LOGGEDIN = true;
                            finish();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this,
                                    "Email ID or password does not match", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "No Data Found", Toast.LENGTH_SHORT)
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<List<UserDataResponse>> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkUserLogin(String email, String password,
                                   List<UserDataResponse> availableUsers) {
        for (UserDataResponse user : availableUsers) {
            if (user.getCfoEmail().equals(email) && user.getCfoPassword().equals(password)) {
                mCurrentUserId = user.getCfoId();
                return true;
            }
        }
        return false;
    }

    //when user press back button
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you really wanna exit ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
