package com.easylib.dima.easylib.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.easylib.dima.easylib.Adapters.ReadBooksAdapter;
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

    //This is the handler that will manager to process the broadcast intent
    //This has to be created inside each activity that needs it ( almost anyone )
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String key = extractKey(intent);

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
        setContentView(R.layout.list_activity);

        userInfo = (User) getIntent().getSerializableExtra(USER_INFO);
        books = (ArrayList<Book>) getIntent().getSerializableExtra(READ_BOOKS_ARRAY);

        mRecyclerView = (RecyclerView) findViewById (R.id.list_recycle);
        // improve performance
        mRecyclerView.setHasFixedSize (true);
        // Set Layout
        if ((getResources ().getConfiguration ().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            mLayoutManager = new GridLayoutManager (this, 3);
        } else if ((getResources ().getConfiguration ().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            mLayoutManager = new GridLayoutManager (this, 6);
        }
        mRecyclerView.setLayoutManager (mLayoutManager);
        mRecyclerView.setItemAnimator (new DefaultItemAnimator ());
        // specify an adapter
        mAdapter = new ReadBooksAdapter (this, books, userInfo);
        mRecyclerView.setAdapter (mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume ();
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.NETWORK_STATE_DOWN));
    }

    @Override
    protected void onPause() {
        super.onPause ();
        unregisterReceiver(mMessageReceiver);
    }
}
