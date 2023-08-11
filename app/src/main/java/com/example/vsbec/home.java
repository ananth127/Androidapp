package com.example.vsbec;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vsbec.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class home extends AppCompatActivity {
    ActivityMainBinding bining;
    BottomNavigationView bottomNavigationView;
    homeFragment homeFragment =new homeFragment();
    profileFragment profileFragment=new profileFragment();
    settingFragment settingFragment=new settingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        bottomNavigationView=findViewById(R.id.bottom_navigaion);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemid=item.getItemId();
                int home=R.id.homefragment;
                if(itemid == R.id.homefragment) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                } else if (itemid == R.id.profile) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                } else if (itemid == R.id.settings) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, settingFragment).commit();
                }
                return false;
            }
        });

    }
}