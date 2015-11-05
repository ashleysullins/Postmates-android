package com.epicodus.postmatesclone.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.epicodus.postmatesclone.R;
import com.facebook.stetho.Stetho;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    private EditText mUsernameInput;
    private EditText mPasswordInput;
    private Button mLoginButton;
    private Button mRegisterButton;
    private Button mCompanyLoginButton;
    private TextView mResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);

        mUsernameInput = (EditText) findViewById(R.id.newUsername);
        mPasswordInput = (EditText) findViewById(R.id.newPassword);
        mLoginButton = (Button) findViewById(R.id.btnLogin);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = mUsernameInput.getText().toString();
                final String password = mPasswordInput.getText().toString();

                // TODO: Make model for user, password should check if name and pw match to let them log in.

                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            goToMainPage();
                        }
                        else if (user == null) {
                            String username = mUsernameInput.getText().toString().trim();
                            String password = mPasswordInput.getText().toString().trim();
                            String role = "customer";
                            registerUser(username, password, role);
                            goToMainPage();
                        }
                        //TODO: Add error handling if username and password do not match
                         else {
                            AlertDialog show = new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Message")
                                    .setMessage("Error: username or password wrong")
                                    .setNeutralButton("OK", null)
                                    .show();
                        }
                    }
                });
                }
        });
    }

    private void goToMainPage() {
        Intent intent = new Intent(MainActivity.this, StoreActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void registerUser(String username, String password, String role) {
        if (username.isEmpty() || password.isEmpty()) {
            showErrorDialog();
        } else {
            ParseUser newUser = getParseUser(username, password);
            newUser.put("role", role);

            newUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    setProgressBarIndeterminateVisibility(false);

                    if (e == null) {
                        // Success!
                        goToMainPage();
                    } else showErrorDialog();
                }
            });
        }
    }

    @NonNull
    private ParseUser getParseUser(String username, String password) {
        setProgressBarIndeterminateVisibility(true);

        ParseUser newUser = new ParseUser();
        newUser.setUsername(username);
        newUser.setPassword(password);
        return newUser;
    }

    private void showErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("This registration won't work for us.")
                .setTitle("Oops!")
                .setPositiveButton(android.R.string.ok, null)
                .create()
                .show();
    }
}

