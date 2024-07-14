package com.designproject.Hardwareapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.designproject.Hardwareapp.DatabaseHelper;

import java.util.concurrent.Executor;

public class SettingsActivity extends AppCompatActivity  {


    CardView deleteAccount;
    DatabaseHelper databaseHelper;
    Cursor data;

    //Fingerprint related variables
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    ConstraintLayout settingsLayout;
    Boolean isFingerprintOn = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        databaseHelper = new DatabaseHelper(this);


        // To set action bar custom view
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //Change action bar title
        AppCompatTextView textView = findViewById(R.id.title_actionbar);
        textView.setText("Settings");
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

        deleteAccount = findViewById(R.id.deleteAccount);
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFingerprintOn) {
                    Toast.makeText(SettingsActivity.this, "Please Enable Fingerprint", Toast.LENGTH_SHORT).show();
                } else {
                    fingerprint();
                }
            }
        });

        // Fingerprint Switch
        SwitchCompat mySwitch = findViewById(R.id.switchFingerprint);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(SettingsActivity.this, "Fingerprint Enabled", Toast.LENGTH_SHORT).show();
                    isFingerprintOn = true;
                } else {
                    // Switch is in the off state
                    Toast.makeText(SettingsActivity.this, "Fingerprint Disable", Toast.LENGTH_SHORT).show();
                    isFingerprintOn = false;
                }
            }
        });

        if (isFingerprintOn) {
            fingerprint();
        }
    }

    public void fingerprint() {
        // Switch is in the on state
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

        biometricPrompt = new BiometricPrompt(SettingsActivity.this, executors, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                data = databaseHelper.getAllData();
                while (data.moveToNext()) {
                    // this will get the account which is logged
                    int status = data.getInt(6);
                    if (status == 1) {
                        boolean is_deleted = databaseHelper.deleteAccount(data.getInt(0));
                        if (is_deleted) {
                            //if delete an account, this will be delete relevant data raw in REMEMBER ME table
                            databaseHelper.deleteRemember(data.getInt(0));

                            Toast.makeText(SettingsActivity.this, "Account Successfully Deleted !", Toast.LENGTH_SHORT).show();
                            Intent intentLogin = new Intent(SettingsActivity.this, LoginActivity.class);
                            startActivity(intentLogin);
                        } else {
                            Toast.makeText(SettingsActivity.this, "Account Deletion Failed !", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo = new  BiometricPrompt.PromptInfo.Builder().setTitle("BUILD MATE")
                .setDescription("Authentication Required !").setDeviceCredentialAllowed(true).build();

        biometricPrompt.authenticate(promptInfo);
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