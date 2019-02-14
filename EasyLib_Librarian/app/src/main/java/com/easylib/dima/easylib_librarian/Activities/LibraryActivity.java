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
import android.os.Handler;
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

import com.bumptech.glide.Glide;
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
import AnswerClasses.Query;
import AnswerClasses.User;

public class LibraryActivity extends AppCompatActivity {

    // Needed
    private static final String USER_INFO = "User Info";
    private static final String LOGIN = "Login";
    private static final String LIBRARY_INFO = "Library Info";
    private static final String BOOK_INFO = "Book Info";
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
        bindService(new Intent(LibraryActivity.this, ConnectionService.class), mConnection,
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
                ArrayList<Book> books = (ArrayList<Book>) intent.getSerializableExtra (Constants.QUERY_ON_BOOKS);
                if (books.size () != 0) {
                    Book b = books.get (0);
                    Intent bookIntent = new Intent (context, BookActivity.class);
                    Bundle bundle = new Bundle ();
                    bundle.putSerializable (BOOK_INFO, b);
                    bundle.putSerializable (LIBRARY_INFO, libraryInfo);
                    bundle.putSerializable (USER_INFO, userInfo);
                    bookIntent.putExtras (bundle);
                    startActivity (bookIntent);
                } else {
                    Toast.makeText(context,"Book not found", Toast.LENGTH_LONG).show();
                }
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

        // set the components
        name.setText(libraryInfo.getLib_name ());
        location.setText (libraryInfo.getAddress ());
        Glide.with(this)
                .load(libraryInfo.getImage_link ())
                .into(image);
        description.setText(libraryInfo.getDescription());
        email.setText (libraryInfo.getEmail ());
        phone.setText (libraryInfo.getTelephone_number ());
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
            if (i < libraryInfo.getLibraryContent ().getNews ().size ()) {
                newsList.add (libraryInfo.getLibraryContent ().getNews ().get (i));
            }
            if (i < libraryInfo.getLibraryContent ().getEvents ().size ()) {
                eventsList.add (libraryInfo.getLibraryContent ().getEvents ().get (i));
            }
            if (i < libraryInfo.getLibraryContent ().getBooks ().size ()) {
                booksList.add (libraryInfo.getLibraryContent ().getBooks ().get (i));
            }
        }

        // setup recycleViews
        newsRec.setHasFixedSize(true);
        eventsRec.setHasFixedSize(true);
        booksRec.setHasFixedSize(true);
        // used grid layout
        RecyclerView.LayoutManager mLayoutManager0 = new GridLayoutManager(this, 3);
        RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(this, 3);
        RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(this, 3);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // used linear layout
            mLayoutManager0 = new GridLayoutManager(this, 6);
            mLayoutManager1 = new GridLayoutManager(this, 6);
            mLayoutManager2 = new GridLayoutManager(this, 6);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // used grid layout
            mLayoutManager0 = new GridLayoutManager(this, 3);
            mLayoutManager1 = new GridLayoutManager(this, 3);
            mLayoutManager2 = new GridLayoutManager(this, 3);
        }
        newsRec.setLayoutManager(mLayoutManager0);
        newsRec.setItemAnimator(new DefaultItemAnimator());
        eventsRec.setLayoutManager(mLayoutManager1);
        eventsRec.setItemAnimator(new DefaultItemAnimator());
        booksRec.setLayoutManager(mLayoutManager2);
        booksRec.setItemAnimator(new DefaultItemAnimator());
        // specify adapters
        ImageTitleNewsAdapter newsAdapter = new ImageTitleNewsAdapter(this, newsList);
        newsRec.setAdapter(newsAdapter);
        ImageTitleEventAdapter eventsAdapter = new ImageTitleEventAdapter(this, eventsList);
        eventsRec.setAdapter(eventsAdapter);
        ImageTitleBookAdapter booksAdapter = new ImageTitleBookAdapter(this, booksList);
        booksRec.setAdapter(booksAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume ();
        // Communication
        doBindService();
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.QUERY_ON_BOOKS));
    }

    @Override
    protected void onPause() {
        super.onPause ();
        doUnbindService ();
    }

    public void goToScanActivity() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult.getContents () != null) {
            Query q = new Query ();
            q.setIdLib (libraryInfo.getId_lib ());
            q.setIdentifier (scanResult.getContents ());
            // Get Book Info from server
            new Handler ().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mBoundService != null) {
                        mBoundService.setCurrentContext(getApplicationContext());
                        mBoundService.sendMessage(Constants.QUERY_ON_BOOKS, q);
                    }
                }
            }, 1000);
        }
    }

    public void logout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
