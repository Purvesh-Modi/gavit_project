package com.project.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.myapplication.retrofit.RetrofitClient;
import com.project.myapplication.retrofit.api_models.CFOUserModel;
import com.project.myapplication.retrofit.api_models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment {

    public static final String TAG = "ListFragment";
    View view;
    private ListUserAdapter mAdapter;
    private RecyclerView    mRvUserList;

    public ListFragment() {

    }

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        mAdapter = new ListUserAdapter();
        mRvUserList = view.findViewById(R.id.rv_users);
        mRvUserList.setAdapter(mAdapter);

        mRvUserList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllUsers();
    }

    private void getAllUsers() {
        try {
            Call<List<UserModel>> userListCall = RetrofitClient.getInstance().getApi().getAllUsers();

            userListCall.enqueue(new Callback<List<UserModel>>() {
                @Override
                public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                    if (response.body() != null) {
                        mAdapter.setUserData(response.body());
                    } else {
                        Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<UserModel>> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
