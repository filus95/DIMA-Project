package com.easylib.dima.easylib_librarian.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib_librarian.Adapters.BookReservationsAdapter;
import com.easylib.dima.easylib_librarian.R;

import java.util.ArrayList;

import AnswerClasses.Book;
import AnswerClasses.User;

public class BookActivity extends AppCompatActivity {

    private Book book;
    private ArrayList<User> reservations;

    private TextView title;
    private TextView authors;
    private TextView avgRate;
    private TextView description;
    private ImageView image;

    // recycle view
    private RecyclerView mRecyclerView;
    private BookReservationsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library);

        // get components
        title = (TextView) findViewById(R.id.book_activity_title);
        authors = (TextView) findViewById(R.id.book_activity_authors);
        description = (TextView) findViewById(R.id.book_activity_description);
        avgRate = (TextView) findViewById(R.id.book_activity_average_rate);
        image = (ImageView) findViewById(R.id.book_activity_image);

        // set the components
        title.setText(book.getTitle());
        for(String author : book.getAuthors()){
            authors.setText(authors + ", " + author);
        }
        description.setText(book.getDescription());
        avgRate.setText(book.getAverageRating().intValue());
        Glide.with(this)
                .load(book.getImageLink())
                .into(image);

        // TODO : make call to get RESERVATIONS

        // Recycle View Settings
        mRecyclerView = (RecyclerView) findViewById(R.id.book_activity_reservations_recycler);
        mRecyclerView.setHasFixedSize(true);
        // used linear layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // specify an adapter
        mAdapter = new BookReservationsAdapter(this, reservations);
        mRecyclerView.setAdapter(mAdapter);
    }
}
