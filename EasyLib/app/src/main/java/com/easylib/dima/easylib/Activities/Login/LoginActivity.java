package com.easylib.dima.easylib.Activities.Login;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.easylib.dima.easylib.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib.Activities.Fragments.MainActivity;
import com.easylib.dima.easylib.ConnectionLayer.Constants;
import com.easylib.dima.easylib.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import AnswerClasses.LibraryDescriptor;
import AnswerClasses.User;

public class LoginActivity extends AppCompatActivity {

    //Comunication
    ConnectionService mBoundService;
    private boolean mIsBound;
    //SignInButton googleSignInButtun;
    FirebaseAuth mAuth;
    private final static int RC_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth.AuthStateListener mAuthListener;

    private Intent loginPrefIntent;
    private Intent mainIntent;
    private User userInfo;
    private ArrayList<LibraryDescriptor> libraries;
    private static final String USER_ID = "User ID";
    private static final String LOGIN = "Login";
    private static final String USER_PREFERENCES = "User Preferences";
    private static final String USER_INFO = "User Info";
    private static final String LOGIN_ALL_LIBRARIES = "Login all Libraries";

    private EditText eText;
    private EditText pText;
    private ConstraintLayout loadingLayout;
    private ImageButton googleButton;
    private ImageButton facebookButton;

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
    }

    private String extractKey(Intent intent){
        Set<String> keySet = Objects.requireNonNull(intent.getExtras()).keySet();
        Iterator iterator = keySet.iterator();
        return (String)iterator.next();
    }

    //This is the handler that will manager to process the broadcast intent
    //This has to be created inside each activity that needs it ( almost anyone )
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String key = extractKey(intent);

            if ( key.equals(Constants.USER_LOGIN)) {
                User user = (User) intent.getSerializableExtra(Constants.USER_LOGIN);
                if (user.getUser_id() == -1) {
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_LONG).show();
                } else {
                    SharedPreferences sp = getSharedPreferences(LOGIN, MODE_PRIVATE);
                    if(!sp.contains(USER_ID)) {
                        SharedPreferences.Editor Ed = sp.edit();
                        Ed.putInt(USER_ID, user.getUser_id());
                        Ed.commit();
                    }
                    userInfo = user;
                    callUserPreferences();
                }
            }
            if (key.equals(Constants.GET_USER_PREFERENCES)) {
                libraries = (ArrayList<LibraryDescriptor>) intent.getSerializableExtra(Constants.GET_USER_PREFERENCES);
                if (libraries.size() == 0) {
                    callAllLibraries();
                } else {
                    mainIntent = new Intent(context, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(USER_PREFERENCES, libraries);
                    bundle.putSerializable(USER_INFO, userInfo);
                    mainIntent.putExtras(bundle);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    doUnbindService();
                    unregisterReceiver(mMessageReceiver);
                    startActivity(mainIntent);
                }
            }
            if (key.equals(Constants.GET_ALL_LIBRARIES)) {
                libraries = (ArrayList<LibraryDescriptor>) intent.getSerializableExtra(Constants.GET_ALL_LIBRARIES);
                loginPrefIntent = new Intent(context, LoginPreferenceActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(USER_INFO, userInfo);
                bundle.putSerializable(LOGIN_ALL_LIBRARIES, libraries);
                loginPrefIntent.putExtras(bundle);
                loginPrefIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                doUnbindService();
                unregisterReceiver(mMessageReceiver);
                startActivity(loginPrefIntent);
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Start the Service
        startService(new Intent(LoginActivity.this, ConnectionService.class));

        super.onCreate(savedInstanceState);
        notificationSetup();
        setContentView(R.layout.login);

        eText = (EditText) findViewById(R.id.email);
        pText = (EditText) findViewById(R.id.password);
        loadingLayout = (ConstraintLayout) findViewById(R.id.login_load_layout);
        googleButton = (ImageButton) findViewById(R.id.g_bt);
        facebookButton = (ImageButton) findViewById(R.id.fb_bt);

        // Change Enter key in Done key when password is typed
        pText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    login(v);
                    return true;
                }
                return false;
            }
        });

        // Google Initialization
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if( firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        doBindService();

        // for Multiple Filters call this multiple times
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.USER_LOGIN));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_USER_PREFERENCES));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_ALL_LIBRARIES));

        // Check if Info are saved
        SharedPreferences sp = getSharedPreferences(LOGIN, MODE_PRIVATE);
        if(!sp.contains(USER_ID)) {
            loadingLayout.setVisibility(View.INVISIBLE);
        } else {
            User user = new User();
            user.setUser_id(sp.getInt(USER_ID, -1));
            // Send Login Info to Server
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mBoundService != null) {
                        mBoundService.setCurrentContext(getApplicationContext());
                        mBoundService.sendMessage(Constants.USER_LOGIN, user);
                    }
                }
            }, 1000);
        }
    }

    public void login(View view) {
        String email = eText.getText().toString();
        String password = pText.getText().toString();
        User user = new User();
        user.setEmail(email);
        user.setPlainPassword(password);
        user.setUser_id(-1);

        // sending identification token for Firebase notifications
        /*FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        while(true) {
                            if (mBoundService != null){
                                User user = new User();
                                user.setUser_id(25);
                                user.setNotification_token(token);
                                mBoundService.sendMessage(Constants.NEW_NOTIFICATION_TOKEN, user);
                                break;
                            }
                        }
                    }
                });
        */

        // try...catch used to hide keyboard after LoginActivity button pressed
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }

        if( email.length() == 0 || password.length() == 0)
            findViewById(R.id.text_error).setVisibility(View.VISIBLE);

        else {

            loadingLayout.setVisibility(View.VISIBLE);

            //TODO: Make the call to Server
            // Get username & password
//          Integer num = 1;

            // Send Login Info to Server
            if (mBoundService != null) {
                mBoundService.setCurrentContext(this);
                mBoundService.sendMessage(Constants.USER_LOGIN, user);
            }
        }
    }

    public void callUserPreferences() {
        if (mBoundService != null) {
            mBoundService.setCurrentContext(this);
            mBoundService.sendMessage(Constants.GET_USER_PREFERENCES, userInfo.getUser_id());
        }
    }

    public void callAllLibraries() {
        if (mBoundService != null) {
            mBoundService.setCurrentContext(this);
            mBoundService.sendMessage(Constants.GET_ALL_LIBRARIES, null);
        }
    }

    public void register(View view) {
        mBoundService.setCurrentContext(this);
        Intent intent = new Intent(this, RegisterActivity.class);

        doUnbindService();
        this.unregisterReceiver(mMessageReceiver);
        startActivity(intent);
    }

    private void safeUnbinding(){
        while (true)
            if ( mBoundService == null)
                break;
    }

    public void loginGoogle(View view) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                //
                User user = new User();
                user.setEmail(account.getEmail());
                user.setUsername(account.getGivenName());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("tag", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("tag", "signInWithCredential:failure", task.getException());
//                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }

    public void loginFb(View view) {
        //TODO: call Facebook LoginActivity API
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */

    private void notificationSetup(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
//        if (getIntent().getExtras() != null) {
//            for (String key : getIntent().getExtras().keySet()) {
//                Object value = getIntent().getExtras().get(key);
//                Log.d(TAG, "Key: " + key + " Value: " + value);
//            }
        }
        // [END handle_data_extras]
}

