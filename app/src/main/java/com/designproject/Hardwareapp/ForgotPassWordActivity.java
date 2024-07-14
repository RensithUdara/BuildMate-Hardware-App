package com.designproject.Hardwareapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.designproject.Hardwareapp.DatabaseHelper;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class ForgotPassWordActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    TextInputLayout edtForgotUsername, edtForgotPhone, edtForgotPassword, edtForgotConfirmPassword;
    String forgotUsername, forgotPhone, forgotPassword, forgotConfirmPassword;
    Button btnChangePassword;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\+(?:[0-9] ?){10,10}[0-9]$");

    @SuppressLint ( "MissingInflatedId" )
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password );

        //change the status bar to dark theme and change color
        getWindow().setStatusBarColor(getColor(R.color.DodgerBlue));
        getWindow().getDecorView().setSystemUiVisibility(0);

        //Set the action bar title as null
        setTitle("");

        //Remove the action bar shadow
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
        }

        databaseHelper = new DatabaseHelper(this);

        btnChangePassword = (Button) findViewById(R.id.btnChangePassword);

        edtForgotUsername = (TextInputLayout) findViewById(R.id.forgotUsername);
        edtForgotPhone = (TextInputLayout) findViewById(R.id.forgotPhone);
        edtForgotPassword = (TextInputLayout) findViewById(R.id.forgotPassword);
        edtForgotConfirmPassword = (TextInputLayout) findViewById(R.id.forgotConfirmPassword);

        edtForgotUsername.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                forgotUsername = edtForgotUsername.getEditText().getText().toString();
                if (forgotUsername.isEmpty()) {
                    edtForgotUsername.setError("Field can't be empty");
                } else if (!forgotUsername.equals(forgotUsername.trim())) {
                    edtForgotUsername.setError("Remove white spaces");
                } else {
                    edtForgotUsername.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtForgotPhone.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                forgotPhone = edtForgotPhone.getEditText().getText().toString();
                if (forgotPhone.isEmpty()) {
                    edtForgotPhone.setError("Field can't be empty");
                } else if (!PHONE_PATTERN.matcher(forgotPhone).matches()) {
                    edtForgotPhone.setError("Invalid number");
                } else {
                    edtForgotPhone.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtForgotPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                forgotPassword = edtForgotPassword.getEditText().getText().toString();
                if (forgotPassword.isEmpty()) {
                    edtForgotPassword.setError("Field can't be empty");
                } else if (!PASSWORD_PATTERN.matcher(forgotPassword).matches()) {
                    edtForgotPassword.setError("Password too week");
                } else {
                    edtForgotPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtForgotConfirmPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                forgotConfirmPassword = edtForgotConfirmPassword.getEditText().getText().toString();
                if (forgotConfirmPassword.isEmpty()) {
                    edtForgotConfirmPassword.setError("Field can't be empty");
                } else if (!forgotConfirmPassword.equals(forgotPassword)) {
                    edtForgotConfirmPassword.setError("Please enter the same password");
                } else {
                    edtForgotConfirmPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotUsername = edtForgotUsername.getEditText().getText().toString();
                forgotPhone = edtForgotPhone.getEditText().getText().toString();
                forgotPassword = edtForgotPassword.getEditText().getText().toString();
                forgotConfirmPassword = edtForgotConfirmPassword.getEditText().getText().toString();

                if (forgotUsername.isEmpty()) {
                    edtForgotUsername.setError("Field can't be empty");
                } else {
                    edtForgotUsername.setError(null);
                }
                if (forgotPhone.isEmpty()) {
                    edtForgotPhone.setError("Field can't be empty");
                } else {
                    edtForgotPhone.setError(null);
                }
                if (forgotPassword.isEmpty()) {
                    edtForgotPassword.setError("Field can't be empty");
                } else {
                    edtForgotPassword.setError(null);
                }
                if (forgotConfirmPassword.isEmpty()) {
                    edtForgotConfirmPassword.setError("Field can't be empty");
                } else {
                    edtForgotConfirmPassword.setError(null);
                }

                if (databaseHelper.checkUserName(forgotUsername)) {
                    if (databaseHelper.checkUserNamePhone(forgotUsername, forgotPhone)) {
                        if (forgotPassword.equals(forgotConfirmPassword)) {
                            try {
                                Cursor data = databaseHelper.getUserData(forgotUsername);
                                if (data.moveToNext()) {
                                    String id = data.getString(0);
                                    boolean isUpdated = databaseHelper.updatePassword(id, forgotPassword);
                                    if (isUpdated) {
                                        Toast.makeText( ForgotPassWordActivity.this, "Password changed", Toast.LENGTH_SHORT).show();
                                        goLoginActivity();
                                    } else {
                                        Toast.makeText( ForgotPassWordActivity.this, "Password not changed", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText( ForgotPassWordActivity.this, "Can't get data", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            edtForgotConfirmPassword.setError("Enter the same password");
                        }

                    } else {
                        edtForgotUsername.setError("Field can't match");
                        edtForgotPhone.setError("Field can't match");
                    }

                } else if (!forgotUsername.isEmpty()){
                    edtForgotUsername.setError("Invalid username");
                }
//                if (databaseHelper.checkPhone(forgotPhone)) {
//
//                } else {
//                    edtForgotPhone.setError("Invalid phone");
//                }

            }
        });

    }

    public void goLoginActivity(View view) {
        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivity(intentLogin);
    }

    public void goLoginActivity() {
        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivity(intentLogin);
    }
}