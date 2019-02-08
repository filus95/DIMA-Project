package com.easylib.dima.easylib_librarian.Activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easylib.dima.easylib_librarian.Adapters.ImageTitleBookAdapter;
import com.easylib.dima.easylib_librarian.Adapters.ImageTitleEventAdapter;
import com.easylib.dima.easylib_librarian.Adapters.ImageTitleNewsAdapter;
import com.easylib.dima.easylib_librarian.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib_librarian.ConnectionLayer.Constants;
import com.easylib.dima.easylib_librarian.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import AnswerClasses.Book;
import AnswerClasses.Event;
import AnswerClasses.LibraryDescriptor;
import AnswerClasses.News;
import AnswerClasses.User;

public class LibraryActivity extends AppCompatActivity {

    // Needed
    private static final String USER_INFO = "User Info";
    private static final String LOGIN = "Login";
    private static final String LIBRARY_INFO = "Library Info";
    private User userInfo;
    private LibraryDescriptor libraryInfo;

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
    private FloatingActionButton scanButton;
    private Button logoutButton;

    RecyclerView.LayoutManager mLayoutManager;

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
        bindService(new Intent(LoginActivity.this, ConnectionService.class), mConnection,
                Context.BIND_AUTO_CREATE);
        mIsBound = true;
        if(mBoundService!=null){
            mBoundService.IsBoundable();
        }
    }

    private void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
        unregisterReceiver(mMessageReceiver);
    }

    //This is the handler that will manager to process the broadcast intent
    //This has to be created inside each activity that needs it ( almost anyone )
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String key = extractKey(intent);

            if ( key.equals(Constants.QUERY_ON_BOOKS)) {
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
        setContentView(R.layout.library);

        userInfo = (User) getIntent().getSerializableExtra(USER_INFO);
        libraryInfo = (LibraryDescriptor) getIntent().getSerializableExtra(LIBRARY_INFO);

        // Communication
        doBindService();
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.QUERY_ON_BOOKS));

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
        scanButton = (FloatingActionButton) findViewById(R.id.library_activity_scan_button);
        logoutButton = (Button) findViewById (R.id.library_activity_logout_button);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToScanActivity ();
            }
        });

        // get 6 elements of each arrayList news, events, books
        int i;
        int size = 0;
        // set size based on orientation
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            size = 6;
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            size = 3;
        }
        ArrayList<News> newsList = new ArrayList<News>();
        ArrayList<Event> eventsList = new ArrayList<Event>();
        ArrayList<Book> booksList = new ArrayList<Book>();
        for(i=0; i<size; i++) {
            newsList.add(libraryInfo.getLibraryContent().getNews().get(i));
            eventsList.add(libraryInfo.getLibraryContent().getEvents().get(i));
            booksList.add(libraryInfo.getLibraryContent().getBooks().get(i));
        }

        // setup recycleViews
        newsRec.setHasFixedSize(true);
        eventsRec.setHasFixedSize(true);
        booksRec.setHasFixedSize(true);
        // used grid layout
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // used linear layout
            mLayoutManager = new GridLayoutManager(this, 6);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // used grid layout
            mLayoutManager = new GridLayoutManager(this, 3);
        }
        newsRec.setLayoutManager(mLayoutManager);
        newsRec.setItemAnimator(new DefaultItemAnimator());
        eventsRec.setLayoutManager(mLayoutManager);
        eventsRec.setItemAnimator(new DefaultItemAnimator());
        booksRec.setLayoutManager(mLayoutManager);
        booksRec.setItemAnimator(new DefaultItemAnimator());
        // specify adapters
        ImageTitleNewsAdapter newsAdapter = new ImageTitleNewsAdapter(this, newsList);
        newsRec.setAdapter(newsAdapter);
        ImageTitleEventAdapter eventsAdapter = new ImageTitleEventAdapter(this, eventsList);
        eventsRec.setAdapter(eventsAdapter);
        ImageTitleBookAdapter booksAdapter = new ImageTitleBookAdapter(this, booksList);
        booksRec.setAdapter(booksAdapter);
    }

    public void goToScanActivity() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            // handle scan result
        }
        // else continue with any other code you need in the method
    }

    public void logout(View view) {
        SharedPreferences sp = this.getSharedPreferences(LOGIN, MODE_PRIVATE);
        sp.edit().clear().apply();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        doUnbindService ();
        startActivity(intent);
    }
}
