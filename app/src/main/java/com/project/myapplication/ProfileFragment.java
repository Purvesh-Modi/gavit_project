package com.project.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.myapplication.retrofit.RetrofitClient;
import com.project.myapplication.retrofit.response.UserDataResponse;
import com.project.myapplication.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    View view;
    private TextView mTvUserId;
    private TextView mTvFirstName;
    private TextView mTvLastName;
    private TextView mTvYearsOfExp;
    private TextView mTvEmail;
    private TextView mTvPhone;

    private ProgressDialog mProgress;

    public ProfileFragment() {

    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgress = new ProgressDialog(getContext());
        initView(view);
        fireViewAPICall();
    }

    private void fireViewAPICall() {
        try {
            int currentUserId = PreferenceManager
                    .getDefaultSharedPreferences(getContext()).getInt(Constants.KEY_USER_ID, 0);
            Call<UserDataResponse> viewProfileCall = RetrofitClient.getInstance().getApi()
                    .getUserById(currentUserId);
            mProgress.show();
            viewProfileCall.enqueue(new Callback<UserDataResponse>() {
                @Override
                public void onResponse(Call<UserDataResponse> call,
                                       Response<UserDataResponse> response) {
                    mProgress.cancel();
                    if (response.body() != null) {
                        bindUI(response.body());
                    }
                }

                @Override
                public void onFailure(Call<UserDataResponse> call, Throwable t) {
                    mProgress.cancel();
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            mProgress.cancel();
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void bindUI(UserDataResponse userData) {
        mTvUserId.setText(String.valueOf(userData.getCfoId()));
        mTvFirstName.setText(userData.getCfoFname());
        mTvLastName.setText(userData.getCfoLname());
        mTvYearsOfExp.setText(userData.getCfoYears_of_exp());
        mTvEmail.setText(userData.getCfoEmail());
        mTvPhone.setText(String.valueOf(userData.getCfoPhone()));
    }

    private void initView(View view) {
        mTvUserId = view.findViewById(R.id.tv_user_id);
        mTvFirstName = view.findViewById(R.id.tv_first_name);
        mTvLastName = view.findViewById(R.id.tv_last_name);
        mTvYearsOfExp = view.findViewById(R.id.tv_years_of_exp);
        mTvEmail = view.findViewById(R.id.tv_email);
        mTvPhone = view.findViewById(R.id.tv_phone);
    }
}
