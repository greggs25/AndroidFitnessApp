package com.oo115.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.oo115.myapplication.Calculator.MarcoCalcFragment;
import com.oo115.myapplication.Settings.SettingsFragment;
import com.oo115.myapplication.WeightTracking.WeightTrackingFragment;
import com.oo115.myapplication.Workout_feature.CustomPlanLVAdapter;
import com.oo115.myapplication.Workout_feature.Select_Muscle_GroupFragment;
import com.oo115.myapplication.bodyMeasures.MeasurementFragment;
import com.oo115.myapplication.retrofitAPI.ApiClient;
import com.oo115.myapplication.retrofitAPI.ApiInterface;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CustomPlanLVAdapter.ChangeFragmentListener {


    TextView nav_header_name, nav_header_mail;
    Toolbar toolbar;
    ProgressBar progressBarLogin;
    private DrawerLayout drawer;
    public PrefConFig prefConFig;
    String user_firstName, user_Email;
    public static ApiInterface apiInterface;
    private SettingsFragment SettingsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer= findViewById(R.id.drawer_layout);
        progressBarLogin = findViewById(R.id.progressBarLogin);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setTitle("Home");
        prefConFig = new PrefConFig(this);
        user_firstName = prefConFig.readName();

        SettingsFragment = new SettingsFragment();


        //loads up the home fragment instead of home activity view
        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_containerHome,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        //setting menu header to contain email address of user
        View header_email = navigationView.getHeaderView(0);

        nav_header_mail = header_email.findViewById(R.id.nav_header_email);
        nav_header_name = header_email.findViewById(R.id.nav_header_name);
        user_Email = prefConFig.readEmail();
        nav_header_mail.setText(user_Email);
        nav_header_name.setText(prefConFig.readName());


        Toast.makeText(this, prefConFig.readId(), Toast.LENGTH_SHORT).show();
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);




    }

    //this method allows the users to navigate to the different Fragments in the drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (!fragments.isEmpty()) {
            for (Fragment fragment : fragments) {
                if (!fragment.isHidden()) {
                    transaction.hide(fragment);
                }
            }
        }

        switch (menuItem.getItemId()){


            case R.id.nav_profile:

                transaction.replace(R.id.fragment_containerHome, new ProfileFragment()).addToBackStack(null).commit();


                break;
            case R.id.nav_weight:
                transaction.replace(R.id.fragment_containerHome, new WeightTrackingFragment()).addToBackStack(null).commit();


                break;
            case R.id.nav_Measurements:
                transaction.replace(R.id.fragment_containerHome, new MeasurementFragment()).addToBackStack(null).commit();


                break;
            case R.id.nav_home:

                transaction.replace(R.id.fragment_containerHome, new HomeFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_workout:
                transaction.replace(R.id.fragment_containerHome, new Select_Muscle_GroupFragment()).addToBackStack(null).commit();

                break;
            case R.id.nav_calculator:
                transaction.replace(R.id.fragment_containerHome, new MarcoCalcFragment()).addToBackStack(null).commit();
                break;


        }
        drawer.closeDrawer(GravityCompat.START );
        return true;
    }



    /**
     * check what item in the three dot menu was clicked and performs the corresponding action
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        FragmentManager manager = getSupportFragmentManager();
        switch (item.getItemId()) {

            case R.id.action_setting:
                replaceFragment(SettingsFragment);
                break;

            case R.id.action_logout:
                logout();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.fragment_containerHome, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    /**
     * helps to inflate the three dot menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_menu, menu);

        return true;
    }


    @Override
    public void onBackPressed() {
        /*this checks if the user has just signed-in or registered,
        if they have they shouldnt be able to press back */
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        }


        int count = getFragmentManager().getBackStackEntryCount();

        this.getSupportActionBar().show();

        /*
        Checks if the drawer is open when the when the back button is pressed,
        if it is then it closes it before carrying on
         */
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            this.getSupportActionBar().hide();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        } else {

            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            super.onBackPressed();



        }


    }


    /**
     * Logs the user out of the application by setting their login status to false
     */
    public void logout(){

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.Logging_out_title))
                .setContentText(getString(R.string.confirm_logout))
                .setCancelText(getString(R.string.dialog_cancel))
                .setConfirmText(getString(R.string.str_ok))
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .setConfirmButton(R.string.menu_log_out, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        prefConFig.log_out();


                        Intent intToMain = new Intent(MainActivity.this, WelcomeActivity.class);
                        intToMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intToMain);

                    }
                })

                .show();

    }

    /*
    This method is used to change fragment
     */
    @Override
    public void callFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.show(newFragment);
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.fragment_containerHome, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        // Commit the transaction
        transaction.commit();

    }


}