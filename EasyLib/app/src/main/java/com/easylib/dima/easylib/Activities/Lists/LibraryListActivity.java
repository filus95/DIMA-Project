package com.easylib.dima.easylib.Activities.Lists;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.easylib.dima.easylib.Adapters.LibraryAdapter;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

import AnswerClasses.LibraryDescriptor;
import AnswerClasses.User;

public class LibraryListActivity extends AppCompatActivity {

    private static final String ALL_LIBRARIES_LIST = "All Libraries List";
    private static final String USER_INFO = "User Info";
    private ArrayList<LibraryDescriptor> libraries = new ArrayList<LibraryDescriptor>();
    private User userInfo;

    // recycle view
    private RecyclerView mRecyclerView;
    private LibraryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        userInfo = (User) getIntent().getSerializableExtra(USER_INFO);
        libraries = (ArrayList<LibraryDescriptor>) getIntent().getSerializableExtra(ALL_LIBRARIES_LIST);

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
}
