package com.easylib.dima.easylib.Lists;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.easylib.dima.easylib.Adapters.BookAdapter;
import com.easylib.dima.easylib.Model.Book;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

public class ListBooks extends AppCompatActivity {

    private ArrayList<Book> books = new ArrayList<Book>();

    // recycle view
    private RecyclerView mRecyclerView;
    private BookAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        int i;
        for(i=0; i<15; i++) {
            books.add(new Book("Book Title"+i,"Book Author "+i, "https://upload.wikimedia.org/wikipedia/en/thumb/7/70/Brisingr_book_cover.png/220px-Brisingr_book_cover.png", i));
        }

        // Recycle View Settings
        mRecyclerView = (RecyclerView) findViewById(R.id.book_list_recycle);
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
