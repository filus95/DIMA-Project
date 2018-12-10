package com.easylib.dima.easylib.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.easylib.dima.easylib.Main.MainActivity;
import com.easylib.dima.easylib.R;

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

        if( username.length() == 0 || password.length() == 0)
            findViewById(R.id.text_error).setVisibility(View.VISIBLE);

        else {

            //TODO: Make the call to Server

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void register(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void loginFb(View view) {
        //TODO: call Facebook Login API
    }

    public void loginGoogle(View view) {
        //TODO: call Google Login API
    }
}
