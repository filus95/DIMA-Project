package com.easylib.dima.easylib.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.Adapters.BookAvailableLibAdapter;
import com.easylib.dima.easylib.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import AnswerClasses.Book;

public class BookActivity extends AppCompatActivity {

    private static final String BOOK_INFO = "Book Info";
    private static final String USER_INFO = "User Info";
    private Book bookInfo;
    private AnswerClasses.User userInfo;

    private TextView title;
    private TextView authors;
    private TextView avgRate;
    private TextView description;
    private EditText userRate;
    private ImageView image;

    private boolean reservedByMe;
    private String libIdReservedByMe;
    private boolean meOnWaitingList;

    // recycle view
    private RecyclerView mRecyclerView;
    private BookAvailableLibAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        userInfo = (AnswerClasses.User) getIntent().getSerializableExtra(USER_INFO);
        bookInfo = (Book) getIntent().getSerializableExtra(BOOK_INFO);

        // get components
        title = (TextView) findViewById(R.id.book_activity_title);
        authors = (TextView) findViewById(R.id.book_activity_authors);
        description = (TextView) findViewById(R.id.book_activity_description);
        avgRate = (TextView) findViewById(R.id.book_activity_average_rate);
        userRate = (EditText) findViewById(R.id.book_activity_your_rate);
        image = (ImageView) findViewById(R.id.book_activity_image);

        // set the components
        title.setText(bookInfo.getTitle());
        for(String author : bookInfo.getAuthors()){
            authors.setText(authors + ", " + author);
        }
        description.setText(bookInfo.getDescription());
        avgRate.setText(bookInfo.getAverageRating().intValue());
        Glide.with(this)
                .load(bookInfo.getImageLink())
                .into(image);

        // Recycle View Settings
        mRecyclerView = (RecyclerView) findViewById(R.id.book_activity_lib_recycle);
        mRecyclerView.setHasFixedSize(true);
        // used linear layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // specify an adapter
        // TODO : change adapter construction
        mAdapter = new BookAvailableLibAdapter(this, true, "string", new ArrayList<Book>());
        mRecyclerView.setAdapter(mAdapter);
    }
}
