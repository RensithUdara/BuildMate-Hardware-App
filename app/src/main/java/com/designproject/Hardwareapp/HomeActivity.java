package com.designproject.Hardwareapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    // declare variables for tab bar and bottom navigation bar
    BottomNavigationView bottomNavigationView;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    FrameLayout frameLayout;
    TextView detailsText;
    LinearLayout layout;

//    DrawerLayout drawerLayout;
//    NavigationView navigationView;
//    ActionBarDrawerToggle toggle;
//    Toolbar toolbar;


//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (toggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // To set action bar custom view
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //Change action bar title
        AppCompatTextView textView = findViewById(R.id.title_actionbar);
        textView.setText("HOME");
        //change the status bar color
        getWindow().setStatusBarColor(getColor(R.color.DodgerBlue));
        //change the status bar to dark theme
        getWindow().getDecorView().setSystemUiVisibility(0);
        //Remove the action bar shadow
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
        }

        //ExpandView codes

        // set variables for tab bar and bottom navigation bar
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);
        bottomNavigationView = findViewById(R.id.bottomNav);
        frameLayout = findViewById(R.id.frameLayout);


//        drawerLayout = findViewById(R.id.drawer_layout);
//        navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        toggle = new ActionBarDrawerToggle(this,
//                drawerLayout, R.string.open_drawer, R.string.close_drawer);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.drawer_order:
//                        Toast.makeText(HomeActivity.this, "Order", Toast.LENGTH_SHORT).show();
//                        goLogin();
//                        break;
//                    case R.id.drawer_payment:
//                        Toast.makeText(HomeActivity.this, "Payment", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.drawer_delivery:
//                        Toast.makeText(HomeActivity.this, "Delivery", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.drawer_settings:
//                        Toast.makeText(HomeActivity.this, "Settings", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.drawer_about:
//                        Toast.makeText(HomeActivity.this, "About", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//                return false;
//            }
//        });


        // Implementation of tab navigation
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
                viewPager2.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager2.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        tabLayout.getTabAt(position).select();
                }
                super.onPageSelected(position);
            }
        });

        // Implementation of bottom navigation
        replaceFragment(new HomeFragment());
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceID")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                frameLayout.setVisibility(View.VISIBLE);
                viewPager2.setVisibility(View.GONE);

                switch (item.getItemId()){
                    case R.id.home:
                        replaceFragment(new BuildingFragment());
                        tabLayout.setVisibility(View.VISIBLE);
                        textView.setText("HOME");
                        tabLayout.getTabAt(0).select();
                        return true;
                    case R.id.cart:
                        replaceFragment(new CartFragment());
                        tabLayout.setVisibility(View.GONE);
                        textView.setText("CART");
                        return true;
                    case R.id.notification:
                        replaceFragment(new NotificationFragment());
                        tabLayout.setVisibility(View.GONE);
                        textView.setText("NOTIFICATIONS");
                        return true;
                    case R.id.menu:
                        replaceFragment(new MenuFragment());
                        tabLayout.setVisibility(View.GONE);
                        textView.setText("MENU");
                        return true;

                }
                return false;
            }
        });
    }

    //Method for replace fragments
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.drawer_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawer_order:
                Toast.makeText(HomeActivity.this, "Order", Toast.LENGTH_SHORT).show();
                break;
            case R.id.drawer_delivery:
                Toast.makeText(HomeActivity.this, "Delivery", Toast.LENGTH_SHORT).show();
                break;
            case R.id.drawer_settings:
                Toast.makeText(HomeActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.drawer_about:
                Toast.makeText(HomeActivity.this, "About", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}