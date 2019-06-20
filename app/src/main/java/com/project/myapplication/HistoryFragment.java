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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment {

    public static final String         TAG = "HistoryFragment";
    private             View           view;
    private             HistoryAdapter mAdapter;
    private             RecyclerView   mRvDataList;

    public HistoryFragment() {

    }

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);
        mAdapter = new HistoryAdapter();
        mRvDataList = view.findViewById(R.id.rv_data_list);
        mRvDataList.setAdapter(mAdapter);

        mRvDataList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Call<List<CFOUserModel>> userListCall = RetrofitClient.getInstance().getApi().getAllCFOUsers();

            userListCall.enqueue(new Callback<List<CFOUserModel>>() {
                @Override
                public void onResponse(Call<List<CFOUserModel>> call, Response<List<CFOUserModel>> response) {
                    if (response.body() != null) {
                        mAdapter.setCFOData(response.body());
                    }else {
                        Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<CFOUserModel>> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
