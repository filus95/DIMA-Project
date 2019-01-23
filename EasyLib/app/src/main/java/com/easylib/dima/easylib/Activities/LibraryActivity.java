package com.easylib.dima.easylib.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.easylib.dima.easylib.Adapters.ImageTitleNewsAdapter;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

import AnswerClasses.Book;
import AnswerClasses.Event;
import AnswerClasses.LibraryDescriptor;
import AnswerClasses.News;

public class LibraryActivity extends AppCompatActivity {

    private LibraryDescriptor library;
    private Boolean isUserFavourite;

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

        // get 3 elements of each arrayList news, events, books
        int i;
        ArrayList<News> newsList = new ArrayList<News>();
        ArrayList<Event> eventsList = new ArrayList<Event>();
        ArrayList<Book> booksList = new ArrayList<Book>();
        for(i=0; i<3; i++) {
            newsList.add(library.getLibraryContent().getNews().get(i));
            eventsList.add(library.getLibraryContent().getEvents().get(i));
            booksList.add(library.getLibraryContent().getBooks().get(i));
        }

        // setup recycleViews
        newsRec.setHasFixedSize(true);
        eventsRec.setHasFixedSize(true);
        booksRec.setHasFixedSize(true);
        // used grid layout
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        newsRec.setLayoutManager(mLayoutManager);
        newsRec.setItemAnimator(new DefaultItemAnimator());
        eventsRec.setLayoutManager(mLayoutManager);
        eventsRec.setItemAnimator(new DefaultItemAnimator());
        booksRec.setLayoutManager(mLayoutManager);
        booksRec.setItemAnimator(new DefaultItemAnimator());
        // specify adapters
        ImageTitleNewsAdapter newsAdapter = new ImageTitleNewsAdapter(this, newsList);
        newsRec.setAdapter(newsAdapter);
        ImageTitleNewsAdapter eventsAdapter = new ImageTitleNewsAdapter(this, eventsList);
        eventsRec.setAdapter(eventsAdapter);
        ImageTitleNewsAdapter booksAdapter = new ImageTitleNewsAdapter(this, booksList);
        booksRec.setAdapter(booksAdapter);

        // setPreference Button setup based on the fact that library is already a favourite or not
        if(!isUserFavourite) {
            setFavourite.setText("Remove from Favourite");
            // TODO : set color red
            setFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFavourite();
                }
            });
        } else {
            setFavourite.setText("Set Favourite");
            // TODO : set color yellow
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
