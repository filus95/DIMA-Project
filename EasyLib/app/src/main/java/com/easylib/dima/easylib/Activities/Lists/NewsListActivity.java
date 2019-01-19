package com.easylib.dima.easylib.Activities.Lists;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.easylib.dima.easylib.Adapters.NewsAdapter;
import com.easylib.dima.easylib.R;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import AnswerClasses.News;

public class NewsListActivity extends AppCompatActivity {

    private ArrayList<News> newsList = new ArrayList<News>();

    // recycle view
    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        // JUST FOR TEST
        int i;
        for(i=0; i<15; i++) {
            News news = new News();
            news.setImage_link("https://upload.wikimedia.org/wikipedia/en/thumb/7/70/Brisingr_book_cover.png/220px-Brisingr_book_cover.png");
            news.setTitle("Title of the news "+i);
            news.setPost_date(LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30));
            newsList.add(news);
        }

        // Recycle View Settings
        mRecyclerView = (RecyclerView) findViewById(R.id.list_recycle);
        mRecyclerView.setHasFixedSize(true);
        // used linear layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // specify an adapter
        mAdapter = new NewsAdapter(this, newsList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
