package com.easylib.dima.easylib.Activities.Lists;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.easylib.dima.easylib.Activities.Fragments.MainActivity;
import com.easylib.dima.easylib.Activities.LibraryActivity;
import com.easylib.dima.easylib.Activities.Login.LoginPreferenceActivity;
import com.easylib.dima.easylib.Adapters.LibraryAdapter;
import com.easylib.dima.easylib.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib.ConnectionLayer.Constants;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import AnswerClasses.LibraryDescriptor;
import AnswerClasses.User;

public class LibraryListActivity extends AppCompatActivity {

    private static final String LIBRARIES_LIST = "Libraries List";
    private static final String USER_INFO = "User Info";
    private ArrayList<LibraryDescriptor> libraries = new ArrayList<LibraryDescriptor>();
    private User userInfo;
    private static final String LIBRARY_IS_PREFERITE = "Library is Preferite";
    private static final String LIBRARY_INFO = "Library Info";
    private LibraryDescriptor libraryToShow;
    private Intent libraryIntent;

    //Comunication
    ConnectionService mBoundService;
    private boolean mIsBound;

    // recycle view
    private RecyclerView mRecyclerView;
    private LibraryAdapter mAdapter;
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
        bindService(new Intent(LibraryListActivity.this, ConnectionService.class), mConnection,
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

            if (key.equals(Constants.GET_USER_PREFERENCES)) {
                ArrayList<LibraryDescriptor> librariesPref = (ArrayList<LibraryDescriptor>) intent.getSerializableExtra(Constants.GET_USER_PREFERENCES);
                Boolean libraryIsPref;
                libraryIsPref = false;
                for (LibraryDescriptor library : librariesPref) {
                    if (library.getId_lib() == libraryToShow.getId_lib())
                        libraryIsPref = true;
                }
                libraryIntent = new Intent(context, LibraryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(LIBRARY_INFO, libraryToShow);
                bundle.putSerializable(LIBRARY_IS_PREFERITE, libraryIsPref);
                bundle.putSerializable(USER_INFO, userInfo);
                libraryIntent.putExtras(bundle);
                doUnbindService();
                unregisterReceiver(mMessageReceiver);
                startActivity(libraryIntent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        userInfo = (User) getIntent().getSerializableExtra(USER_INFO);
        libraries = (ArrayList<LibraryDescriptor>) getIntent().getSerializableExtra(LIBRARIES_LIST);

        // Communication
        doBindService();
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_USER_PREFERENCES));

        mRecyclerView = (RecyclerView) findViewById(R.id.list_recycle);
        // improve performance
        mRecyclerView.setHasFixedSize(true);
        // used linear layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // specify an adapter
        mAdapter = new LibraryAdapter(this, libraries);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Communication
        doBindService();
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_USER_PREFERENCES));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        doUnbindService();
        unregisterReceiver(mMessageReceiver);
    }

    public void showLibrary(LibraryDescriptor library) {
        libraryToShow = library;
        if (mBoundService != null) {
            mBoundService.setCurrentContext(getApplicationContext());
            mBoundService.sendMessage(Constants.GET_USER_PREFERENCES, userInfo.getUser_id());
        }
    }
}
