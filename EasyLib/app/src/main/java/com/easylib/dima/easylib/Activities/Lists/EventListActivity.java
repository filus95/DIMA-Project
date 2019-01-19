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

public class EventListActivity extends AppCompatActivity {

    private ArrayList<Event> events = new ArrayList<Event>();

    // recycle view
    private RecyclerView mRecyclerView;
    private EventAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        // JUST FOR TEST
        int i;
        for(i=0; i<15; i++) {
            Event event = new Event();
            event.setImage_link("https://upload.wikimedia.org/wikipedia/en/thumb/7/70/Brisingr_book_cover.png/220px-Brisingr_book_cover.png");
            event.setTitle("Title of the news "+i);
            event.setDate(LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30));
            events.add(event);
        }

        // Recycle View Settings
        mRecyclerView = (RecyclerView) findViewById(R.id.list_recycle);
        mRecyclerView.setHasFixedSize(true);
        // used linear layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // specify an adapter
        mAdapter = new EventAdapter(this, events);
        mRecyclerView.setAdapter(mAdapter);
    }
}
