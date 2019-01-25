package com.easylib.dima.easylib_librarian.Activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.easylib.dima.easylib_librarian.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib_librarian.R;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //Comunication
    ConnectionService mBoundService;
    private boolean mIsBound;
//    FirebaseAuth mAuth;
    private final static int RC_SIGN_IN = 1;
//    GoogleSignInClient mGoogleSignInClient;
//    FirebaseAuth.AuthStateListener mAuthListener;

    //For the communication Service
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
        bindService(new Intent(this, ConnectionService.class), mConnection,
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
    protected void onStart() {
        super.onStart();

//        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void login(View view) {
        // Get username & password
        EditText eText = (EditText) findViewById(R.id.email);
        String username = eText.getText().toString();
        EditText pText = (EditText) findViewById(R.id.password);
        String password = pText.getText().toString();


//        mAuth = FirebaseAuth.getInstance();

        //TODO: Make the call to Server
        Intent intent = new Intent(this, LibraryActivity.class);
        doBindService();
        startActivity(intent);
    }
}
