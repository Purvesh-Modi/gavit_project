package com.project.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.project.myapplication.utils.Constants;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int getCfoID;
    private Toolbar                                               toolbar;
    private NavigationView                                        navigationView;
    private DrawerLayout                                          drawer;
    private BottomNavigationView                                  navView;
    private TextView                                              mTvLoginUserName;
    private TextView                                              mTvLoginUserEmail;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.sub_content_frame, HistoryFragment.newInstance(),
                                    HistoryFragment.TAG).commit();
                    toolbar.setTitle(R.string.title_home);
                    return true;
                case R.id.navigation_search:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.sub_content_frame, SearchFragment.newInstance(),
                                    SearchFragment.TAG).commit();
                    toolbar.setTitle(R.string.search);
                    return true;
                case R.id.navigation_profile:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.sub_content_frame, ProfileFragment.newInstance(),
                                    ProfileFragment.TAG).commit();
                    toolbar.setTitle(R.string.profile);
                    return true;

                case R.id.navigation_logout:
                    mLogout();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getCfoID =
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this)
                        .getInt(Constants.KEY_USER_ID, 0);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        mTvLoginUserName = navigationView.getHeaderView(0)
                .findViewById(R.id.tv_login_user_name);

        mTvLoginUserEmail = navigationView.getHeaderView(0)
                .findViewById(R.id.tv_login_user_email);

        mTvLoginUserName.setText(PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.KEY_USER_NAME, ""));
        mTvLoginUserEmail.setText(PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.KEY_USER_EMAIL, ""));

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.sub_content_frame, HistoryFragment.newInstance(), ListFragment.TAG)
                .commit();
        toolbar.setTitle(R.string.title_home);

        navView = findViewById(R.id.navigation);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you really wanna exit ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("stay here", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.sub_content_frame, HistoryFragment.newInstance(),
                            HistoryFragment.TAG).commit();
            toolbar.setTitle(R.string.menu_home);
        } else if (id == R.id.nav_Update) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.sub_content_frame, EditFragment.newInstance(), EditFragment.TAG)
                    .commit();
            toolbar.setTitle(R.string.menu_Update);

        } else if (id == R.id.nav_List) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.sub_content_frame, ListFragment.newInstance(), ListFragment.TAG)
                    .commit();
            toolbar.setTitle(R.string.menu_List);

        } else if (id == R.id.nav_logout) {
            mLogout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);

        MenuItem home = menu.findItem(R.id.navigation_home);
        home.setVisible(false);

        MenuItem search = menu.findItem(R.id.navigation_search);
        search.setVisible(false);

        MenuItem profile = menu.findItem(R.id.navigation_profile);
        profile.setVisible(false);

        MenuItem logout = menu.findItem(R.id.navigation_logout);
        logout.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_logout:
                mLogout();
                break;
        }
        return true;
    }

    private void mLogout() {
        if (getCfoID != 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you wanna Logout ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().clear().apply();
                    finish();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });
            builder.setNegativeButton("stay here", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        } else {
            Toast.makeText(this, "Please ! Login first", Toast.LENGTH_SHORT).show();
        }
    }
}