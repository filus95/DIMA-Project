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
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.Activities.Login.LoginPreferenceActivity;
import com.easylib.dima.easylib.Adapters.BookAvailableLibAdapter;
import com.easylib.dima.easylib.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib.ConnectionLayer.Constants;
import com.easylib.dima.easylib.R;
import com.google.android.gms.vision.text.Line;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import AnswerClasses.Book;
import AnswerClasses.LibraryDescriptor;
import AnswerClasses.Rating;
import AnswerClasses.Reservation;

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
    private LinearLayout rateLayout;
    private LinearLayout reservedLayout;
    private TextView reservedText;
    private Button reservedButton;
    private LinearLayout librariesLayout;

    // Needed variable
    private ArrayList<AnswerClasses.LibraryDescriptor> availableLibraries;
    private int libraryWhereIsRead;

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

            if (key.equals(Constants.GET_READ_BOOKS)) {
                ArrayList<Book> readBooks = (ArrayList<Book>) intent.getSerializableExtra(Constants.GET_READ_BOOKS);
                Boolean isRead = false;
                for (Book b : readBooks) {
                    if (bookInfo.getIdentifier ().equals (b.getIdentifier ())) {
                        isRead = true;
                        libraryWhereIsRead = b.getIdLibrary ();
                        break;
                    }
                }
                if (isRead) {
                    if (mBoundService != null) {
                        mBoundService.setCurrentContext(getApplicationContext());
                        mBoundService.sendMessage(Constants.GET_USER_RATED_BOOKS, userInfo.getUser_id ());
                    }
                }
            }
            if (key.equals (Constants.GET_USER_RATED_BOOKS)) {
                ArrayList<Book> ratedBooks = (ArrayList<Book>) intent.getSerializableExtra(Constants.GET_USER_RATED_BOOKS);
                Boolean isRated = false;
                for (Book b : ratedBooks) {
                    if (bookInfo.getIdentifier ().equals (b.getIdentifier ())) {
                        isRated = true;
                        break;
                    }
                }
                if (!isRated) {
                    rateLayout.setVisibility (View.VISIBLE);
                }
                if (mBoundService != null) {
                    mBoundService.setCurrentContext(getApplicationContext());
                    mBoundService.sendMessage(Constants.GET_WAITING_LIST_USER, userInfo);
                }
            }
            if (key.equals (Constants.INSERT_RATING)) {
                Boolean bool = (Boolean) intent.getSerializableExtra (Constants.INSERT_RATING);
                if (bool) {
                    Toast.makeText(context,"Rate Inserted", Toast.LENGTH_LONG).show();
                    rateLayout.setVisibility (View.GONE);
                }
                else {
                    Toast.makeText(context,"ERROR..", Toast.LENGTH_LONG).show();
                }
            }
            if (key.equals (Constants.GET_WAITING_LIST_USER)) {
                AnswerClasses.WaitingPerson waitingPerson = (AnswerClasses.WaitingPerson) intent.getSerializableExtra (Constants.GET_WAITING_LIST_USER);
                Boolean isInWaitingList = false;
                for (Book b : waitingPerson.getBooksInWaitingList ()) {
                    if (bookInfo.getIdentifier ().equals (b.getIdentifier ())) {
                        isInWaitingList = true;
                        break;
                    }
                }
                if (isInWaitingList) {
                    reservedLayout.setVisibility (View.VISIBLE);
                    reservedText.setVisibility (View.VISIBLE);
                    reservedText.setText ("On Waiting List");
                } else {
                    AnswerClasses.Reservation reservation = new AnswerClasses.Reservation ();
                    reservation.setUser_id (userInfo.getUser_id ());
                    reservation.setIdLib (-1);
                    if (mBoundService != null) {
                        mBoundService.setCurrentContext(getApplicationContext());
                        mBoundService.sendMessage(Constants.GET_USER_RESERVATION, reservation);
                    }
                }
            }
            if (key.equals (Constants.GET_USER_RESERVATION)) {
                ArrayList<AnswerClasses.Reservation> reservations = (ArrayList<AnswerClasses.Reservation>) intent.getSerializableExtra (Constants.GET_USER_RESERVATION);
                Boolean isInReservationList = false;
                AnswerClasses.Reservation res = new Reservation ();
                for (AnswerClasses.Reservation r : reservations) {
                    if (bookInfo.getIdentifier ().equals (r.getBook_idetifier ())) {
                        isInReservationList = true;
                        res = r;
                        break;
                    }
                }
                if (isInReservationList) {
                    if (res.isTaken ()) {
                        reservedLayout.setVisibility (View.VISIBLE);
                        reservedText.setVisibility (View.VISIBLE);
                        reservedText.setText ("Already Taken");
                    } else {
                        reservedLayout.setVisibility (View.VISIBLE);
                        reservedText.setVisibility (View.VISIBLE);
                        reservedText.setText ("On Reservation List");
                        reservedButton.setVisibility (View.VISIBLE);
                    }
                } else {
                    // TODO : call LIBRARIES_FOR_A_BOOK
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
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.GET_USER_RATED_BOOKS));
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.GET_READ_BOOKS));
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.INSERT_RATING));
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.GET_WAITING_LIST_USER));
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.GET_USER_RESERVATION));
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.LIBRARIES_FOR_BOOK));
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.GET_USER_RATED_BOOKS));

        userInfo = (AnswerClasses.User) getIntent().getSerializableExtra(USER_INFO);
        bookInfo = (Book) getIntent().getSerializableExtra(BOOK_INFO);

        // get components
        title = (TextView) findViewById(R.id.book_activity_title);
        authors = (TextView) findViewById(R.id.book_activity_authors);
        description = (TextView) findViewById(R.id.book_activity_description);
        avgRate = (TextView) findViewById(R.id.book_activity_average_rate);
        userRate = (EditText) findViewById(R.id.book_activity_your_rate);
        image = (ImageView) findViewById(R.id.book_activity_image);
        rateLayout = (LinearLayout) findViewById (R.id.book_activity_rate_layout);
        reservedLayout = (LinearLayout) findViewById (R.id.book_activity_reserved_layout);
        reservedText = (TextView) findViewById (R.id.book_activity_reserved_text);
        reservedButton = (Button) findViewById (R.id.book_activity_reserved_button);
        librariesLayout = (LinearLayout) findViewById (R.id.book_activity_available_libraries_layout);

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

        // Change the Enter key on keyborad in a SearchActivity button
        userRate.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    insertRate ();
                    return true;
                }
                return false;
            }
        });

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
                if (mBoundService != null) {
                    mBoundService.setCurrentContext(getApplicationContext());
                    mBoundService.sendMessage(Constants.GET_READ_BOOKS, userInfo);
                }
            }
        }, 1000);
    }

    public void setLibrariesRecycler() {
        // specify an adapter
        // TODO : change adapter construction
        mAdapter = new BookAvailableLibAdapter(this, true, "string", new ArrayList<Book>());
        mRecyclerView.setAdapter(mAdapter);
    }

    public void insertRate() {
        int rate = Integer.parseInt (userRate.getText ().toString ());
        if (rate >= 0 && rate<= 10) {
            AnswerClasses.Rating rateObj = new Rating ();
            rateObj.setBook_identifier (bookInfo.getIdentifier ());
            rateObj.setRating (rate);
            rateObj.setUser_id (userInfo.getUser_id ());
            rateObj.setIdLib (libraryWhereIsRead);
            if (mBoundService != null) {
                mBoundService.setCurrentContext(getApplicationContext());
                mBoundService.sendMessage(Constants.INSERT_RATING, userInfo);
            }
        } else {
            userRate.setTextColor (Color.RED);
        }
    }

    public void removeReservation(View view) {
        // TODO
    }
}
