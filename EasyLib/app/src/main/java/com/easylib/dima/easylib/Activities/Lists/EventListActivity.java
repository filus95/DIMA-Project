package com.easylib.dima.easylib.Activities.Lists;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.easylib.dima.easylib.Adapters.EventAdapter;
import com.easylib.dima.easylib.R;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import AnswerClasses.Event;
import AnswerClasses.User;

public class EventListActivity extends AppCompatActivity {

    private static final String ALL_EVENTS = "All Events";
    private static final String USER_INFO = "User Info";
    AnswerClasses.User userInfo;
    private ArrayList<Event> events;

    // recycle view
    private RecyclerView mRecyclerView;
    private EventAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        events = (ArrayList<Event>) getIntent().getSerializableExtra(ALL_EVENTS);
        userInfo = (User) getIntent ().getSerializableExtra (USER_INFO);

        // Recycle View Settings
        mRecyclerView = (RecyclerView) findViewById(R.id.list_recycle);
        mRecyclerView.setHasFixedSize(true);
        // used linear layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // specify an adapter
        mAdapter = new EventAdapter(this, events, userInfo);
        mRecyclerView.setAdapter(mAdapter);
    }
}
