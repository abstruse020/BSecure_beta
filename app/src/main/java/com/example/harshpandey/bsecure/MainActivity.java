package com.example.harshpandey.bsecure;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        verifyPermissions();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new home_fragment(),"home_frag").commit();
        navigationView.setCheckedItem(R.id.home);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //home_fragment hf = (home_fragment)getSupportFragmentManager().findFragmentByTag("home_frag");
        feedback_fragment ff = (feedback_fragment)getSupportFragmentManager().findFragmentByTag("feedback_frag");
        help_fragment hef = (help_fragment)getSupportFragmentManager().findFragmentByTag("help_frag");
        about_us_fragment af = (about_us_fragment)getSupportFragmentManager().findFragmentByTag("about_us_frag");
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (ff !=null && ff.isVisible() ){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new home_fragment(),"home_frag").commit();
            navigationView.setCheckedItem(R.id.home);
        }
        else if(hef!=null && hef.isVisible()){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new home_fragment(),"home_frag").commit();
            navigationView.setCheckedItem(R.id.home);

        }
        else if (af!=null && af.isVisible()){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new home_fragment(),"home_frag").commit();
            navigationView.setCheckedItem(R.id.home);

        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(this,Settings.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.home){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new home_fragment(),"home_frag").commit();

        }
        else if (id == R.id.about_us) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new about_us_fragment(),"about_us_frag").commit();

        } else if (id == R.id.help) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new help_fragment(),"help_frag").commit();

        } else if (id == R.id.feedback) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new feedback_fragment(),"feedback_frag").commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void verifyPermissions()
    {
        Log.d("TAG","verify permissions : asking user for the permissions");
        String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[0])==PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[1])==PackageManager.PERMISSION_GRANTED)
        {
        }
        else
        {
            ActivityCompat.requestPermissions(this,permissions,1);
        }
    }
}
