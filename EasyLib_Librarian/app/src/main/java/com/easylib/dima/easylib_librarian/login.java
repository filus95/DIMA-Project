package com.easylib.dima.easylib_librarian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class login extends AppCompatActivity {

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
    }

    public void loginFb(View view) {
        //TODO: call Facebook login API
    }

    public void loginGoogle(View view) {
        //TODO: call Google login API
    }
}
