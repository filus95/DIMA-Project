package com.easylib.dima.easylib_librarian.Activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.easylib.dima.easylib_librarian.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib_librarian.ConnectionLayer.Constants;
import com.easylib.dima.easylib_librarian.R;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import AnswerClasses.LibraryDescriptor;
import AnswerClasses.User;
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

    // Components
    private EditText email;
    private EditText password;
    private ConstraintLayout loadingLayout;
    private ImageButton loginButton;
    private TextView errotText;

    // Needed
    private static final String USER_INFO = "User Info";
    private static final String USER_ID = "User ID";
    private static final String LOGIN = "Login";
    private static final String LIBRARY_INFO = "Library Info";
    private User userInfo;
    private LibraryDescriptor libraryInfo;

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
        bindService(new Intent(LoginActivity.this, ConnectionService.class), mConnection,
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
        unregisterReceiver(mMessageReceiver);
    }

    //This is the handler that will manager to process the broadcast intent
    //This has to be created inside each activity that needs it ( almost anyone )
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String key = extractKey(intent);

            if ( key.equals(Constants.LIBRARIAN_LOGIN)) {
                User user = (User) intent.getSerializableExtra(Constants.LIBRARIAN_LOGIN);
                if (user.getUser_id() == -1) {
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_LONG).show();
                    loadingLayout.setVisibility (View.INVISIBLE);
                } else {
                    userInfo = user;
                    getLibraryInfo ();
                }
            }
            if (key.equals (Constants.GET_LIBRARY_INFO)) {
                libraryInfo = (LibraryDescriptor) intent.getSerializableExtra (Constants.GET_LIBRARY_INFO);
                Intent libraryIntent = new Intent(context, LibraryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(LIBRARY_INFO, libraryInfo);
                bundle.putSerializable(USER_INFO, userInfo);
                libraryIntent.putExtras(bundle);
                libraryIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                doUnbindService();
                startActivity(libraryIntent);
            }
        }
    };

    private String extractKey(Intent intent){
        Set<String> keySet = Objects.requireNonNull(intent.getExtras()).keySet();
        Iterator iterator = keySet.iterator();
        return (String)iterator.next();
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

        startService(new Intent(LoginActivity.this, ConnectionService.class));

        // get components
        email = (EditText) findViewById (R.id.email);
        password = (EditText) findViewById (R.id.password);
        loginButton = (ImageButton) findViewById (R.id.login_bt);
        loadingLayout = (ConstraintLayout) findViewById (R.id.login_load_layout);
        errotText = (TextView) findViewById (R.id.text_error);

        // Change Enter key in Done key when password is typed
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    login(v);
                    return true;
                }
                return false;
            }
        });

        // Communication
        doBindService();
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.LIBRARIAN_LOGIN));
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.GET_LIBRARY_INFO));
    }

    public void login(View view) {
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        User user = new User();
        user.setEmail(emailText);
        user.setPlainPassword(passwordText);
        user.setUser_id(-1);

        // try...catch used to hide keyboard after LoginActivity button pressed
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }

        if( email.length() == 0 || password.length() == 0) {
            errotText.setVisibility (View.VISIBLE);
            errotText.setText ("Fields not filled up !");
        } else {
            loadingLayout.setVisibility(View.VISIBLE);
            // Send Login Info to Server
            if (mBoundService != null) {
                mBoundService.setCurrentContext(this);
                mBoundService.sendMessage(Constants.LIBRARIAN_LOGIN, user);
            }
        }
    }

    public void getLibraryInfo() {
        if (mBoundService != null) {
            mBoundService.setCurrentContext(this);
            mBoundService.sendMessage(Constants.GET_LIBRARY_INFO, userInfo.getUser_id ());
        }
    }
}
