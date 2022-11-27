package com.vrctech.aproundup.activities;

import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import com.vrctech.aproundup.GlobalMethods;
import com.vrctech.aproundup.NotifyHelper;
import com.vrctech.aproundup.R;
import com.vrctech.aproundup.activities.aboutUs.AboutUsFragment;
import com.vrctech.aproundup.activities.epaper.EpaperFragment;
import com.vrctech.aproundup.activities.minister.MinistersFragment;
import com.vrctech.aproundup.activities.mla.AssemblyResultsFragment;
import com.vrctech.aproundup.activities.mla.DistrictsListActivity;
import com.vrctech.aproundup.activities.mp.ParliamentConstituenciesListActivity;
import com.vrctech.aproundup.activities.mp.ParliamentResultsFragment;

import java.util.Objects;

/**
 * Launch activity.
 * This will hold all features fragments.
 */
public class LaunchActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static String PENDING_INTENT = "PENDING_INTENT";
    public static String COVID_CASES = "COVID_CASES";
    public static String NEWS_PAPERS = "NEWS_PAPERS";

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    boolean showMenu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeLaunch);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        init();

        if(savedInstanceState != null){
            showMenu = savedInstanceState.getBoolean("SHOW_MENU");
            invalidateOptionsMenu();
            return;
        }

        if(!GlobalMethods.isInternetAvailable(this)){
            displayNoInternetDialog();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new EpaperFragment()).commit();
        }

    }

    public void init(){
        drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.naviationView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        showMenu = false;
        if(id == R.id.newsPapers){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new EpaperFragment(), "EPAPER").commit();
        }else if(id == R.id.assemblyResults){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new AssemblyResultsFragment(), "ASSEMBLY_RESULTS").commit();
            showMenu = true;
        }else if(id == R.id.parliamentResults){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ParliamentResultsFragment(), "PARLIAMENT_RESULTS").commit();
            showMenu = true;
        }else if(id == R.id.rateUs){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
        }else if(id == R.id.ministersList){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new MinistersFragment(), "MINISTERS").commit();
        }else if(id == R.id.aboutUs){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new AboutUsFragment(), "ABOUT_US").commit();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        invalidateOptionsMenu();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_launch_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.constituencyWiseResults);
        menuItem.setVisible(showMenu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.constituencyWiseResults) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
            if(fragment instanceof AssemblyResultsFragment) {
                startActivity(new Intent(this, DistrictsListActivity.class));
            }else if(fragment instanceof ParliamentResultsFragment){
                startActivity(new Intent(this, ParliamentConstituenciesListActivity.class));
            }
            return true;
        }else if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            askIfTheUserWantToCloseTheApplication();
        }
    }

    public void askIfTheUserWantToCloseTheApplication(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage(R.string.confirm_close_app);
        builder.setPositiveButton(R.string.yes, (dialog, i) -> LaunchActivity.super.onBackPressed());
        builder.setNegativeButton(R.string.no, (dialog, i) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void displayNoInternetDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.no_network);
        builder.setMessage(R.string.internet_not_available);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.retry, (dialog, i) -> {
            if(GlobalMethods.isInternetAvailable(this)) {
                dialog.dismiss();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new EpaperFragment()).commit();
            }else{
                displayNoInternetDialog();
                NotifyHelper.toast(this, R.string.no_network);
            }
        });
        builder.setNegativeButton(R.string.ok, (dialog, i) -> {
            finish();
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("SHOW_MENU", showMenu);
    }

}
