package com.easylib.dima.easylib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.easylib.dima.easylib.Main.MainActivity;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void login(View view) {
        // Get username & password
        EditText uText = (EditText) findViewById(R.id.username);
        String username = uText.getText().toString();
        EditText pText = (EditText) findViewById(R.id.password);
        String password = pText.getText().toString();

        //TODO: Make the call to Server

        //Just for test
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void loginFb(View view) {
        //TODO: call Facebook Login API
    }

    public void loginGoogle(View view) {
        //TODO: call Google Login API
    }
}
