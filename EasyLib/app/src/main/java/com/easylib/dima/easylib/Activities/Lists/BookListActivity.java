package com.easylib.dima.easylib.Activities.Lists;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.easylib.dima.easylib.Adapters.BookAdapter;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {

    private ArrayList<AnswerClasses.Book> books = new ArrayList<AnswerClasses.Book>();

    // recycle view
    private RecyclerView mRecyclerView;
    private BookAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        // Recycle View Settings
        mRecyclerView = (RecyclerView) findViewById(R.id.list_recycle);
        mRecyclerView.setHasFixedSize(true);
        // used linear layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // specify an adapter
        mAdapter = new BookAdapter(this, books);
        mRecyclerView.setAdapter(mAdapter);
    }
}
