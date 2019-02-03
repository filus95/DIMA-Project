package com.easylib.dima.easylib.Activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib.ConnectionLayer.Constants;
import com.easylib.dima.easylib.R;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import AnswerClasses.LibraryDescriptor;
import AnswerClasses.News;

public class NewsActivity extends AppCompatActivity {

    private static final String NEWS_INFO = "News Info";
    private News news;

    private TextView title;
    private TextView location;
    private TextView date;
    private TextView description;
    private ImageView image;

    //Comunication
    ConnectionService mBoundService;
    private boolean mIsBound;

    //For the communication Service
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBoundService = ((ConnectionService.LocalBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundService = null;
        }
    };

    public void doBindService() {
        bindService(new Intent (NewsActivity.this, ConnectionService.class), mConnection,
                Context.BIND_AUTO_CREATE);
        mIsBound = true;
        if(mBoundService!=null){
            mBoundService.IsBoundable();
        }
    }

    public void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
        unregisterReceiver(mMessageReceiver);
    }

    private String extractKey(Intent intent){
        Set<String> keySet = Objects.requireNonNull(intent.getExtras()).keySet();
        Iterator iterator = keySet.iterator();
        return (String)iterator.next();
    }

    //This is the handler that will manager to process the broadcast intent
    //This has to be created inside each activity that needs it ( almost anyone )
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String key = extractKey(intent);

            if (key.equals(Constants.GET_LIBRARY_INFO)) {
                LibraryDescriptor library = (LibraryDescriptor) intent.getSerializableExtra (Constants.GET_LIBRARY_INFO);
                location.setText (library.getAddress ());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        // Communication
        doBindService();
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.GET_LIBRARY_INFO));

        news = (News) getIntent ().getSerializableExtra(NEWS_INFO);

        // get components
        title = (TextView) findViewById(R.id.news_activity_title);
        location = (TextView) findViewById(R.id.news_activity_location);
        date = (TextView) findViewById(R.id.news_activity_date);
        description = (TextView) findViewById(R.id.news_activity_description);
        image = (ImageView) findViewById(R.id.news_activity_image);

        // set the components
        title.setText(news.getTitle());
        date.setText(news.getPost_date().toString());
        description.setText(news.getContent());
        Glide.with(this)
                .load(news.getImage_link())
                .into(image);

        // Get user Preferences from Server to see if this library is Prefered
        new Handler ().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mBoundService != null) {
                    mBoundService.setCurrentContext(getApplicationContext());
                    mBoundService.sendMessage(Constants.GET_LIBRARY_INFO, news.getIdLib ());
                }
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        doUnbindService();
    }
}
