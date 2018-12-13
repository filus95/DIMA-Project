package com.easylib.dima.easylib.Login;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.easylib.dima.easylib.ConnectionLayer.ClientThreadPool;
import com.easylib.dima.easylib.ConnectionLayer.NetworkStarter;
import com.easylib.dima.easylib.R;

import AnswerClasses.User;

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

        User user = new User();
        user.setUsername(surname);
        user.setEmail(email);
        user.setPlainPassword(password);
        new sendMessage().execute();
        //        .sendRegistration(user);
        //TODO register method
    }

    private static class sendMessage extends AsyncTask<User, String, Void> {
        @Override
        protected Void doInBackground(User... user) {
            return null;
        }
    }
}
