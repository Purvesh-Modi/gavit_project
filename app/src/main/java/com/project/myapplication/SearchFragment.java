package com.project.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.*;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;
import com.project.myapplication.retrofit.RetrofitClient;
import com.project.myapplication.retrofit.api_models.CFOUserModel;
import com.project.myapplication.retrofit.api_models.UserModel;
import retrofit2.*;

import java.util.List;

public class SearchFragment
  extends Fragment
  implements SearchView.OnQueryTextListener {

  public static final String         TAG = "SearchFragment";
  private             View           view;
  private             RecyclerView   mRvSearchRecycler;
  private             CFOUserAdapter mCFOAdapter;
  private             UserAdapter    mUserAdapter;

  public SearchFragment() {}

  public static SearchFragment newInstance() {
    return new SearchFragment();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Nullable
  @Override
  public View onCreateView(
    @NonNull LayoutInflater inflater,
    @Nullable ViewGroup container,
    @Nullable Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_search, container, false);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mRvSearchRecycler = view.findViewById(R.id.rv_search_recycler);
    /*mRvSearchRecycler.addItemDecoration(new DividerItemDecoration(getContext(),
      DividerItemDecoration.VERTICAL));*/

    mCFOAdapter = new CFOUserAdapter();
    mUserAdapter = new UserAdapter();

    if (BaseConfiguration.isCurrentUserIsCFO) {
      getAllCFOUsers();
      mRvSearchRecycler.setAdapter(mCFOAdapter);
    } else {
      getAllUsers();
      mRvSearchRecycler.setAdapter(mUserAdapter);
    }
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.search_menu, menu);
    MenuItem   search     = menu.findItem(R.id.action_search);
    SearchView searchView = (SearchView) search.getActionView();
    
    searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
    searchView.setOnQueryTextListener(this);
    super.onCreateOptionsMenu(menu, inflater);
  }

  private void getAllCFOUsers() {
    try {
      Call<List<CFOUserModel>> userListCall =
        RetrofitClient.getInstance().getApi().getAllCFOUsers();

      userListCall.enqueue(new Callback<List<CFOUserModel>>() {
        @Override
        public void onResponse(
          Call<List<CFOUserModel>> call,
          Response<List<CFOUserModel>> response) {
          if (response.body() != null) {
            mCFOAdapter.setCFOData(response.body());
          } else {
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

  private void getAllUsers() {
    try {
      Call<List<UserModel>> userListCall = RetrofitClient.getInstance().getApi().getAllUsers();

      userListCall.enqueue(new Callback<List<UserModel>>() {
        @Override
        public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
          if (response.body() != null) {
            mUserAdapter.setUserData(response.body());
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

  @Override
  public boolean onQueryTextSubmit(String s) {
    return false;
  }

  @Override
  public boolean onQueryTextChange(String s) {
    if (BaseConfiguration.isCurrentUserIsCFO) {
      mCFOAdapter.getFilter().filter(s);
    } else {
      mUserAdapter.getFilter().filter(s);
    }
    return false;
  }
}
