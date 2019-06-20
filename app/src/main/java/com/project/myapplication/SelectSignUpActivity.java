package com.project.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SelectSignUpActivity extends AppCompatActivity implements View.OnClickListener {

    Button Signupuser, signupcfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_signup);

        Signupuser = findViewById(R.id.Signupuser);
        signupcfo = findViewById(R.id.Signupcfo);

        Signupuser.setOnClickListener(this);
        signupcfo.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Signupuser:
                mSignUpUser();
                break;

            case R.id.Signupcfo:
                mSignUpCfo();
                break;
        }
    }

    private void mSignUpUser() {
        finish();
        startActivity(new Intent(this, UserSignUpActivity.class));
    }

    private void mSignUpCfo(){
        finish();
        startActivity(new Intent(this, CfoSignUpActivity.class));
    }
}
