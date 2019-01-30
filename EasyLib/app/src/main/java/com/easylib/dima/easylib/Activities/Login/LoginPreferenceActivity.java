package com.easylib.dima.easylib.Activities.Login;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.easylib.dima.easylib.Activities.Fragments.MainActivity;
import com.easylib.dima.easylib.Adapters.PrefLibAdapter;
import com.easylib.dima.easylib.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib.ConnectionLayer.Constants;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import AnswerClasses.LibraryDescriptor;
import AnswerClasses.User;

public class LoginPreferenceActivity extends AppCompatActivity {

    private ArrayList<LibraryDescriptor> librariesPref;
    private ArrayList<LibraryDescriptor> allLibraries;
    private User userInfo;
    private Intent mainIntent;
    private static final String USER_INFO = "User Info";
    private static final String LOGIN_ALL_LIBRARIES = "Login all Libraries";
    private static final String USER_PREFERENCES = "User Preferences";

    //Comunication
    ConnectionService mBoundService;
    private boolean mIsBound;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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
        bindService(new Intent(LoginPreferenceActivity.this, ConnectionService.class), mConnection,
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

            /*if ( key.equals(Constants.GET_USER_PREFERENCES)) {
                Intent loginPref = new Intent(context, LoginPreferenceActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user Info", user);
                loginPref.putExtras(bundle);
                loginPref.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                doUnbindService();
                startActivity(loginPref);
            }*/
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_preference);

        userInfo = (User) getIntent().getSerializableExtra(USER_INFO);
        allLibraries = (ArrayList<LibraryDescriptor>) getIntent().getSerializableExtra(LOGIN_ALL_LIBRARIES);
        librariesPref = new ArrayList<LibraryDescriptor>();

        // RecyclerView setup
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_pref);
        // improve performance
        mRecyclerView.setHasFixedSize(true);
        // specify an adapter
        mAdapter = new PrefLibAdapter(this, allLibraries);
        mRecyclerView.setAdapter(mAdapter);
        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            // used linear layout
            mLayoutManager = new LinearLayoutManager(this);
        } else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            // used grid layout
            mLayoutManager = new GridLayoutManager(this, 2);
        }
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Communication
        doBindService();
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_USER_PREFERENCES));
    }

    public void skipOnClick(View view) {
        goToMainActivty();
    }

    public void goToMainActivty() {
        mainIntent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(USER_PREFERENCES, librariesPref);
        bundle.putSerializable(USER_INFO, userInfo);
        mainIntent.putExtras(bundle);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        doUnbindService();
        unregisterReceiver(mMessageReceiver);
        startActivity(mainIntent);
    }

    public void addPrefLibrary(LibraryDescriptor library) {
        librariesPref.add(library);
        goToMainActivty();
    }
}
