package com.easylib.dima.easylib.Activities.Login;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.easylib.dima.easylib.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib.ConnectionLayer.Constants;
import com.easylib.dima.easylib.R;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import AnswerClasses.User;

public class RegisterActivity extends Activity {
    ConnectionService mBoundService;
    boolean mIsBound;

    // Create Service bounding inside the class
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
        bindService(new Intent(RegisterActivity.this, ConnectionService.class), mConnection,
                Context.BIND_AUTO_CREATE);
        mIsBound = true;
        if(mBoundService!=null){
            mBoundService.IsBoundable();
        }
    }

    //This is the handler that will manager to process the broadcast intent
    //This has to be created inside each activity that needs it ( almost anyone )
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String key = extractKey(intent);

            if ( key.equals(Constants.REGISTER_USER)) {
                User user = (User) intent.getSerializableExtra(Constants.REGISTER_USER);
                Toast.makeText(context, user.getName() + user.getEmail(), Toast.LENGTH_LONG).show();
                goToLogin();
            }

//            else if something Else....
//            IN THIS WAY WE CAN MANAGE DIFFERENT MESSAGES AND DIFFERENT REACTION FOR EACH ACTIVITY


            // Extract data included in the Intent
            // String message = intent.getStringExtra("message");

        }
    };

    private String extractKey(Intent intent){
        Set<String> keySet = Objects.requireNonNull(intent.getExtras()).keySet();
        Iterator iterator = keySet.iterator();
        return (String)iterator.next();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBindService();
        setContentView(R.layout.register);
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.REGISTER_USER));
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
        else {
            User user = new User();
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setPlainPassword(password);

            if (mBoundService != null) {
                mBoundService.setCurrentContext(this);
                mBoundService.sendMessage(Constants.REGISTER_USER, user);
            }
        }
    }

    public void goToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        this.unregisterReceiver(mMessageReceiver);
        startActivity(intent);

    }
}
