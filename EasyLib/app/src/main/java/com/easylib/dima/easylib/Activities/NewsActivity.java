package com.easylib.dima.easylib.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.R;

import AnswerClasses.News;

public class NewsActivity extends AppCompatActivity {

    private static final String NEWS_INFO = "News Info";
    private News news;

    private TextView title;
    private TextView date;
    private TextView description;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        news = (News) getIntent ().getSerializableExtra(NEWS_INFO);

        // get components
        title = (TextView) findViewById(R.id.news_activity_title);
        date = (TextView) findViewById(R.id.news_activity_date);
        description = (TextView) findViewById(R.id.news_activity_description);
        image = (ImageView) findViewById(R.id.news_activity_image);

        // set the components
        title.setText(news.getTitle());
        date.setText(news.getPost_date().toString().replace ("T", "  "));
        description.setText(news.getContent());
        Glide.with(this)
                .load(news.getImage_link())
                .into(image);
    }
}
