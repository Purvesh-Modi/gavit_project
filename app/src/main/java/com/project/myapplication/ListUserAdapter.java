package com.project.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.myapplication.retrofit.api_models.CFOUserModel;
import com.project.myapplication.retrofit.api_models.UserModel;

import java.util.ArrayList;
import java.util.List;

class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.UserViewHolder> {

    private List<UserModel> mDataList = new ArrayList<>();

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new UserViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
        userViewHolder.bindData(i);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    void setUserData(List<UserModel> dataList) {
        mDataList.clear();
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        private int          mPosition;
        private TextView     mTvUserName;
        private TextView     mTvEmail;
        private TextView     mTvPhone;
        private UserModel mData;

        UserViewHolder(@NonNull ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user, parent, false));
            initView(itemView);
        }

        private void bindData(int position) {
            mPosition = position;
            mData = mDataList.get(mPosition);

            mTvUserName.setText(mData.getUserFname() + " " + mData.getUserLname());
            mTvEmail.setText(mData.getUserEmail() + "");
            mTvPhone.setText(String.valueOf(mData.getUserPhone()));
        }

        private void initView(View convertView) {
            mTvUserName = convertView.findViewById(R.id.tv_user_name);
            mTvEmail = convertView.findViewById(R.id.tv_email);
            mTvPhone = convertView.findViewById(R.id.tv_phone);
        }
    }
}
