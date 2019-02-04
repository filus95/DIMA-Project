package com.easylib.dima.easylib.Activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easylib.dima.easylib.Adapters.BookAdapter;
import com.easylib.dima.easylib.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib.ConnectionLayer.Constants;
import com.easylib.dima.easylib.Model.Book;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import AnswerClasses.LibraryDescriptor;
import AnswerClasses.Query;

public class SearchActivity extends AppCompatActivity {

    private static final String USER_INFO = "User Info";
    private AnswerClasses.User userInfo;
    private ArrayList<AnswerClasses.LibraryDescriptor> allLibraries;
    private ArrayList<Book> books;

    // recycle view
    private RecyclerView mRecyclerView;
    private BookAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // Toolbar
    private EditText searchTitle;
    private ImageButton searchBt;
    private LinearLayout searchLay;
    private LinearLayout advancesSearchLay;
    private EditText advSearchAuthor;
    private EditText advSearchGenre;
    private EditText advSearchLib;

    //Comunication
    ConnectionService mBoundService;
    private boolean mIsBound;

    //For the communication Service
    private ServiceConnection mConnection = new ServiceConnection () {
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
        bindService(new Intent (SearchActivity.this, ConnectionService.class), mConnection,
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
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver () {
        @Override
        public void onReceive(Context context, Intent intent) {
            String key = extractKey(intent);

            if (key.equals(Constants.GET_ALL_LIBRARIES)) {
                allLibraries = (ArrayList<LibraryDescriptor>) intent.getSerializableExtra(Constants.GET_ALL_LIBRARIES);
            }
            if (key.equals(Constants.QUERY_ON_BOOKS_ALL_LIBRARIES)) {
                books = (ArrayList<Book>) intent.getSerializableExtra(Constants.QUERY_ON_BOOKS_ALL_LIBRARIES);
                // specify an adapter
                mAdapter = new BookAdapter(getApplicationContext (), books);
                mRecyclerView.setAdapter(mAdapter);
            }
            if (key.equals(Constants.QUERY_ON_BOOKS)) {
                books = (ArrayList<Book>) intent.getSerializableExtra(Constants.QUERY_ON_BOOKS);
                // specify an adapter
                mAdapter = new BookAdapter(getApplicationContext (), books);
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        // Communication
        doBindService();
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.GET_ALL_LIBRARIES));
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.QUERY_ON_BOOKS));
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.QUERY_ON_BOOKS_ALL_LIBRARIES));

        userInfo = (AnswerClasses.User) getIntent().getSerializableExtra(USER_INFO);

        // SearchActivity items
        searchTitle = (EditText) findViewById(R.id.search_title);
        searchBt = (ImageButton) findViewById(R.id.search_icon);
        searchLay = (LinearLayout) findViewById(R.id.search_lin_layout);
        // Advancd SearchActivity items
        advancesSearchLay = (LinearLayout) findViewById(R.id.advanced_search_linear_layout);
        advSearchAuthor = (EditText) findViewById(R.id.search_author);
        advSearchGenre = (EditText) findViewById(R.id.search_genre);
        advSearchLib = (EditText) findViewById(R.id.search_biblo);

        // Change the Enter key on keyborad in a SearchActivity button
        searchTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });
        advSearchAuthor.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });
        advSearchGenre.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });
        advSearchLib.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        // Recycle View Settings
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_search);
        mRecyclerView.setHasFixedSize(true);
        // used linear layout
        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            // used linear layout
            mLayoutManager = new LinearLayoutManager(this);
        } else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            // used grid layout
            mLayoutManager = new GridLayoutManager(this, 2);
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // get all Libraries
        new Handler ().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mBoundService != null) {
                    mBoundService.setCurrentContext(getApplicationContext());
                    mBoundService.sendMessage(Constants.GET_ALL_LIBRARIES, null);
                }
            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Communication
        doBindService();
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.GET_ALL_LIBRARIES));
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.QUERY_ON_BOOKS));
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.QUERY_ON_BOOKS_ALL_LIBRARIES));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        doUnbindService();
    }

    public void activateAdvancedSearch(View view) {
        if (advancesSearchLay.getVisibility() == View.INVISIBLE) {
            advancesSearchLay.setVisibility(View.VISIBLE);
            searchBt.setColorFilter(getResources().getColor(R.color.colorPrimaryDark));
        } else {
            advancesSearchLay.setVisibility(View.INVISIBLE);
            searchBt.setColorFilter(R.color.colorGray);
        }
    }

    // When X button is pressed the title text is cleared
    public void clearTitleText(View view) {searchTitle.getText().clear();}
    public void clearAuthorText(View view) {advSearchAuthor.getText().clear();}
    public void clearGenreText(View view) {advSearchGenre.getText().clear();}
    public void clearLibraryText(View view) {advSearchLib.getText().clear();}

    public void performSearch() {
        advSearchLib.setTextColor (Color.BLACK);

        if (advSearchLib.getText ().toString ().trim ().length () == 0) {
            AnswerClasses.Query query = new Query ();
            if (searchTitle.getText ().toString ().trim ().length () != 0)
                query.setTitle (searchTitle.getText ().toString ());
            if (advSearchAuthor.getText ().toString ().trim ().length () != 0)
                query.setAuthor (advSearchAuthor.getText ().toString ());
            if (advSearchGenre.getText ().toString ().trim ().length () != 0)
                query.setCategory (advSearchGenre.getText ().toString ());
            if (mBoundService != null) {
                mBoundService.setCurrentContext(getApplicationContext());
                mBoundService.sendMessage(Constants.QUERY_ON_BOOKS_ALL_LIBRARIES, query);
            }
        } else {
            for (LibraryDescriptor biblo : allLibraries) {
                if (advSearchLib.getText ().toString().equalsIgnoreCase (biblo.getLib_name ())) {
                    AnswerClasses.Query query = new Query ();
                    if (searchTitle.getText ().toString ().trim ().length () != 0)
                        query.setTitle (searchTitle.getText ().toString ());
                    if (advSearchAuthor.getText ().toString ().trim ().length () != 0)
                        query.setAuthor (advSearchAuthor.getText ().toString ());
                    if (advSearchGenre.getText ().toString ().trim ().length () != 0)
                        query.setCategory (advSearchGenre.getText ().toString ());
                    query.setIdLib (biblo.getId_lib ());
                    if (mBoundService != null) {
                        mBoundService.setCurrentContext (getApplicationContext ());
                        mBoundService.sendMessage (Constants.QUERY_ON_BOOKS, query);
                    }
                    return;
                }
            }
            advSearchLib.setTextColor (Color.RED);
        }
    }
}
