package com.easylib.dima.easylib.Activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.easylib.dima.easylib.Adapters.ImageTitleEventAdapter;
import com.easylib.dima.easylib.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib.ConnectionLayer.Constants;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import AnswerClasses.Event;
import AnswerClasses.User;

public class JoinedEventsActivity extends AppCompatActivity {

    private static final String USER_INFO = "User Info";
    private static final String ALL_EVENTS = "All Events";
    private ArrayList<Event> joinedEvents;
    private User userInfo;

    private Boolean firstTimeCalledActivity = true;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //Comunication
    ConnectionService mBoundService;
    private boolean mIsBound;

    //For the communication Service
    private ServiceConnection mConnection = new ServiceConnection () {
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
        bindService(new Intent (JoinedEventsActivity.this, ConnectionService.class), mConnection,
                Context.BIND_AUTO_CREATE);
        mIsBound = true;
        if(mBoundService!=null){
            mBoundService.IsBoundable();
        }
    }

    public void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
        unregisterReceiver(mMessageReceiver);
    }

    private String extractKey(Intent intent){
        Set<String> keySet = Objects.requireNonNull(intent.getExtras()).keySet();
        Iterator iterator = keySet.iterator();
        return (String)iterator.next();
    }

    //This is the handler that will manager to process the broadcast intent
    //This has to be created inside each activity that needs it ( almost anyone )
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver () {
        @Override
        public void onReceive(Context context, Intent intent) {
            String key = extractKey(intent);

            if (key.equals(Constants.GET_EVENTS_PER_USER)) {
                joinedEvents = (ArrayList<Event>) intent.getSerializableExtra (Constants.GET_EVENTS_PER_USER);
                setAdapter ();
            }
            if (key.equals(Constants.NETWORK_STATE_DOWN)){
                Intent internetIntent = new Intent (context, NoInternetActivity.class);
                startActivity (internetIntent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        userInfo = (User) getIntent().getSerializableExtra(USER_INFO);
        joinedEvents = (ArrayList<Event>) getIntent().getSerializableExtra(ALL_EVENTS);

        mRecyclerView = (RecyclerView) findViewById (R.id.list_recycle);
        // improve performance
        mRecyclerView.setHasFixedSize (true);
        // used linear layout
        // Set Layout
        if ((getResources ().getConfiguration ().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            mLayoutManager = new GridLayoutManager (this, 3);
        } else if ((getResources ().getConfiguration ().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            mLayoutManager = new GridLayoutManager (this, 6);
        }
        mRecyclerView.setLayoutManager (mLayoutManager);
        mRecyclerView.setItemAnimator (new DefaultItemAnimator ());

        if (firstTimeCalledActivity) {
            setAdapter ();
        }
    }

    @Override
    protected void onPause() {
        super.onPause ();
        firstTimeCalledActivity = false;
        doUnbindService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Communication
        doBindService();
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.GET_EVENTS_PER_USER));
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.NETWORK_STATE_DOWN));

        // Get Library info from Server if activity resumed
        if (!firstTimeCalledActivity) {
            new Handler ().postDelayed (new Runnable () {
                @Override
                public void run() {
                    if (mBoundService != null) {
                        mBoundService.setCurrentContext (getApplicationContext ());
                        mBoundService.sendMessage (Constants.GET_EVENTS_PER_USER, userInfo);
                    }
                }
            }, 1000);
        }
    }

    public void setAdapter() {
        // specify an adapter
        mAdapter = new ImageTitleEventAdapter (this, joinedEvents, userInfo);
        mRecyclerView.setAdapter (mAdapter);
    }
}
