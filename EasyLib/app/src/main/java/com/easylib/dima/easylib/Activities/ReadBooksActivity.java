package com.easylib.dima.easylib.Activities;

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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.easylib.dima.easylib.Adapters.ReadBooksAdapter;
import com.easylib.dima.easylib.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib.ConnectionLayer.Constants;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import AnswerClasses.Book;
import AnswerClasses.User;

public class ReadBooksActivity extends AppCompatActivity {

    private static final String USER_INFO = "User Info";
    private static final String READ_BOOKS_ARRAY = "Read Books Array";
    private ArrayList<Book> books;
    private User userInfo;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //Comunication
    ConnectionService mBoundService;
    private boolean mIsBound;

    // TODO CALL on Book... see if service bind needed

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
        bindService(new Intent(ReadBooksActivity.this, ConnectionService.class), mConnection,
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

            //if (key.equals(Constants.GET_ALL_LIBRARIES)) {

            //}
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        userInfo = (User) getIntent().getSerializableExtra(USER_INFO);
        books = (ArrayList<Book>) getIntent().getSerializableExtra(READ_BOOKS_ARRAY);

        mRecyclerView = (RecyclerView) findViewById (R.id.list_recycle);
        // improve performance
        mRecyclerView.setHasFixedSize (true);
        // used linear layout
        mLayoutManager = new GridLayoutManager (this, 3);
        mRecyclerView.setLayoutManager (mLayoutManager);
        mRecyclerView.setItemAnimator (new DefaultItemAnimator ());
        // specify an adapter
        mAdapter = new ReadBooksAdapter (this, books);
        mRecyclerView.setAdapter (mAdapter);

        // Communication
        doBindService();
        // TODO : calls on item click
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_ALL_LIBRARIES));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Communication
        doBindService();
        //this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_ALL_LIBRARIES));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        doUnbindService();
        unregisterReceiver(mMessageReceiver);
    }
}
