package com.easylib.dima.easylib.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.ConnectionLayer.Constants;
import com.easylib.dima.easylib.R;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import AnswerClasses.News;

public class NewsActivity extends AppCompatActivity {

    private static final String NEWS_INFO = "News Info";
    private News news;

    private TextView title;
    private TextView date;
    private TextView description;
    private ImageView image;

    //This is the handler that will manager to process the broadcast intent
    //This has to be created inside each activity that needs it ( almost anyone )
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String key = extractKey(intent);

            if (key.equals(Constants.NETWORK_STATE_DOWN)){
                Intent internetIntent = new Intent (context, NoInternetActivity.class);
                startActivity (internetIntent);
            }
        }
    };

    private String extractKey(Intent intent){
        Set<String> keySet = Objects.requireNonNull(intent.getExtras()).keySet();
        Iterator iterator = keySet.iterator();
        return (String)iterator.next();
    }

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

    @Override
    protected void onResume() {
        super.onResume ();
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.NETWORK_STATE_DOWN));
    }

    @Override
    protected void onPause() {
        super.onPause ();
        unregisterReceiver(mMessageReceiver);
    }
}
