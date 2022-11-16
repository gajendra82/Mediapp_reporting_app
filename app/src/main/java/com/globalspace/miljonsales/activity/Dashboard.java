package com.globalspace.miljonsales.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.globalspace.miljonsales.fragment.AddcoveredpincodeFragment;
import com.globalspace.miljonsales.ui.add_details_dashboard.AddDetailsDashboardFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalspace.miljonsales.MiljonOffer;
import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.fragment.FragmentAnalytics;
import com.globalspace.miljonsales.fragment.FragmentCampaign;
import com.globalspace.miljonsales.fragment.FragmentChangePassword;
import com.globalspace.miljonsales.fragment.FragmentCoveredChemist;
import com.globalspace.miljonsales.fragment.FragmentNotInterested;
import com.globalspace.miljonsales.fragment.FragmentOthers;
import com.globalspace.miljonsales.fragment.FragmentReporting;
import com.globalspace.miljonsales.fragment.FragmentSubordinatesList;
import com.globalspace.miljonsales.fragment.FragmentLeave;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private TextView username, versionNo, refreshBtn, searchBtn, groupBtn, editBtn, toolbartitle, emp_name, reporting_manager;
    private ImageView profilePic;
    private SharedPreferences sPref;
    private SharedPreferences.Editor edit;
    private FrameLayout frameLayout;
    private Fragment fragment;
    private FragmentTransaction transaction;
    private Toolbar searchToolbar, editToolbar;
    private SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        setContent();
		
		/*fragment = new FragmentReporting();
		transaction.add(R.id.frameLayout, fragment).commit();*/

        fragment = new FragmentAnalytics();
        transaction.add(R.id.frameLayout, fragment).commit();
        toolbartitle.setText("Dashboard");
        navigationView.getMenu().getItem(0).setChecked(true);
        refreshBtn.setVisibility(View.GONE);
        groupBtn.setVisibility(View.GONE);
        searchBtn.setVisibility(View.GONE);
        editBtn.setVisibility(View.GONE);

        fab.setVisibility(View.GONE);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void setContent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbartitle = (TextView) findViewById(R.id.toolbartitle);

        setSupportActionBar(toolbar);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        transaction = getSupportFragmentManager().beginTransaction();
        sPref = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        edit = sPref.edit();

        searchToolbar = (Toolbar) findViewById(R.id.searchtoolbar);
        searchToolbar.setVisibility(View.GONE);

        editToolbar = (Toolbar) findViewById(R.id.edittoolbar);
        editToolbar.setVisibility(View.GONE);

        refreshBtn = (TextView) findViewById(R.id.refreshBtn);
        refreshBtn.setTypeface(MiljonOffer.solidFont);

        groupBtn = (TextView) findViewById(R.id.group_Btn);
        groupBtn.setTypeface(MiljonOffer.solidFont);

        editBtn = (TextView) findViewById(R.id.editBtn);
        editBtn.setTypeface(MiljonOffer.solidFont);

        searchBtn = (TextView) findViewById(R.id.searchBtn);
        searchBtn.setTypeface(MiljonOffer.solidFont);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        username = (TextView) headerView.findViewById(R.id.username_tv);
        reporting_manager = (TextView) headerView.findViewById(R.id.rm_tv);
        if (sPref.getString(getResources().getString(R.string.reporting_manager), "").equals("")) {
            reporting_manager.setText("No RM Assigned");
        } else {
            reporting_manager.setText("RM : " + sPref.getString(getResources().getString(R.string.reporting_manager), ""));
        }
        if (sPref.getString(getResources().getString(R.string.subordinate_flag), "").equals("N")) {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.subordinateList).setVisible(false);
        }
        emp_name = (TextView) headerView.findViewById(R.id.name_tv);
        String username1 = sPref.getString(getString(R.string.Username), "");
        username.setText(username1 + "");
        emp_name.setText(sPref.getString(getResources().getString(R.string.employee_name), ""));
       // versionNo = (TextView) findViewById(R.id.tv_version);
       /* //region version No
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
         //   versionNo.setText("Version " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //endregion*/
        profilePic = (ImageView) headerView.findViewById(R.id.profilePic);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_exit_to_app_black_24dp)
                    .setTitle("Application Exit")
                    .setMessage("Do you want to exit application")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    //region Extra
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.dashboard, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    //endregion

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.analytics) {
            fragment = new FragmentAnalytics();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
            refreshBtn.setVisibility(View.GONE);
            searchBtn.setVisibility(View.GONE);
            groupBtn.setVisibility(View.GONE);
            editBtn.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            toolbartitle.setText("Dashboard");
        } else if (id == R.id.home) {
            fragment = new FragmentReporting();
          //  fragment = FragmentReporting.newInstance(getSupportFragmentManager());
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
            refreshBtn.setVisibility(View.VISIBLE);
            searchBtn.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
            groupBtn.setVisibility(View.GONE);
            editBtn.setVisibility(View.GONE);
            toolbartitle.setText("Call Report");
        } else if (id == R.id.hospital) {
            fragment = new AddDetailsDashboardFragment();
          //  fragment = FragmentReporting.newInstance(getSupportFragmentManager());
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
            fab.setVisibility(View.VISIBLE);
            refreshBtn.setVisibility(View.GONE);
            groupBtn.setVisibility(View.GONE);
            editBtn.setVisibility(View.GONE);
            searchBtn.setVisibility(View.GONE);
            sdf = new SimpleDateFormat("dd MMMM yyyy");
            String currentDate = sdf.format(new Date());
            toolbartitle.setText(currentDate);
        } else if (id == R.id.addcoveredpincode) {
            fragment = new AddcoveredpincodeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
            refreshBtn.setVisibility(View.GONE);
            groupBtn.setVisibility(View.GONE);
            editBtn.setVisibility(View.GONE);
            searchBtn.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            toolbartitle.setText("Add Covered Pin Code");
        }



        else if (id == R.id.notInterestedChemist) {
            fragment = new FragmentNotInterested();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
            refreshBtn.setVisibility(View.GONE);
            groupBtn.setVisibility(View.GONE);
            editBtn.setVisibility(View.GONE);
            searchBtn.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
            toolbartitle.setText("Not Interested Chemist");
        } else if (id == R.id.subordinateList) {
            fragment = new FragmentSubordinatesList();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
            refreshBtn.setVisibility(View.GONE);
            groupBtn.setVisibility(View.GONE);
            editBtn.setVisibility(View.GONE);
            searchBtn.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
            toolbartitle.setText("Subordinates");
        } else if (id == R.id.applyleave) {
            fragment = new FragmentLeave();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
            refreshBtn.setVisibility(View.GONE);
            searchBtn.setVisibility(View.GONE);
            groupBtn.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            editBtn.setVisibility(View.GONE);
            toolbartitle.setText("Apply Leave");
        } else if (id == R.id.chemistCampaign) {
            fragment = new FragmentCampaign();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
            refreshBtn.setVisibility(View.VISIBLE);
            groupBtn.setVisibility(View.GONE);
            editBtn.setVisibility(View.VISIBLE);
            searchBtn.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
            toolbartitle.setText("Chemist Campaign");
        }else if (id == R.id.coveredChemist) {
            fragment = new FragmentCoveredChemist() ;
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
            refreshBtn.setVisibility(View.VISIBLE);
            groupBtn.setVisibility(View.GONE);
            editBtn.setVisibility(View.VISIBLE);
            searchBtn.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
            toolbartitle.setText("Covered Chemist");
        }
        else if (id == R.id.otherReporting) {
            fragment = new FragmentOthers();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
            refreshBtn.setVisibility(View.GONE);
            groupBtn.setVisibility(View.GONE);
            searchBtn.setVisibility(View.GONE);
            editBtn.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            toolbartitle.setText("Other Reporting");
        }  else if (id == R.id.changePassword) {
            fragment = new FragmentChangePassword();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
            refreshBtn.setVisibility(View.GONE);
            groupBtn.setVisibility(View.GONE);
            searchBtn.setVisibility(View.GONE);
            editBtn.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            toolbartitle.setText("Change Password");
        } else if (id == R.id.signOut) {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_exit_to_app_black_24dp)
                    .setTitle("Sign Out")
                    .setMessage("Do you want to sign out")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            edit.putBoolean(getString(R.string.isLogin), false);
                            edit.commit();
                            Intent i = new Intent(Dashboard.this, Login.class);
                            startActivity(i);
                            Dashboard.this.finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void calldialogueforleave() {
        final Dialog dialog1 = new Dialog(Dashboard.this);
        // Include dialog.xml file
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.popup_leaveapply);
        TextView texttoshow = (TextView) dialog1.findViewById(R.id.texttoshow);

        TextView tv_ok = (TextView) dialog1.findViewById(R.id.tv_yes);
        TextView tv_no = (TextView) dialog1.findViewById(R.id.tv_no);


        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                navigationView.getMenu().getItem(0).setChecked(true);

            }
        });
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                navigationView.getMenu().getItem(0).setChecked(true);
            }
        });
        dialog1.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        sdf = new SimpleDateFormat("dd MMMM yyyy");
        String currentDate = sdf.format(new Date());
        if(toolbartitle.getText().equals(currentDate)){
            fragment = new AddDetailsDashboardFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
        }

    }
}
