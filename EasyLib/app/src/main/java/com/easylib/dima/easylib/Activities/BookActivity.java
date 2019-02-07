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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.Activities.Login.LoginPreferenceActivity;
import com.easylib.dima.easylib.Adapters.BookAvailableLibAdapter;
import com.easylib.dima.easylib.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib.ConnectionLayer.Constants;
import com.easylib.dima.easylib.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import AnswerClasses.Book;
import AnswerClasses.LibraryDescriptor;

public class BookActivity extends AppCompatActivity {

    private static final String BOOK_INFO = "Book Info";
    private static final String USER_INFO = "User Info";
    private Book bookInfo;
    private AnswerClasses.User userInfo;

    private TextView title;
    private TextView authors;
    private TextView avgRate;
    private TextView description;
    private EditText userRate;
    private ImageView image;

    // Needed variable
    private ArrayList<AnswerClasses.LibraryDescriptor> availableLibraries;
    private ArrayList<Integer> availableLibrariesIds;
    private boolean reservedByMe;
    private String libIdReservedByMe;
    private boolean meOnWaitingList;

    // recycle view
    private RecyclerView mRecyclerView;
    private BookAvailableLibAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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
        bindService(new Intent (BookActivity.this, ConnectionService.class), mConnection,
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

            if (key.equals(Constants.QUERY_ON_BOOKS_ALL_LIBRARIES)) {
                ArrayList<Book> books = (ArrayList<Book>) intent.getSerializableExtra(Constants.QUERY_ON_BOOKS_ALL_LIBRARIES);
                for (Book b : books) {
                    availableLibrariesIds.add (b.getIdLibrary ());
                }
                if (mBoundService != null) {
                    mBoundService.setCurrentContext(getApplicationContext());
                    mBoundService.sendMessage(Constants.GET_ALL_LIBRARIES, null);
                }
            }
            if (key.equals (Constants.GET_ALL_LIBRARIES)) {
                ArrayList<AnswerClasses.LibraryDescriptor> libraries = (ArrayList<LibraryDescriptor>) intent.getSerializableExtra(Constants.GET_ALL_LIBRARIES);
                for (Integer i : availableLibrariesIds) {
                    for (LibraryDescriptor l : libraries) {
                        if (l.getId_lib () == i) {
                            availableLibraries.add (l);
                            break;
                        }
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        // Communication
        doBindService();
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.QUERY_ON_BOOKS_ALL_LIBRARIES));
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.GET_ALL_LIBRARIES));

        userInfo = (AnswerClasses.User) getIntent().getSerializableExtra(USER_INFO);
        bookInfo = (Book) getIntent().getSerializableExtra(BOOK_INFO);

        // get components
        title = (TextView) findViewById(R.id.book_activity_title);
        authors = (TextView) findViewById(R.id.book_activity_authors);
        description = (TextView) findViewById(R.id.book_activity_description);
        avgRate = (TextView) findViewById(R.id.book_activity_average_rate);
        userRate = (EditText) findViewById(R.id.book_activity_your_rate);
        image = (ImageView) findViewById(R.id.book_activity_image);

        // set the components
        title.setText(bookInfo.getTitle());
        for(String author : bookInfo.getAuthors()){
            authors.setText(authors + ", " + author);
        }
        description.setText(bookInfo.getDescription());
        avgRate.setText(bookInfo.getAverageRating().intValue());
        Glide.with(this)
                .load(bookInfo.getImageLink())
                .into(image);

        // Recycle View Settings
        mRecyclerView = (RecyclerView) findViewById(R.id.book_activity_lib_recycle);
        mRecyclerView.setHasFixedSize(true);
        // used linear layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        new Handler ().postDelayed(new Runnable() {
            @Override
            public void run() {
                AnswerClasses.Query query = new AnswerClasses.Query ();
                // TODO : check if can do this
                query.setIdentifier (bookInfo.getIdentifier ());
                if (mBoundService != null) {
                    mBoundService.setCurrentContext(getApplicationContext());
                    mBoundService.sendMessage(Constants.QUERY_ON_BOOKS_ALL_LIBRARIES, query);
                }
            }
        }, 1000);

        // specify an adapter
        // TODO : change adapter construction
        mAdapter = new BookAvailableLibAdapter(this, true, "string", new ArrayList<Book>());
        mRecyclerView.setAdapter(mAdapter);
    }
}
