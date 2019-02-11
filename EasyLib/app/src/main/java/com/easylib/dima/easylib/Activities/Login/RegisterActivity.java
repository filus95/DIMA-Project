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
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.easylib.dima.easylib.Activities.NoInternetActivity;
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

    private EditText sText;
    private EditText nText;
    private EditText eText;
    private EditText pText;

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

    private void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
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
                if (user.getUser_id() == -1) {
                    Toast.makeText(context, "Not Registered", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Registered", Toast.LENGTH_LONG).show();
                    goToLogin();
                }
            }
            if (key.equals(Constants.NETWORK_STATE_DOWN)){
                Intent internetIntent = new Intent (context, NoInternetActivity.class);
                startActivity (internetIntent);
            }
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
        setContentView(R.layout.register);

        sText = (EditText) findViewById(R.id.surname);
        nText = (EditText) findViewById(R.id.name);
        eText = (EditText) findViewById(R.id.email);
        pText = (EditText) findViewById(R.id.password);

        // Change Enter key in Done key when password is typed
        pText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    reg();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume ();
        doBindService();
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.REGISTER_USER));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.NETWORK_STATE_DOWN));
    }

    @Override
    protected void onPause() {
        super.onPause ();
        doUnbindService();
        unregisterReceiver(mMessageReceiver);
    }

    public void register(View view) {
        reg();
    }

    public void reg() {
        String surname = sText.getText().toString();
        String name = nText.getText().toString();
        String email = eText.getText().toString();
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
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
