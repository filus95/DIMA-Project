package com.easylib.dima.easylib.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.R;

import java.time.LocalDateTime;
import java.time.Month;

import AnswerClasses.News;

public class NewsActivity extends AppCompatActivity {

    private News news;

    private TextView title;
    private TextView location;
    private TextView date;
    private TextView description;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        // JUST FOR TEST
        news = new News();
        news.setImage_link("https://upload.wikimedia.org/wikipedia/en/thumb/7/70/Brisingr_book_cover.png/220px-Brisingr_book_cover.png");
        news.setTitle("Title Event");
        news.setContent("skjdnfkjdnf dsjfdsjk fdsf jkds fkjds fkds fksjd f");
        news.setPost_date(LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30));

        // get components
        title = (TextView) findViewById(R.id.news_activity_title);
        location = (TextView) findViewById(R.id.news_activity_location);
        date = (TextView) findViewById(R.id.news_activity_date);
        description = (TextView) findViewById(R.id.news_activity_description);
        image = (ImageView) findViewById(R.id.news_activity_image);

        // set the components
        title.setText(news.getTitle());
        location.setText("via non la so (MI)");
        date.setText(news.getPost_date().toString());
        description.setText(news.getContent());
        Glide.with(this)
                .load(news.getImage_link())
                .into(image);
    }
}
