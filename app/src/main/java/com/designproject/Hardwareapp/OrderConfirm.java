package com.designproject.Hardwareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class OrderConfirm extends AppCompatActivity {

    private Button btnreturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);

        VideoView videoView = findViewById(R.id.videoView);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.thanks);
        videoView.start();

        btnreturn = findViewById(R.id.btnreturn);
        btnreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnreturn();
            }
        });
    }

    public void btnreturn() {
        Intent intent = new Intent(OrderConfirm.this, HomeActivity.class);
        startActivity(intent);
    }
}
