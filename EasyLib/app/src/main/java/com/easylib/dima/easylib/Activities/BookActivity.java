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

    private Book book;

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

        // JUST FOR TEST
        book = new Book();
        book.setTitle("Brisingr");
        book.setAuthors("author 1", "author 2", "", "");
        book.setDescription("sdjkfkjsd dskfndsjkf dskfnjsdk fdsf ndsfjds fjnds fndsl fdsln fds");
        book.setImageLink("https://upload.wikimedia.org/wikipedia/en/thumb/7/70/Brisingr_book_cover.png/220px-Brisingr_book_cover.png");
        book.setAverageRating(7.5f);

        // get components
        title = (TextView) findViewById(R.id.book_activity_title);
        authors = (TextView) findViewById(R.id.book_activity_authors);
        description = (TextView) findViewById(R.id.book_activity_description);
        avgRate = (TextView) findViewById(R.id.book_activity_average_rate);
        userRate = (EditText) findViewById(R.id.book_activity_your_rate);
        image = (ImageView) findViewById(R.id.book_activity_image);

        // TODO : make checks on servations and waitingList
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
        // TODO : make call on user ratings and set userRating

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
