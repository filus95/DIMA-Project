package com.easylib.dima.easylib.Activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.Activities.Lists.EventListActivity;
import com.easylib.dima.easylib.Activities.Lists.NewsListActivity;
import com.easylib.dima.easylib.Activities.Login.LoginPreferenceActivity;
import com.easylib.dima.easylib.Adapters.ImageTitleBookAdapter;
import com.easylib.dima.easylib.Adapters.ImageTitleEventAdapter;
import com.easylib.dima.easylib.Adapters.ImageTitleNewsAdapter;
import com.easylib.dima.easylib.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib.ConnectionLayer.Constants;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import AnswerClasses.Book;
import AnswerClasses.Event;
import AnswerClasses.LibraryDescriptor;
import AnswerClasses.News;
import AnswerClasses.User;
import AnswerClasses.UserPreferences;

public class LibraryActivity extends AppCompatActivity {

    private static final String USER_INFO = "User Info";
    private static final String LIBRARY_INFO = "Library Info";
    private LibraryDescriptor libraryInfo;
    private User userInfo;
    private Boolean isLibraryFavourite;

    // For List Activities
    private static final String ALL_NEWS = "All News";
    private static final String ALL_EVENTS = "All Events";
    private static final String ALL_BOOKS = "All Books";

    //Comunication
    ConnectionService mBoundService;
    private boolean mIsBound;

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
        bindService(new Intent (LibraryActivity.this, ConnectionService.class), mConnection,
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

            if (key.equals (Constants.GET_LIBRARY_INFO)) {
                libraryInfo = (LibraryDescriptor) intent.getSerializableExtra (Constants.GET_LIBRARY_INFO);
                setAdapters ();
                // Get user Preferences from Server to see if this library is Prefered
                if (mBoundService != null) {
                    mBoundService.setCurrentContext(getApplicationContext());
                    mBoundService.sendMessage(Constants.GET_USER_PREFERENCES, userInfo.getUser_id ());
                }
            }
            if (key.equals(Constants.GET_USER_PREFERENCES)) {
                ArrayList<LibraryDescriptor> prefLibraries = (ArrayList<LibraryDescriptor>) intent.getSerializableExtra(Constants.GET_USER_PREFERENCES);
                Boolean libraryIsPref;
                libraryIsPref = false;
                for (LibraryDescriptor library : prefLibraries) {
                    if (library.getId_lib() == libraryInfo.getId_lib())
                        libraryIsPref = true;
                }
                setFavouriteButton(libraryIsPref);
            }
            if (key.equals(Constants.INSERT_PREFERENCE)) {
                Boolean bool = (Boolean) intent.getSerializableExtra (Constants.INSERT_PREFERENCE);
                if (bool) {
                    Toast.makeText (context, "Library Added to Favourite", Toast.LENGTH_LONG).show ();
                    setFavourite.setText("Remove Favourite");
                    setFavourite.setTextColor(Color.RED);
                    setFavourite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            removeFromFavourites();
                        }
                    });
                } else
                    Toast.makeText (context, "ERROR..", Toast.LENGTH_LONG).show ();
            }
            if (key.equals(Constants.EDIT_PROFILE)) {
                Boolean bool = (Boolean) intent.getSerializableExtra (Constants.EDIT_PROFILE);
                if (bool) {
                    Toast.makeText (context, "Library Removed from Favourite", Toast.LENGTH_LONG).show ();
                    setFavourite.setText("Set Favourite");
                    setFavourite.setTextColor(Color.GREEN);
                    setFavourite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setFavourite();
                        }
                    });
                } else
                    Toast.makeText (context, "ERROR..", Toast.LENGTH_LONG).show ();
            } if (key.equals(Constants.NOTIFICATION)){
                String message = (String) intent.getSerializableExtra(Constants.NOTIFICATION);
                Toast.makeText(context,message, Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_activity);

        userInfo = (User) getIntent().getSerializableExtra(USER_INFO);
        libraryInfo = (LibraryDescriptor) getIntent().getSerializableExtra(LIBRARY_INFO);

        // Communication
        doBindService();
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.GET_USER_PREFERENCES));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.INSERT_PREFERENCE));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.EDIT_PROFILE));
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.NOTIFICATION));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_LIBRARY_INFO));

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Communication
        doBindService();
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_USER_PREFERENCES));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.INSERT_PREFERENCE));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.EDIT_PROFILE));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_LIBRARY_INFO));

        // Get Library info from Server
        new Handler ().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mBoundService != null) {
                    mBoundService.setCurrentContext(getApplicationContext());
                    mBoundService.sendMessage(Constants.GET_LIBRARY_INFO, libraryInfo.getId_lib ());
                }
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        doUnbindService();
    }

    public void setAdapters() {
        // Set idLib to all News
        for (Event event : libraryInfo.getLibraryContent ().getEvents ()){
            event.setIdLib (libraryInfo.getId_lib ());
        }

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

        // specify adapters
        ImageTitleNewsAdapter newsAdapter = new ImageTitleNewsAdapter(this, newsList);
        newsRec.setAdapter(newsAdapter);
        ImageTitleEventAdapter eventsAdapter = new ImageTitleEventAdapter(this, eventsList, userInfo);
        eventsRec.setAdapter(eventsAdapter);
        ImageTitleBookAdapter booksAdapter = new ImageTitleBookAdapter(this, booksList, userInfo);
        booksRec.setAdapter(booksAdapter);
    }

    // setPreference Button setup based on the fact that library is already a favourite or not
    public void setFavouriteButton(Boolean isFavourite) {
        if(!isFavourite) {
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
        setFavourite.setVisibility (View.VISIBLE);
    }

    public void showAllNews(View view) {
        Intent newsIntent = new Intent (this, NewsListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ALL_NEWS, libraryInfo.getLibraryContent ().getNews ());
        newsIntent.putExtras(bundle);
        doUnbindService();
        startActivity(newsIntent);
    }

    public void showAllEvents(View view) {
        Intent eventIntent = new Intent (this, EventListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ALL_EVENTS, libraryInfo.getLibraryContent ().getEvents ());
        bundle.putSerializable (USER_INFO, userInfo);
        bundle.putSerializable (LIBRARY_INFO, libraryInfo);
        eventIntent.putExtras(bundle);
        doUnbindService();
        startActivity(eventIntent);
    }

    public void showAllBooks(View view) {
        // TODO
    }

    public void setFavourite() {
        AnswerClasses.UserPreferences userPreferences = new UserPreferences ();
        userPreferences.setUser_id (userInfo.getUser_id ());
        userPreferences.setLibrary_id_update (libraryInfo.getId_lib ());
        if (mBoundService != null) {
            mBoundService.setCurrentContext(getApplicationContext());
            mBoundService.sendMessage(Constants.INSERT_PREFERENCE, userPreferences);
        }
    }

    public void removeFromFavourites() {
        AnswerClasses.UserPreferences userPreferences = new UserPreferences ();
        userPreferences.setUser_id (userInfo.getUser_id ());
        userPreferences.setLibrary_to_delete (libraryInfo.getId_lib ());
        if (mBoundService != null) {
            mBoundService.setCurrentContext(getApplicationContext());
            mBoundService.sendMessage(Constants.EDIT_PROFILE, userPreferences);
        }
    }
}
