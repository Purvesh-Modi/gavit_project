package com.project.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.project.myapplication.retrofit.api_models.CFOUserModel;
import com.project.myapplication.retrofit.api_models.UserModel;

import java.util.*;

class UserAdapter
  extends RecyclerView.Adapter<UserAdapter.UserViewHolder> implements Filterable {

    private List<UserModel> mDataList = new ArrayList<>();
    private List<UserModel> mGlobalDataList;
    private Filter mUserFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String        searchString  = constraint.toString().toLowerCase().trim();
            FilterResults filterResults = new FilterResults();
            if (searchString.isEmpty()) {
                filterResults.values = mGlobalDataList;
            } else {
                List<UserModel> filterData = new ArrayList<>();
                for (UserModel userModel : mGlobalDataList) {
                    if (String.format("%s %s", userModel.getUserFname(), userModel.getUserLname())
                              .toLowerCase()
                              .trim()
                              .contains(searchString)||
                        userModel.getUserPhone().toString().contains(searchString)) {
                        filterData.add(userModel);
                    }
                }
                filterResults.values = filterData;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mDataList.clear();
            mDataList.addAll((Collection<? extends UserModel>) results.values);
            notifyDataSetChanged();
        }
    };

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
        mGlobalDataList = new ArrayList<>(dataList);
    }

    @Override
    public Filter getFilter() {
        return mUserFilter;
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
