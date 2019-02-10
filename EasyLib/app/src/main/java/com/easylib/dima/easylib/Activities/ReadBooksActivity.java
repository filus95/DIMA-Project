package com.easylib.dima.easylib.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.easylib.dima.easylib.Adapters.ReadBooksAdapter;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

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
    }
}
