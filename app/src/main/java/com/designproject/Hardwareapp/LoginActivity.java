package com.designproject.Hardwareapp;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    MaterialCheckBox rememberMe;
    Cursor data, dataById, dataRemember, dataStatus, findId;
    TextInputLayout edtLoginUserName, edtLoginPassword;
    String loginUserName, loginPassword;
    Button btnLogin;

    //FIREBASE VARIABLE


    @SuppressLint ( {"WrongViewCast", "MissingInflatedId"} )
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);

        //change the status bar color
        getWindow().setStatusBarColor(getColor(R.color.DodgerBlue));
        //change the status bar to dark theme
        getWindow().getDecorView().setSystemUiVisibility(0);
        //Set the action bar title as null
        setTitle("");
        //Remove the action bar shadow
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
        }

        btnLogin = (Button) findViewById(R.id.btnLogin);

        edtLoginUserName = (TextInputLayout) findViewById(R.id.loginUserName);
        edtLoginPassword = (TextInputLayout) findViewById(R.id.loginPassword);

        //set onclick listener for checkbox
        rememberMe = findViewById(R.id.checkBoxRememberMe);
        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rememberMe.setButtonTintList( ColorStateList.valueOf(getResources().getColor(R.color.DodgerBlue)));
                } else {
                    rememberMe.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.DarkGray)));
                }
            }
        });

        //set login status = 0 in all the data raw in accounts table
        dataStatus = databaseHelper.getAllData();
        while (dataStatus.moveToNext()) {
            if (dataStatus.getInt(5) == 1) {
                databaseHelper.updateLoginStatus(dataStatus.getString(0), 0);
            }
        }
        dataStatus.close();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserName = edtLoginUserName.getEditText().getText().toString();
                loginPassword = edtLoginPassword.getEditText().getText().toString();
//
                if (loginUserName.isEmpty()) {
                    edtLoginUserName.setError("Username required");
                } else {
                    edtLoginUserName.setError(null);
                }
                if (loginPassword.isEmpty()) {
                    edtLoginPassword.setError("Password required");
                } else {
                    edtLoginPassword.setError(null);
                }

                if (databaseHelper.checkUserName(loginUserName)) {
                    if (databaseHelper.checkUserNamePassword(loginUserName, loginPassword)) {

                        data = databaseHelper.getUserData(loginUserName);
                        dataRemember = databaseHelper.checkRememberMe();
                        if (data.moveToFirst()) {
                            if (rememberMe.isChecked()) {
                                if (data.getInt(5) == 0) {

                                    while (dataRemember.moveToNext()) {
                                        //set all the states as 0
                                        if (dataRemember.getInt(2) == 1) {
                                            databaseHelper.updateRememberTableRemember(dataRemember.getInt(1), 0);
                                            databaseHelper.updateRemember(dataRemember.getString(1), 0);
                                        }
                                    }

                                    //find any accountId exist in remember table is equals to the current UserID
                                    findId = databaseHelper.findAccountIdRememberTable(data.getString(0));
                                    if (findId.getCount() > 0) {
                                        if (findId.moveToNext()) {
                                            //If found, update the states as 1
                                            databaseHelper.updateRememberTableRemember(data.getInt(0), 1);
                                            databaseHelper.updateRemember(data.getString(0), 1);
                                        }
                                    } else {
                                        //if there does not exist, insert new data raw to the remember table
                                        databaseHelper.insertRemember(data.getString(0)); // default status is 1 when inserting
                                        databaseHelper.updateRemember(data.getString(0), 1);
                                    }
                                    findId.close();
                                }
                                dataRemember.close();

                            } else {
                                databaseHelper.updateRemember(data.getString(0), 0);
                                if (dataRemember.getCount() > 0) {
                                    // Check the remember me table if already exist a raw relevant current user ID
                                    while (dataRemember.moveToNext()) {
                                        //If it is found, update status as 0
                                        if (dataRemember.getInt(1) == data.getInt(0)) {
                                            databaseHelper.updateRememberTableRemember(data.getInt(0), 0);
                                        }
                                    }
                                }
                                dataRemember.close();
                            }
                            //when login set the login_status as 1
                            databaseHelper.updateLoginStatus(data.getString(0), 1);
                            data.close();
                        }

                        //finally it goes to home activity
                        goHomeActivity();

                    } else {
                        if (loginPassword.isEmpty()) {
                            edtLoginPassword.setError("Password required");
                        } else {
                            edtLoginPassword.setError("Invalid password");
                        }
                    }
                } else if (!loginUserName.isEmpty()){
                    edtLoginUserName.setError("Invalid user name");
                }

            }
        });

        //THIS SECTION WILL SET EDIT TEXT FILLED BY DATA WHICH IS IN STATE AS 1
        dataRemember = databaseHelper.checkRememberMe();
        if (dataRemember != null) {
            while (dataRemember.moveToNext()) {
                //check the data raw which is status = 1
                if (dataRemember.getInt(2) == 1) {
                    dataById = databaseHelper.getUserDataById(dataRemember.getString(1));
                    if (dataById.moveToNext()) {
                        edtLoginUserName.getEditText().setText(dataById.getString(1));
                        edtLoginPassword.getEditText().setText(dataById.getString(4));
                    }
                    int remember = dataRemember.getInt(2);
                    rememberMe.setChecked(toBoolean(remember));
                }
            }
            dataRemember.close();
        }


    }

    public void goHomeActivity() {
        Intent intentHome = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intentHome);
    }

    public void goToRegister(View view) {
        Intent intentRegister = new Intent(this, RegisterActivity.class);
        startActivity(intentRegister);
    }

    //When try to back from Login page this method will execute
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Exit App");
        alertDialog.setMessage("Do you want to exit BUILD MATE app ?");

        //When click "Yes" it will execute this
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                onStop();
                onDestroy();
            }
        });

        //When click "No" it will execute this
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void goForgotPassWordActivity(View view) {
        Intent intentForgot = new Intent(this, ForgotPassWordActivity.class);
        startActivity(intentForgot);
    }

    //this can convert int value to boolean
    public boolean toBoolean(int num){
        return num!=0;
    }
}