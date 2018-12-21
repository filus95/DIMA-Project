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
import com.easylib.dima.easylib.Main.MainActivity;
import com.easylib.dima.easylib.R;

public class Login extends AppCompatActivity {
    ConnectionService mBoundService;
    private boolean mIsBound;

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
        bindService(new Intent(Login.this, ConnectionService.class), mConnection,
                Context.BIND_AUTO_CREATE);
        mIsBound = true;
        if(mBoundService!=null){
            mBoundService.IsBoundable();
        }
    }

    private void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        startService(new Intent(Login.this, ConnectionService.class));
        doBindService();
    }

    public void login(View view) {
        // Get username & password
//        Integer num = 1;
        mBoundService.setCurrentContext(this);
//        mBoundService.sendMessage(Constants.GET_NEWS, num);
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
        mBoundService.setCurrentContext(this);
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
