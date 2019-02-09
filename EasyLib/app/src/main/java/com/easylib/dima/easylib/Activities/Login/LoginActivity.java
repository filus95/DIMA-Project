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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.easylib.dima.easylib.ConnectionLayer.CheckConnectionService;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
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
    CheckConnectionService mCheckConnService;
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
    private static final String GOOGLE_TOKEN = "google_token";
    private static final String USER_ID = "User ID";
    private static final String LOGIN = "Login";
    private static final String USER_PREFERENCES = "User Preferences";
    private static final String USER_INFO = "User Info";
    private static final String LOGIN_ALL_LIBRARIES = "Login all Libraries";

    private EditText eText;
    private EditText pText;
    private TextView errorText;
    private ConstraintLayout loadingLayout;
    private ImageButton loginButton;
    private ImageButton regButton;
    private ImageButton googleButton;
    private ImageButton facebookButton;

    //For the communication Service
    private ServiceConnection mConnection2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mCheckConnService = ((CheckConnectionService.LocalBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

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
        bindService(new Intent(LoginActivity.this, CheckConnectionService.class), mConnection2,
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
                    loadingLayout.setVisibility (View.INVISIBLE);
                } else {
                    SharedPreferences sp = getSharedPreferences(LOGIN, MODE_PRIVATE);
                    if(!sp.contains(USER_ID)) {
                        SharedPreferences.Editor Ed = sp.edit();
                        Ed.putInt(USER_ID, user.getUser_id());
                        Ed.commit();
                    }
                    userInfo = user;
                    callUserPreferences();
//                    sendNewNotificationToken(user);

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
            if (key.equals(Constants.NETWORK_STATE_UP)){
                Toast.makeText(context,"NETWORK IS UP!", Toast.LENGTH_LONG).show();
                startApp ();
            }
            if (key.equals(Constants.NETWORK_STATE_DOWN)){
                Toast.makeText(context,"NETWORK IS DOWN!", Toast.LENGTH_LONG).show();
            }
            if (key.equals(Constants.USER_LOGIN_GOOGLE)){
                User user = (User) intent.getSerializableExtra(Constants.USER_LOGIN_GOOGLE);
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
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
//        String email = mAuth.getCurrentUser().getEmail();
//        if ( email != null ){
//
//        }
//        if ( isNetworkAvailable())
//            mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Communication
        doBindService();
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.USER_LOGIN));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_USER_PREFERENCES));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_ALL_LIBRARIES));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.NETWORK_STATE_UP));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.NETWORK_STATE_DOWN));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.USER_LOGIN_GOOGLE));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.USER_SILENT_LOGIN_GOOGLE));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        startService(new Intent(LoginActivity.this, CheckConnectionService.class));

        startApp ();
    }

    private void startApp() {
        if ( isNetworkAvailable() ) {
            setView();
            startService(new Intent(LoginActivity.this, ConnectionService.class));
            notificationSetup();
            // Google Initialization
            mAuth = FirebaseAuth.getInstance();

            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() != null) {
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
            Handler handler = new Handler();
            if (!sp.contains(USER_ID)) {
                loadingLayout.setVisibility(View.INVISIBLE);
                makeItemsTouchable (true);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mCheckConnService != null) {
                            mCheckConnService.setCurrentContext(getApplicationContext());
                        }
                    }
                }, 1000);

            } else {
                User user = new User();
                user.setUser_id(sp.getInt(USER_ID, -1));
                // Send Login Info to Server
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mBoundService != null) {
                            mCheckConnService.setCurrentContext(getApplicationContext());
                            mBoundService.setCurrentContext(getApplicationContext());
                            mBoundService.sendMessage(Constants.USER_LOGIN, user);
                        }
                    }
                }, 1000);
            }
        }else {
            setView();
        }
    }

    private void setView(){
        eText = (EditText) findViewById(R.id.email);
        pText = (EditText) findViewById(R.id.password);
        errorText = (TextView) findViewById (R.id.login_text_error);
        loadingLayout = (ConstraintLayout) findViewById(R.id.login_load_layout);
        loginButton = (ImageButton) findViewById (R.id.login_bt);
        regButton = (ImageButton) findViewById (R.id.reg_bt);
        googleButton = (ImageButton) findViewById(R.id.g_bt);
        facebookButton = (ImageButton) findViewById(R.id.fb_bt);

        // Make the Layout not clickable
        makeItemsTouchable (false);

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
    }

    public void makeItemsTouchable(Boolean areTouchable) {
        eText.setEnabled (areTouchable);
        pText.setEnabled (areTouchable);
        googleButton.setEnabled (areTouchable);
        loginButton.setEnabled (areTouchable);
        regButton.setEnabled (areTouchable);
        googleButton.setEnabled (areTouchable);
        facebookButton.setEnabled (areTouchable);
    }

    public void login(View view) {
        String email = eText.getText().toString();
        String password = pText.getText().toString();
        User user = new User();
        user.setEmail(email);
        user.setPlainPassword(password);
        user.setUser_id(-1);

        // try...catch used to hide keyboard after LoginActivity button pressed
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }

        if( email.length() == 0 || password.length() == 0) {
            errorText.setVisibility (View.VISIBLE);
            errorText.setText ("Fields not filled up !");
        } else {

            loadingLayout.setVisibility(View.VISIBLE);
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
                account.getIdToken();
                User user = new User();
                user.setGoogle_id_token(account.getIdToken());
                user.setEmail(account.getEmail());
                user.setName(account.getGivenName());
                user.setSurname(account.getFamilyName());
                if (mBoundService != null) {
                    mBoundService.setCurrentContext(this);
                    mBoundService.sendMessage(Constants.USER_LOGIN_GOOGLE, user);
                }
            } catch (ApiException e) {
                e.printStackTrace();
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
        private boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

    // sending identification token for Firebase notifications
    private void sendNewNotificationToken(User user){
            FirebaseInstanceId.getInstance().getInstanceId()
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
                                    user.setNotification_token(token);
                                    mBoundService.sendMessage(Constants.NEW_NOTIFICATION_TOKEN, user);
                                    break;
                                }
                            }
                        }
                    });
        }
}

