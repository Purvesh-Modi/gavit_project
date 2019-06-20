package com.project.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.myapplication.retrofit.RetrofitClient;
import com.project.myapplication.retrofit.api_models.CFOUserModel;
import com.project.myapplication.retrofit.api_models.UserModel;
import com.project.myapplication.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnUserLogin, btnCfoLogin, signup;
    private TextInputEditText username, mPassword;
    private Snackbar       snackbar;
    private View           view;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        view = findViewById(android.R.id.content);

        int getCfoID =
                PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                        .getInt(Constants.KEY_USER_ID, 0);

        if (getCfoID != 0) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        username = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        btnUserLogin = findViewById(R.id.loginAsUser);
        btnCfoLogin = findViewById(R.id.loginAsCfo);
        signup = findViewById(R.id.signup);

        mProgressDialog = new ProgressDialog(this);

        btnUserLogin.setOnClickListener(this);
        btnCfoLogin.setOnClickListener(this);
        signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginAsUser:
                mUserLogin();
                break;

            case R.id.signup:
                mSignUp();
                break;

            case R.id.loginAsCfo:
                mCfoLogin();
                break;
        }
    }

    private void mCfoLogin() {
        if (!validateForm()) {
            return;
        }

        final String email    = username.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();

        try {
            Call<List<CFOUserModel>> userListCall = RetrofitClient.getInstance().getApi()
                    .getAllCFOUsers();

            userListCall.enqueue(new Callback<List<CFOUserModel>>() {
                @Override
                public void onResponse(Call<List<CFOUserModel>> call,
                                       Response<List<CFOUserModel>> response) {
                    if (response.body() != null) {
                        CFOUserModel currentUser = new CFOUserModel();
                        if (checkCFOUserLogin(email, password, response.body(), currentUser)) {
                            PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                                    .edit().putInt(Constants.KEY_USER_ID, currentUser.getCfoId()).commit();
                            PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                                    .edit().putString(Constants.KEY_USER_NAME, currentUser.getCfoFname() + " " + currentUser.getCfoLname()).commit();
                            PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                                    .edit().putString(Constants.KEY_USER_EMAIL, currentUser.getCfoEmail()).commit();
                            Constants.IS_LOGGED_IN = true;
                            BaseConfiguration.isCurrentUserIsCFO = true;
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<List<CFOUserModel>> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
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

    // for btnUserLogin
    private void mUserLogin() {
        if (!validateForm()) {
            return;
        }

        final String email    = username.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();

        try {
            Call<List<UserModel>> userListCall = RetrofitClient.getInstance().getApi()
                    .getAllUsers();

            userListCall.enqueue(new Callback<List<UserModel>>() {
                @Override
                public void onResponse(Call<List<UserModel>> call,
                                       Response<List<UserModel>> response) {
                    if (response.body() != null) {
                        UserModel currentUser = new UserModel();
                        if (checkUserLogin(email, password, response.body(), currentUser)) {
                            PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                                    .edit().putInt(Constants.KEY_USER_ID, currentUser.getUserId()).commit();
                            PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                                    .edit().putString(Constants.KEY_USER_NAME, currentUser.getUserFname() + " " + currentUser.getUserLname()).commit();
                            PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                                    .edit().putString(Constants.KEY_USER_EMAIL, currentUser.getUserEmail()).commit();
                            Constants.IS_LOGGED_IN = true;
                            BaseConfiguration.isCurrentUserIsCFO = false;
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<List<UserModel>> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkCFOUserLogin(String email, String password,
                                      List<CFOUserModel> availableUsers,
                                      CFOUserModel currentUserOut) {
        for (CFOUserModel user : availableUsers) {
            if (user.getCfoEmail().equals(email) && user.getCfoPassword().equals(password)) {
                currentUserOut.setCfoFname(user.getCfoFname());
                currentUserOut.setCfoLname(user.getCfoLname());
                currentUserOut.setCfoId(user.getCfoId());
                currentUserOut.setCfoEmail(user.getCfoEmail());
                return true;
            }
        }
        return false;
    }

    private boolean checkUserLogin(String email, String password,
                                   List<UserModel> availableUsers, UserModel currentUserOut) {
        for (UserModel user : availableUsers) {
            if (user.getUserEmail().equals(email) && user.getUserPassword().equals(password)) {
                currentUserOut.setUserFname(user.getUserFname());
                currentUserOut.setUserLname(user.getUserLname());
                currentUserOut.setUserId(user.getUserId());
                currentUserOut.setUserEmail(user.getUserEmail());
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
