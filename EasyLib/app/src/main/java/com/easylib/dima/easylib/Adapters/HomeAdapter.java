package com.easylib.dima.easylib.Adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.easylib.dima.easylib.Activities.Fragments.MainActivity;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

import AnswerClasses.Book;
import AnswerClasses.Event;
import AnswerClasses.LibraryDescriptor;
import AnswerClasses.News;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder> {

    private static final String USER_INFO = "User Info";
    AnswerClasses.User userInfo;
    ArrayList<LibraryDescriptor> libraries;
    Context context;
    private int size = 0;

    public HomeAdapter(Context context, ArrayList libraries, AnswerClasses.User userInfo) {
        this.context = context;
        this.libraries = libraries;
        this.userInfo = userInfo;
    }

    @Override
    public HomeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_home_item, parent, false);
        return new HomeHolder(v);
    }

    @Override
    public void onBindViewHolder(HomeHolder holder, int position) {
        // set the data in items
        LibraryDescriptor library = libraries.get(position);

        holder.libraryTitle.setText(library.getLib_name());

        // check screen size
        if ((context.getResources ().getConfiguration ().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            size = 3;
        } else if ((context.getResources ().getConfiguration ().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            size = 6;
        }

        // get first 3 items of news, events, books
        int i;
        ArrayList<News> news = new ArrayList<News>();
        ArrayList<Event> events = new ArrayList<Event>();
        ArrayList<Book> books = new ArrayList<Book>();
        for(i=0; i<size; i++) {
            if (i < library.getLibraryContent ().getNews ().size ()) {
                news.add (library.getLibraryContent ().getNews ().get (i));
            }
            if (i < library.getLibraryContent ().getEvents ().size ()) {
                events.add (library.getLibraryContent ().getEvents ().get (i));
            }
            if (i < library.getLibraryContent ().getBooks ().size ()) {
                books.add (library.getLibraryContent ().getBooks ().get (i));
            }
        }

        // Set idLib to all News
        for (Event event : library.getLibraryContent ().getEvents ()){
            event.setIdLib (library.getId_lib ());
        }

        // improve performance
        holder.recycleNews.setHasFixedSize(true);
        holder.recycleEvent.setHasFixedSize(true);
        holder.recycleBooks.setHasFixedSize(true);
        // used grid layout
        RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(context, size);
        holder.recycleNews.setLayoutManager(mLayoutManager1);
        holder.recycleNews.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(context, size);
        holder.recycleEvent.setLayoutManager(mLayoutManager2);
        holder.recycleEvent.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager mLayoutManager3 = new GridLayoutManager(context, size);
        holder.recycleBooks.setLayoutManager(mLayoutManager3);
        holder.recycleBooks.setItemAnimator(new DefaultItemAnimator());
        // specify adapters
        ImageTitleNewsAdapter newsAdapter = new ImageTitleNewsAdapter(context, news);
        holder.recycleNews.setAdapter(newsAdapter);
        ImageTitleEventAdapter eventsAdapter = new ImageTitleEventAdapter(context, events, userInfo);
        holder.recycleEvent.setAdapter(eventsAdapter);
        ImageTitleBookAdapter booksAdapter = new ImageTitleBookAdapter(context, books, userInfo);
        holder.recycleBooks.setAdapter(booksAdapter);

        // set Button click listener
        // implemented onClickListener event
        holder.libraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).showLibrary(library);
            }
        });
    }

    @Override
    public int getItemCount() { return libraries.size(); }

    static class HomeHolder extends RecyclerView.ViewHolder {
        protected TextView libraryTitle;
        protected RecyclerView recycleNews;
        protected RecyclerView recycleEvent;
        protected RecyclerView recycleBooks;
        protected Button libraryButton;

        public HomeHolder(View v) {
            super(v);
            libraryTitle = v.findViewById(R.id.fragment_home_item_library_name);
            recycleNews = v.findViewById(R.id.fragment_home_item_recycle_news);
            recycleEvent = v.findViewById(R.id.fragment_home_item_recycle_event);
            recycleBooks = v.findViewById(R.id.fragment_home_item_recycle_book);
            libraryButton = v.findViewById(R.id.fragment_home_item_recycle_button);
        }
    }
}
