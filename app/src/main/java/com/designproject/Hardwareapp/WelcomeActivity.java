package com.designproject.Hardwareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;



public class WelcomeActivity extends AppCompatActivity {


    Timer timer;

    //Fingerprint related variables
    androidx.biometric.BiometricPrompt biometricPrompt;
    androidx.biometric.BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // FINGERPRINT CODES

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this, "Device Doesn't have Fingerprint", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this, "Not Working !", Toast.LENGTH_SHORT).show();

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this, "No Fingerprint assigned !", Toast.LENGTH_SHORT).show();
            case BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED:
                break;
            case BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED:
                break;
            case BiometricManager.BIOMETRIC_STATUS_UNKNOWN:
                break;
            case BiometricManager.BIOMETRIC_SUCCESS:
                break;
        }

        Executor executors = ContextCompat.getMainExecutor(this);

        biometricPrompt = new BiometricPrompt(WelcomeActivity.this, executors, new androidx.biometric.BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull androidx.biometric.BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(WelcomeActivity.this, "Account Successfully Deleted !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo = new  androidx.biometric.BiometricPrompt.PromptInfo.Builder().setTitle("BUILDMATE")
                .setDescription("Authentication Required !").setDeviceCredentialAllowed(true).build();

        biometricPrompt.authenticate(promptInfo);


//      hide the action bar from this activity
        getSupportActionBar().hide();

        // Hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


//      Automatically play the welcome video when intent to this activity
        VideoView videoView = findViewById(R.id.videoView);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.welcome5);
        videoView.start();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                goLogin();
            }
        }, 5000); // delay 5 seconds
    }

    //Automatically call this method after 5 seconds
    protected void goLogin() {
        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivity(intentLogin);
    }
}