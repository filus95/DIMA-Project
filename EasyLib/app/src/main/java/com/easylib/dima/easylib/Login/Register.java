package com.easylib.dima.easylib.Login;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.easylib.dima.easylib.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib.ConnectionLayer.Constants;
import com.easylib.dima.easylib.R;

import AnswerClasses.User;

public class Register extends AppCompatActivity {
    ConnectionService mBoundService;
    boolean mIsBound;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBoundService = ((ConnectionService.LocalBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundService = null;
        }
    };

    public void doBindService() {
        bindService(new Intent(Register.this, ConnectionService.class), mConnection,
                Context.BIND_AUTO_CREATE);
        mIsBound = true;
        if(mBoundService!=null){
            mBoundService.IsBoundable();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBindService();
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

        //TODO register method
        if(mBoundService!=null)
        {
            mBoundService.sendMessageWithContent(Constants.REGISTER_USER, user );
        }
    }
}
