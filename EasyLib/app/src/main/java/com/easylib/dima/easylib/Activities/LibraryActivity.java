package com.easylib.dima.easylib.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.Adapters.ImageTitleBookAdapter;
import com.easylib.dima.easylib.Adapters.ImageTitleEventAdapter;
import com.easylib.dima.easylib.Adapters.ImageTitleNewsAdapter;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

import AnswerClasses.Book;
import AnswerClasses.Event;
import AnswerClasses.LibraryDescriptor;
import AnswerClasses.News;
import AnswerClasses.User;

public class LibraryActivity extends AppCompatActivity {

    private static final String LIBRARY_IS_PREFERITE = "Library is Preferite";
    private static final String USER_INFO = "User Info";
    private static final String LIBRARY_INFO = "Library Info";
    private LibraryDescriptor libraryInfo;
    private User userInfo;
    private Boolean isLibraryFavourite;

    // Layout Components
    private TextView name;
    private TextView location;
    private ImageView image;
    private TextView description;
    private TextView email;
    private TextView phone;
    private RecyclerView newsRec;
    private Button newsButton;
    private RecyclerView eventsRec;
    private Button eventsButton;
    private RecyclerView booksRec;
    private Button booksButton;
    private Button setFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_activity);

        userInfo = (User) getIntent().getSerializableExtra(USER_INFO);
        libraryInfo = (LibraryDescriptor) getIntent().getSerializableExtra(LIBRARY_INFO);
        isLibraryFavourite = (Boolean) getIntent().getSerializableExtra(LIBRARY_IS_PREFERITE);

        // get layout components references
        name = (TextView) findViewById(R.id.library_activity_name);
        location = (TextView) findViewById(R.id.library_activity_location);
        image = (ImageView) findViewById(R.id.library_activity_image);
        description = (TextView) findViewById(R.id.library_activity_description);
        email = (TextView) findViewById(R.id.library_activity_email);
        phone = (TextView) findViewById(R.id.library_activity_phone);
        newsRec = (RecyclerView) findViewById(R.id.library_activity_news_recycle);
        newsButton = (Button) findViewById(R.id.library_activity_all_news_button);
        eventsRec = (RecyclerView) findViewById(R.id.library_activity_events_recycle);
        eventsButton = (Button) findViewById(R.id.library_activity_all_events_button);
        booksRec = (RecyclerView) findViewById(R.id.library_activity_books_recycle);
        booksButton = (Button) findViewById(R.id.library_activity_all_books_button);
        setFavourite = (Button) findViewById(R.id.library_activity_fav_lib_button);

        // Setup Library Info
        name.setText(libraryInfo.getLib_name());
        location.setText(libraryInfo.getAddress());
        Glide.with(this)
                .load(libraryInfo.getImage_link())
                .into(image);
        description.setText(libraryInfo.getDescription());
        email.setText(libraryInfo.getEmail());
        phone.setText(libraryInfo.getTelephone_number());

        // get 3 elements of each arrayList news, events, books
        int i;
        ArrayList<News> newsList = new ArrayList<News>();
        ArrayList<Event> eventsList = new ArrayList<Event>();
        ArrayList<Book> booksList = new ArrayList<Book>();
        for(i=0; i<3; i++) {
            newsList.add(libraryInfo.getLibraryContent().getNews().get(i));
            eventsList.add(libraryInfo.getLibraryContent().getEvents().get(i));
            booksList.add(libraryInfo.getLibraryContent().getBooks().get(i));
        }

        // setup recycleViews
        newsRec.setHasFixedSize(true);
        eventsRec.setHasFixedSize(true);
        booksRec.setHasFixedSize(true);
        // used grid layout
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        newsRec.setLayoutManager(mLayoutManager);
        newsRec.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(this, 3);
        eventsRec.setLayoutManager(mLayoutManager2);
        eventsRec.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager mLayoutManager3 = new GridLayoutManager(this, 3);
        booksRec.setLayoutManager(mLayoutManager3);
        booksRec.setItemAnimator(new DefaultItemAnimator());
        // specify adapters
        ImageTitleNewsAdapter newsAdapter = new ImageTitleNewsAdapter(this, newsList);
        newsRec.setAdapter(newsAdapter);
        ImageTitleEventAdapter eventsAdapter = new ImageTitleEventAdapter(this, eventsList);
        eventsRec.setAdapter(eventsAdapter);
        ImageTitleBookAdapter booksAdapter = new ImageTitleBookAdapter(this, booksList);
        booksRec.setAdapter(booksAdapter);

        // setPreference Button setup based on the fact that library is already a favourite or not
        if(!isLibraryFavourite) {
            setFavourite.setText("Set Favourite");
            setFavourite.setTextColor(Color.GREEN);
            setFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFavourite();
                }
            });
        } else {
            setFavourite.setText("Remove Favourite");
            setFavourite.setTextColor(Color.RED);
            setFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeFromFavourites();
                }
            });
        }
    }

    public void setFavourite() {
        // TODO : call set favourite
    }

    public void removeFromFavourites() {
        // TODO : call removeFromFavourites
    }
}
