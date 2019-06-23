package com.project.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.*;
import com.project.myapplication.retrofit.api_models.CFOUserModel;

import java.util.*;

class CFOUserAdapter
  extends RecyclerView.Adapter<CFOUserAdapter.MyHistoryViewHolder>
  implements Filterable {

  private List<CFOUserModel> mDataList      = new ArrayList<>();
  private List<CFOUserModel> mGlobalDataList;
  private Filter             mCFOUserFilter = new Filter() {
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
      String        searchString  = constraint.toString().toLowerCase().trim();
      FilterResults filterResults = new FilterResults();
      if (searchString.isEmpty()) {
        filterResults.values = mGlobalDataList;
      } else {
        List<CFOUserModel> filterData = new ArrayList<>();
        for (CFOUserModel cfoUserModel : mGlobalDataList) {
          if (String.format("%s %s", cfoUserModel.getCfoFname(), cfoUserModel.getCfoLname())
                    .toLowerCase()
                    .trim()
                    .contains(searchString)||
              cfoUserModel.getCfoPhone().toString().contains(searchString)) {
            filterData.add(cfoUserModel);
          }
        }
        filterResults.values = filterData;
      }
      return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
      mDataList.clear();
      mDataList.addAll((Collection<? extends CFOUserModel>) results.values);
      notifyDataSetChanged();
    }
  };

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

  void setCFOData(List<CFOUserModel> dataList) {
    mDataList.clear();
    mDataList.addAll(dataList);
    notifyDataSetChanged();
    mGlobalDataList = new ArrayList<>(dataList);
  }

  @Override
  public Filter getFilter() {
    return mCFOUserFilter;
  }

  class MyHistoryViewHolder
    extends RecyclerView.ViewHolder {

    private TextView     mTvProjectId;
    private TextView     mTvServiceName;
    private TextView     mTvUserName;
    private TextView     mTvCFOName;
    private TextView     mTvDuration;
    private TextView     mTvAmount;
    private int          mPosition;
    private CFOUserModel mData;

    MyHistoryViewHolder(@NonNull ViewGroup parent) {
      super(LayoutInflater.from(parent.getContext())
                          .inflate(R.layout.item_cfo_user, parent, false));
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

    private void bindData(int position) {
      mPosition = position;
      mData = mDataList.get(mPosition);

      mTvProjectId.setText(String.valueOf(mPosition + 1));
      mTvServiceName.setText(String.valueOf(mData.getCfoId()));
      mTvUserName.setText("dummy");
      mTvCFOName.setText(mData.getCfoFname() + " " + mData.getCfoLname());
      mTvDuration.setText(mData.getCfoYears_of_exp());
      mTvAmount.setText("$ 50000");
    }
  }
}
