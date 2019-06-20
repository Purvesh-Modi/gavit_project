package com.project.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.myapplication.retrofit.response.UserDataResponse;

import java.util.ArrayList;
import java.util.List;

class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyHistoryViewHolder> {

    private List<UserDataResponse> mDataList = new ArrayList<>();

    @NonNull
    @Override
    public MyHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyHistoryViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHistoryViewHolder myHistoryViewHolder, int i) {
        myHistoryViewHolder.bindData(i);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    void setCFOData(List<UserDataResponse> dataList){
        mDataList.clear();
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    class MyHistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvProjectId;
        private TextView mTvServiceName;
        private TextView mTvUserName;
        private TextView mTvCFOName;
        private TextView mTvDuration;
        private TextView mTvAmount;
        private int mPosition;
        private UserDataResponse mData;

        MyHistoryViewHolder(@NonNull ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_history_fragment, parent, false));
            initView(itemView);
        }

        private void initView(View convertView) {
            mTvProjectId = convertView.findViewById(R.id.tv_project_id);
            mTvServiceName = convertView.findViewById(R.id.tv_service_name);
            mTvUserName = convertView.findViewById(R.id.tv_user_name);
            mTvCFOName = convertView.findViewById(R.id.tv_CFO_name);
            mTvDuration = convertView.findViewById(R.id.tv_duration);
            mTvAmount = convertView.findViewById(R.id.tv_amount);
        }

        private void bindData(int position){
            mPosition = position;
            mData = mDataList.get(mPosition);

            mTvProjectId.setText(String.valueOf(mPosition + 1));
            mTvServiceName.setText(String.valueOf(mData.getCfoId()));
            mTvUserName.setText("dumi");
            mTvCFOName.setText(mData.getCfoFname() + " " + mData.getCfoLname());
            mTvDuration.setText("3");
            mTvAmount.setText("$ 50000");
        }
    }
}
