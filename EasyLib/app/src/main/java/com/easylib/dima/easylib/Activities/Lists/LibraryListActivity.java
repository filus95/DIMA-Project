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
    private ArrayList<LibraryDescriptor> libraries;
    private User userInfo;
    private static final String LIBRARY_INFO = "Library Info";

    // recycle view
    private RecyclerView mRecyclerView;
    private LibraryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        userInfo = (User) getIntent().getSerializableExtra(USER_INFO);
        libraries = (ArrayList<LibraryDescriptor>) getIntent().getSerializableExtra(LIBRARIES_LIST);

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

    public void showLibrary(LibraryDescriptor library) {
        Intent libraryIntent = new Intent(this, LibraryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(LIBRARY_INFO, library);
        bundle.putSerializable(USER_INFO, userInfo);
        libraryIntent.putExtras(bundle);
        startActivity(libraryIntent);
    }
}
