package com.designproject.Hardwareapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    TextInputLayout edtUserName, edtEmail, edtPhone, edtPassword, edtConfirmPassword;
    Button btnRegister;
    String userName, email, phone, password, confirmPassword;
    Boolean validity = false;
    Cursor data;

    Map<String, Object> users;

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

    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


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

        btnRegister = (Button) findViewById(R.id.btnRegister);

        edtUserName = (TextInputLayout) findViewById(R.id.regUserName);
        edtEmail = (TextInputLayout) findViewById(R.id.regEmail);
        edtPhone = (TextInputLayout) findViewById(R.id.forgotPhone);
        edtPassword = (TextInputLayout) findViewById(R.id.forgotPassword);
        edtConfirmPassword = (TextInputLayout) findViewById(R.id.forgotConfirmPassword);

        edtUserName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userName = edtUserName.getEditText().getText().toString();
                validUserName(userName);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                email = edtEmail.getEditText().getText().toString();
                validateEmail(email);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtPhone.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phone = edtPhone.getEditText().getText().toString();
                validPhone(phone);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = edtPassword.getEditText().getText().toString();
                validatePassword(password);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtConfirmPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                confirmPassword = edtConfirmPassword.getEditText().getText().toString();
                password = edtPassword.getEditText().getText().toString();
                confirmPassword(confirmPassword, password);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        registerUser();
    }

    public void goLogin() {
        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivity(intentLogin);
    }

    public void registerUser() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = edtUserName.getEditText().getText().toString();
                email = edtEmail.getEditText().getText().toString();
                phone = edtPhone.getEditText().getText().toString();
                password = edtPassword.getEditText().getText().toString();
                confirmPassword = edtConfirmPassword.getEditText().getText().toString();

                Boolean validUserName = validUserName(userName);
                Boolean validEmail = validateEmail(email);
                Boolean validPhone = validPhone(phone);
                Boolean validPassword = validatePassword(password);
                Boolean validConfirm = confirmPassword(confirmPassword, password);

                if (validUserName && validEmail && validPhone && validPassword && validConfirm) {
                    boolean isInserted = databaseHelper.registerUser(userName, email, phone, password);
                    if (isInserted) {
                        Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                        goLogin();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration fail", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Validation failed!", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    public boolean validateEmail(String email) {
        if (email.isEmpty()) {
            edtEmail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Please enter a valid email address");
            return false;
        } else {
            edtEmail.setError(null);
            validity = true;
            return true;
        }
    }

    public boolean validatePassword(String password) {
        if (password.isEmpty()) {
            edtPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            edtPassword.setError("Password too week");
            return false;
        } else {
            edtPassword.setError(null);
            validity = true;
            return true;
        }
    }

    public boolean confirmPassword(String confirmPassword, String password) {
        if (!confirmPassword.equals(password)) {
            edtConfirmPassword.setError("Please enter the same password");
            return false;
        } else {
            edtConfirmPassword.setError(null);
            validity = true;
            return true;
        }
    }

    public boolean validUserName(String userName) {
        if (userName.isEmpty()) {
            edtUserName.setError("Field can't be empty");
            return false;
        } else if (!userName.equals(userName.trim())) {
            edtUserName.setError("Remove white spaces");
            return false;
        } else if (databaseHelper.checkUserName(userName)) {
            edtUserName.setError("User name already exist");
            return false;
        } else {
            edtUserName.setError(null);
            return true;
        }
    }

    public boolean validPhone(String phone) {
        if (phone.isEmpty()) {
            edtPhone.setError("Field can't be empty");
            return false;
        } else if (!PHONE_PATTERN.matcher(phone).matches()) {
            edtPhone.setError("Invalid number");
            return false;
        } else {
            edtPhone.setError(null);
            return true;
        }
    }

    public void goLoginActivity(View view) {
        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivity(intentLogin);
    }
}