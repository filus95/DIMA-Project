package com.easylib.dima.easylib_librarian.Activities;

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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib_librarian.Adapters.BookReservationsAdapter;
import com.easylib.dima.easylib_librarian.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib_librarian.ConnectionLayer.Constants;
import com.easylib.dima.easylib_librarian.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import AnswerClasses.Book;
import AnswerClasses.LibraryDescriptor;
import AnswerClasses.Reservation;
import AnswerClasses.User;

public class BookActivity extends AppCompatActivity {

    // Needed
    private static final String USER_INFO = "User Info";
    private static final String BOOK_INFO = "Book Info";
    private static final String LIBRARY_INFO = "Library Info";
    private User userInfo;
    private Book bookInfo;
    private LibraryDescriptor libraryInfo;
    private ArrayList<Reservation> reservationsList;


    private TextView title;
    private TextView authors;
    private TextView avgRate;
    private TextView numBooksAvailable;
    private TextView description;
    private ImageView image;

    // recycle view
    private RecyclerView mRecyclerView;
    private BookReservationsAdapter mAdapter;
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

    //This is the handler that will manager to process the broadcast intent
    //This has to be created inside each activity that needs it ( almost anyone )
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String key = extractKey(intent);

            if ( key.equals(Constants.GET_ALL_RESERVATIONS_FOR_BOOK)) {
                reservationsList = (ArrayList<Reservation>) intent.getSerializableExtra (Constants.GET_ALL_RESERVATIONS_FOR_BOOK);
                setReservationsAdapter ();
            }
            if (key.equals (Constants.RESERVED_BOOK_RETURNED)) {
                Boolean bool = (Boolean) intent.getSerializableExtra (Constants.RESERVED_BOOK_RETURNED);
                if (bool) {
                    Toast.makeText(context,"Book Returned", Toast.LENGTH_LONG).show();
                    getAllReservations ();
                } else {
                    Toast.makeText(context,"ERROR..", Toast.LENGTH_LONG).show();
                }
            }
            if (key.equals (Constants.RESERVED_BOOK_TAKEN)) {
                Boolean bool = (Boolean) intent.getSerializableExtra (Constants.RESERVED_BOOK_TAKEN);
                if (bool) {
                    Toast.makeText(context,"Book Taken", Toast.LENGTH_LONG).show();
                    getAllReservations ();
                } else {
                    Toast.makeText(context,"ERROR..", Toast.LENGTH_LONG).show();
                }
            }
            if (key.equals (Constants.REMOVE_RESERVATION)) {
                Boolean bool = (Boolean) intent.getSerializableExtra (Constants.REMOVE_RESERVATION);
                if (bool) {
                    Toast.makeText(context,"Removed Reservation", Toast.LENGTH_LONG).show();
                    getAllReservations ();
                }
                else {
                    Toast.makeText(context,"ERROR..", Toast.LENGTH_LONG).show();
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
        setContentView(R.layout.book_activity);

        userInfo = (User) getIntent().getSerializableExtra(USER_INFO);
        bookInfo = (Book) getIntent().getSerializableExtra(BOOK_INFO);
        libraryInfo = (LibraryDescriptor) getIntent().getSerializableExtra(LIBRARY_INFO);

        // get components
        title = (TextView) findViewById(R.id.book_activity_title);
        authors = (TextView) findViewById(R.id.book_activity_authors);
        numBooksAvailable = (TextView) findViewById (R.id.book_activity_books_num);
        description = (TextView) findViewById(R.id.book_activity_description);
        avgRate = (TextView) findViewById(R.id.book_activity_average_rate);
        image = (ImageView) findViewById(R.id.book_activity_image);

        // set the components
        title.setText(bookInfo.getTitle ());
        for(int i=0; i<bookInfo.getAuthors ().size (); i++){
            if (i == 0) {
                authors.setText (authors.getText ().toString () + bookInfo.getAuthors ().get (i));
            } else {
                authors.setText (authors.getText ().toString () + ", " + bookInfo.getAuthors ().get (i));
            }
        }
        numBooksAvailable.setText (String.valueOf (bookInfo.getQuantity_reserved ()));
        description.setText(bookInfo.getDescription());
        avgRate.setText(String.valueOf (bookInfo.getAverageRating ().intValue ()));
        Glide.with(this)
                .load(bookInfo.getImageLink())
                .into(image);

        // Recycle View Settings
        mRecyclerView = (RecyclerView) findViewById(R.id.book_activity_reservations_recycler);
        mRecyclerView.setHasFixedSize(true);
        // used linear layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Communication
        // Communication
        doBindService();
        this.registerReceiver(mMessageReceiver, new IntentFilter (Constants.GET_ALL_RESERVATIONS_FOR_BOOK));

        // Send Login Info to Server
        new Handler ().postDelayed(new Runnable() {
            @Override
            public void run() {
                getAllReservations ();
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        doUnbindService();
    }

    public void setReservationsAdapter() {
        // specify an adapter
        mAdapter = new BookReservationsAdapter(this, reservationsList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void getAllReservations() {
        Reservation reservation = new Reservation ();
        reservation.setIdLib (libraryInfo.getId_lib ());
        reservation.setBook_idetifier (bookInfo.getIdentifier ());
        if (mBoundService != null) {
            mBoundService.setCurrentContext(getApplicationContext());
            mBoundService.sendMessage(Constants.GET_ALL_RESERVATIONS_FOR_BOOK, reservation);
        }
    }

    public void setBookReturned(int userId) {
        Reservation reservation = new Reservation ();
        reservation.setIdLib (libraryInfo.getId_lib ());
        reservation.setBook_idetifier (bookInfo.getIdentifier ());
        reservation.setUser_id (userId);
        if (mBoundService != null) {
            mBoundService.setCurrentContext(getApplicationContext());
            mBoundService.sendMessage(Constants.RESERVED_BOOK_RETURNED, reservation);
        }
    }

    public void removeReservation( int userId) {
        Reservation reservation = new Reservation ();
        reservation.setIdLib (libraryInfo.getId_lib ());
        reservation.setUser_id (userId);
        reservation.setBook_idetifier (bookInfo.getIdentifier ());
        if (mBoundService != null) {
            mBoundService.setCurrentContext(getApplicationContext());
            mBoundService.sendMessage(Constants.REMOVE_RESERVATION, reservation);
        }
    }

    public void confirmReservation(int userId) {
        Reservation reservation = new Reservation ();
        reservation.setIdLib (libraryInfo.getId_lib ());
        reservation.setBook_idetifier (bookInfo.getIdentifier ());
        reservation.setUser_id (userId);
        if (mBoundService != null) {
            mBoundService.setCurrentContext(getApplicationContext());
            mBoundService.sendMessage(Constants.RESERVED_BOOK_TAKEN, reservation);
        }
    }
}
