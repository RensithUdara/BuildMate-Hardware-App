package com.designproject.Hardwareapp;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import android.animation.LayoutTransition;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrdersActivity extends AppCompatActivity {

    CardView order1;
    TextView order1Details;
    LinearLayout linearOrder1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        // To set action bar custom view
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //Change action bar title
        AppCompatTextView textView = findViewById(R.id.title_actionbar);
        textView.setText("Orders");
        //change the status bar color
        getWindow().setStatusBarColor(getColor(R.color.DodgerBlue));
        //change the status bar to dark theme
        getWindow().getDecorView().setSystemUiVisibility(0);
        //Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Remove the action bar shadow
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
        }
    }

    //when click back button, it will go previous item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // handle back button click
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}