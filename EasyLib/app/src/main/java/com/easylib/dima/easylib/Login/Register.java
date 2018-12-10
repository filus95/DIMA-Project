package com.easylib.dima.easylib.Login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.easylib.dima.easylib.R;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
    }

    public void register(View view) {

        EditText sText = (EditText) findViewById(R.id.surname);
        String surname = sText.getText().toString();
        EditText nText = (EditText) findViewById(R.id.name);
        String name = nText.getText().toString();
        EditText eText = (EditText) findViewById(R.id.email);
        String email = eText.getText().toString();
        EditText pText = (EditText) findViewById(R.id.password);
        String password = pText.getText().toString();

        if( surname.length() == 0 || name.length() == 0 || email.length() == 0 ||
                password.length() == 0 )
            findViewById(R.id.text_error).setVisibility(View.VISIBLE);

        //TODO register method

    }
}
