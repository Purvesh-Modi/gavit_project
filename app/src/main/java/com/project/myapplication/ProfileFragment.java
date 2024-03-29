package com.project.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.Toast;
import com.project.myapplication.retrofit.RetrofitClient;
import com.project.myapplication.retrofit.api_models.CFOUserModel;
import com.project.myapplication.retrofit.api_models.UserModel;
import com.project.myapplication.utils.Constants;
import retrofit2.*;

public class ProfileFragment
  extends Fragment {

  public static final String TAG = "ProfileFragment";
  View view;
  private TextInputEditText mTvUserId;
  private TextInputEditText mTvName;
  private TextInputEditText mTvYearsOfExp;
  private TextInputEditText mTvQualification;
  private TextInputEditText mTvEmail;
  private TextInputEditText mTvPhone;

  private ProgressDialog mProgress;

  public ProfileFragment() {

  }

  public static ProfileFragment newInstance() {
    return new ProfileFragment();
  }

  @Nullable
  @Override
  public View onCreateView(
    @NonNull LayoutInflater inflater, @Nullable ViewGroup container,
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

  private void initView(View view) {
    mTvUserId = view.findViewById(R.id.tv_user_id);
    mTvName = view.findViewById(R.id.tv_name);
    mTvYearsOfExp = view.findViewById(R.id.tv_years_of_exp);
    mTvQualification = view.findViewById(R.id.tv_qualification);
    mTvEmail = view.findViewById(R.id.tv_email);
    mTvPhone = view.findViewById(R.id.tv_phone);
  }

  private void fireViewAPICall() {
    int currentUserId = PreferenceManager
      .getDefaultSharedPreferences(getContext()).getInt(Constants.KEY_USER_ID, 0);
    if (BaseConfiguration.isCurrentUserIsCFO) {
      try {
        Call<CFOUserModel> viewProfileCall = RetrofitClient.getInstance().getApi()
                                                           .getCFOUserById(currentUserId);
        mProgress.show();
        viewProfileCall.enqueue(new Callback<CFOUserModel>() {
          @Override
          public void onResponse(
            Call<CFOUserModel> call,
            Response<CFOUserModel> response) {
            mProgress.cancel();
            if (response.body() != null) {
              bindUI(response.body());
            }
          }

          @Override
          public void onFailure(Call<CFOUserModel> call, Throwable t) {
            mProgress.cancel();
            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
          }
        });
      } catch (Exception ex) {
        Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        mProgress.cancel();
      }
    } else {
      try {
        Call<UserModel> viewProfileCall = RetrofitClient.getInstance().getApi()
                                                        .getUserById(currentUserId);
        mProgress.show();
        viewProfileCall.enqueue(new Callback<UserModel>() {
          @Override
          public void onResponse(
            Call<UserModel> call,
            Response<UserModel> response) {
            mProgress.cancel();
            if (response.body() != null) {
              bindUI(response.body());
            }
          }

          @Override
          public void onFailure(Call<UserModel> call, Throwable t) {
            mProgress.cancel();
            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
          }
        });
      } catch (Exception ex) {
        Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        mProgress.cancel();
      }
    }
  }

  private void bindUI(CFOUserModel userData) {
    mTvUserId.setText(String.valueOf(userData.getCfoId()));
    mTvName.setText(String.format("%s %s", userData.getCfoFname(), userData.getCfoLname()));
    mTvYearsOfExp.setText(userData.getCfoYears_of_exp());
    mTvQualification.setText(userData.getCfoQualification());
    mTvEmail.setText(userData.getCfoEmail());
    mTvPhone.setText(String.valueOf(userData.getCfoPhone()));
  }

  private void bindUI(UserModel userData) {
    mTvUserId.setText(String.valueOf(userData.getUserId()));
    mTvName.setText(String.format("%s %s", userData.getUserFname(), userData.getUserLname()));
    mTvYearsOfExp.setText("Not Applicable");
    mTvQualification.setText("Not Applicable");
    mTvEmail.setText(userData.getUserEmail());
    mTvPhone.setText(String.valueOf(userData.getUserPhone()));
  }
}
