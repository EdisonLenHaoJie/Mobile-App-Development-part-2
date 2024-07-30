package com.fit2081.assignment12081;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;


public class MainActivity3 extends AppCompatActivity {
    private DrawerLayout drawerlayout;
    private NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity_main);

        drawerlayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar2);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerlayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());







    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.option_refresh) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView2);
            if (fragment instanceof FragmentListCategory) {
                ((FragmentListCategory) fragment).refreshData();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }





    public void onNewEventButtonClick(View view){

        Intent intent = new Intent(this, NewEventCategory.class);


        startActivity(intent);
    }

    public void onAddEventButtonClick(View view){

        Intent intent = new Intent(this, AddEvent.class);


        startActivity(intent);
    }



        class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // get the id of the selected item
                int id = item.getItemId();

                if (id == R.id.View_All_Categories){
                    Intent intent = new Intent(MainActivity3.this, ListCategoryActivity.class);


                    startActivity(intent);
                }

                if (id == R.id.Add_Category){
                    Intent intent = new Intent(MainActivity3.this, NewEventCategory.class);


                    startActivity(intent);
                }

                if (id == R.id.View_All_Events){

                    // Do something

                }else if (id == R.id.Log_out) {
                    finish();
                }
                // close the drawer
                drawerlayout.closeDrawers();
                // tell the OS
                return true;
            }
        }
}