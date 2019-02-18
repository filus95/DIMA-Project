package com.easylib.dima.easylib.Activities.Fragments;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.easylib.dima.easylib.Activities.BookActivity;
import com.easylib.dima.easylib.Activities.JoinedEventsActivity;
import com.easylib.dima.easylib.Activities.LibraryActivity;
import com.easylib.dima.easylib.Activities.Lists.EventListActivity;
import com.easylib.dima.easylib.Activities.Lists.LibraryListActivity;
import com.easylib.dima.easylib.Activities.Login.LoginActivity;
import com.easylib.dima.easylib.Activities.NoInternetActivity;
import com.easylib.dima.easylib.Activities.NotificationActivity;
import com.easylib.dima.easylib.Activities.ReadBooksActivity;
import com.easylib.dima.easylib.Activities.SearchActivity;
import com.easylib.dima.easylib.Adapters.HomeAdapter;
import com.easylib.dima.easylib.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib.ConnectionLayer.Constants;
import com.easylib.dima.easylib.R;
import com.easylib.dima.easylib.Utils.NotificationObj;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import AnswerClasses.Book;
import AnswerClasses.LibraryDescriptor;
import AnswerClasses.Query;
import AnswerClasses.Reservation;
import AnswerClasses.User;
import AnswerClasses.Event;
import AnswerClasses.WaitingPerson;
import AnswerClasses.WaitingPersonInsert;

public class MainActivity extends AppCompatActivity {

    private static final String USER_PREFERENCES = "User Preferences";
    private static final String USER_INFO = "User Info";
    private static final String LIBRARIES_LIST = "Libraries List";
    private User userInfo;
    private ArrayList<LibraryDescriptor> prefLibraries;
    private ArrayList<LibraryDescriptor> allLibrariesList;
    private Fragment fragment;

    // for Notifications
    private static final String NOTIFICATIONS = "Notifications";

    // Library List Activity
    private Intent librariesListIntent;
    // Library Activity
    private static final String LIBRARY_INFO = "Library Info";
    // Book Activity
    private static final String BOOK_INFO = "Book Info";
    // Profile Fragment
    private static final String READ_BOOKS_ARRAY = "Read Books Array";
    private static final String ALL_EVENTS = "All Events";
    private Boolean prefLibCalledForProfile = false;
    private Boolean readBooksCalledForProfile = false;
    Boolean isJoinedEventsForProfile = false;
    private ArrayList<Book> readBooks;
    // Home Fragment
    private Boolean prefLibCalledForHome = false;

    //Comunication
    ConnectionService mBoundService;
    private boolean mIsBound;

    // Frame for Fragments and BottomNavBar
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    // SearchActivity items
    private ImageButton searchBt;
    private ImageButton notifBt;

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
        bindService(new Intent(MainActivity.this, ConnectionService.class), mConnection,
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

            if (key.equals(Constants.GET_ALL_LIBRARIES)) {
                allLibrariesList = (ArrayList<LibraryDescriptor>) intent.getSerializableExtra(Constants.GET_ALL_LIBRARIES);
                librariesListIntent = new Intent(context, LibraryListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(USER_INFO, userInfo);
                bundle.putSerializable(LIBRARIES_LIST, allLibrariesList);
                librariesListIntent.putExtras(bundle);
                startActivity(librariesListIntent);
            }
            else if (key.equals(Constants.GET_USER_PREFERENCES)) {
                prefLibraries = (ArrayList<LibraryDescriptor>) intent.getSerializableExtra(Constants.GET_USER_PREFERENCES);
                if (prefLibCalledForProfile){
                    getReadBooks (true);
                    prefLibCalledForProfile = false;
                } else if (prefLibCalledForHome) {
                    if ( fragment instanceof HomeFragment ) {
                        prefLibCalledForHome = false;
                        ((HomeFragment) fragment).setData(prefLibraries, userInfo);
                        setFragment(fragment);
                    }
                } else {
                    Intent prefLibsIntent = new Intent(context, LibraryListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(USER_INFO, userInfo);
                    bundle.putSerializable(LIBRARIES_LIST, prefLibraries);
                    prefLibsIntent.putExtras(bundle);
                    startActivity(prefLibsIntent);
                }
            }
            else if (key.equals(Constants.GET_READ_BOOKS)) {
                readBooks = (ArrayList<Book>) intent.getSerializableExtra(Constants.GET_READ_BOOKS);
                if (readBooksCalledForProfile) {
                    readBooksCalledForProfile = false;
                    // Send Login Info to Server to recieve back userInfo
                    User user = new User ();
                    user.setUser_id (userInfo.getUser_id ());
                    if (mBoundService != null) {
                        mBoundService.setCurrentContext(getApplicationContext ());
                        mBoundService.sendMessage(Constants.USER_LOGIN, user);
                    }
                } else {
                    Intent readBooksIntent = new Intent(context, ReadBooksActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(READ_BOOKS_ARRAY, readBooks);
                    bundle.putSerializable(USER_INFO, userInfo);
                    readBooksIntent.putExtras(bundle);
                    startActivity(readBooksIntent);
                }
            }
            else if (key.equals(Constants.USER_LOGIN)) {
                User user = (User) intent.getSerializableExtra(Constants.USER_LOGIN);
                userInfo = user;
                getJoinedEvents (true);

            }
            else if (key.equals(Constants.GET_EVENTS_PER_USER)) {
                ArrayList<Event> events = (ArrayList<Event>) intent.getSerializableExtra (Constants.GET_EVENTS_PER_USER);
                if ( fragment instanceof ProfileFragment ) {
                    if (isJoinedEventsForProfile) {
                        ((ProfileFragment) fragment).setData(userInfo, prefLibraries, readBooks, events);
                        isJoinedEventsForProfile = false;
                        setFragment(fragment);
                    } else {
                        Intent eventListIntent = new Intent(context, JoinedEventsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(ALL_EVENTS, events);
                        bundle.putSerializable(USER_INFO, userInfo);
                        eventListIntent.putExtras(bundle);
                        startActivity(eventListIntent);
                    }
                }
            }
            else if (key.equals (Constants.GET_WAITING_LIST_USER)) {
                AnswerClasses.WaitingPerson waitingPerson = (WaitingPerson) intent.getSerializableExtra (Constants.GET_WAITING_LIST_USER);
                if ( fragment instanceof QueueFragment ) {
                    ArrayList<Book> waitingListBooks = waitingPerson.getBooksInWaitingList();
                    ((QueueFragment) fragment).setData(userInfo, waitingListBooks);
                    setFragment(fragment);
                }
            }
            else if (key.equals (Constants.REMOVE_WAITING_PERSON)) {
                Boolean bool = (Boolean) intent.getSerializableExtra (Constants.REMOVE_WAITING_PERSON);
                if (bool)
                    Toast.makeText (context, "Removed", Toast.LENGTH_LONG).show ();
                else
                    Toast.makeText (context, "ERROR..", Toast.LENGTH_LONG).show ();
            }
            else if (key.equals (Constants.GET_USER_RESERVATION)) {
                ArrayList<Reservation> reservations = (ArrayList<Reservation>) intent.getSerializableExtra (Constants.GET_USER_RESERVATION);
                if ( fragment instanceof CalendarFragment) {
                    ((CalendarFragment) fragment).setData(reservations, userInfo);
                    setFragment(fragment);
                }
            }
            else if (key.equals (Constants.QUERY_ON_BOOKS_ALL_LIBRARIES)) {
                ArrayList<Book> books = (ArrayList<Book>) intent.getSerializableExtra (Constants.QUERY_ON_BOOKS_ALL_LIBRARIES);
                Book b = books.get (0);
                Intent bookIntent = new Intent (context, BookActivity.class);
                Bundle bundle = new Bundle ();
                bundle.putSerializable(BOOK_INFO, b);
                bundle.putSerializable (USER_INFO, userInfo);
                bookIntent.putExtras(bundle);
                context.startActivity(bookIntent);
            }
            else if (key.equals(Constants.NETWORK_STATE_DOWN)){
                Intent internetIntent = new Intent (context, NoInternetActivity.class);
                startActivity (internetIntent);
            }
            else if (key.equals(Constants.NOTIFICATION)){
                checkNotifications ();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        userInfo = (User) getIntent().getSerializableExtra(USER_INFO);
        prefLibraries = (ArrayList<LibraryDescriptor>) getIntent().getSerializableExtra(USER_PREFERENCES);

        // Frame for Fragments and BottomNavBar
        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.bottom_navbar);
        // SearchActivity items
        searchBt = (ImageButton) findViewById(R.id.search_icon);
        notifBt = (ImageButton) findViewById (R.id.notification_icon);

        // Set Initial Fragment to be visualized
        fragment = new HomeFragment();
        ((HomeFragment) fragment).setData (prefLibraries, userInfo);
        setFragment(fragment);

        // Change fragment based on icon clicked on bottomNavBar
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home_item :
                        fragment = new HomeFragment();
                        getPrefLibraries (false, true);
                        break;

                    case R.id.calendar_item :
                        fragment = new CalendarFragment();
                        getReservations ();
                        break;

                    case R.id.scan_item :
                        scan ();
                        break;

                    case R.id.queue_item :
                        fragment = new QueueFragment();
                        getWaitingListBooks ();
                        break;

                    case R.id.profile_item :
                        fragment = new ProfileFragment();
                        getPrefLibraries(true, false);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Communication
        doBindService();
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_ALL_LIBRARIES));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_USER_PREFERENCES));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_READ_BOOKS));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.USER_LOGIN));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_EVENTS_PER_USER));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_WAITING_LIST_USER));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.REMOVE_WAITING_PERSON));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_USER_RESERVATION));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.QUERY_ON_BOOKS_ALL_LIBRARIES));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.NETWORK_STATE_DOWN));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.NOTIFICATION));

        // Reload Notifications
        checkNotifications ();

        // Reload fragment content on resume
        if (fragment instanceof ProfileFragment) {
            mMainNav.setSelectedItemId (R.id.profile_item);
        }
        if (fragment instanceof HomeFragment) {
            mMainNav.setSelectedItemId (R.id.home_item);
        }
        if (fragment instanceof CalendarFragment) {
            mMainNav.setSelectedItemId (R.id.calendar_item);
        }
        if (fragment instanceof QueueFragment) {
            mMainNav.setSelectedItemId (R.id.queue_item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause ();
        doUnbindService ();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    // Method called when the SearchActivity icon is clicked
    public void goToSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(USER_INFO, userInfo);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    // Method called when the Notification icon is clicked
    public void goToNofitication(View view) {
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    // Get all Libraries from Home
    public void getAllLibraries() {
        if (mBoundService != null) {
            mBoundService.setCurrentContext(getApplicationContext());
            mBoundService.sendMessage(Constants.GET_ALL_LIBRARIES, null);
        }
    }

    // Called to show LibraryActivity
    public void showLibrary(LibraryDescriptor library) {
        Intent libraryActivityIntent = new Intent(this, LibraryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(LIBRARY_INFO, library);
        bundle.putSerializable(USER_INFO, userInfo);
        libraryActivityIntent.putExtras(bundle);
        startActivity(libraryActivityIntent);
    }

    public void getPrefLibraries(boolean prefLibCalledForProfile, Boolean prefLibCalledForHome) {
        this.prefLibCalledForProfile = prefLibCalledForProfile;
        this.prefLibCalledForHome = prefLibCalledForHome;
        if (mBoundService != null) {
            mBoundService.setCurrentContext(getApplicationContext());
            mBoundService.sendMessage(Constants.GET_USER_PREFERENCES, userInfo.getUser_id());
        }
    }

    public void getReadBooks(Boolean readBooksCalledForProfile) {
        this.readBooksCalledForProfile = readBooksCalledForProfile;
        if (mBoundService != null) {
            mBoundService.setCurrentContext(getApplicationContext());
            mBoundService.sendMessage(Constants.GET_READ_BOOKS, userInfo);
        }
    }

    public void getJoinedEvents(Boolean isJoinedEventsForProfile) {
        this.isJoinedEventsForProfile = isJoinedEventsForProfile;
        if (mBoundService != null) {
            mBoundService.setCurrentContext(getApplicationContext());
            mBoundService.sendMessage(Constants.GET_EVENTS_PER_USER, userInfo);
        }
    }

    public void getWaitingListBooks() {
        if (mBoundService != null) {
            mBoundService.setCurrentContext(getApplicationContext());
            mBoundService.sendMessage(Constants.GET_WAITING_LIST_USER, userInfo);
        }
    }

    public void removeFromWaitingList(int lib_id, String book_id) {
        AnswerClasses.WaitingPersonInsert wp = new WaitingPersonInsert ();
        wp.setUser_id (userInfo.getUser_id ());
        wp.setId_lib (lib_id);
        wp.setBook_identifier (book_id);
        if (mBoundService != null) {
            mBoundService.setCurrentContext(getApplicationContext());
            mBoundService.sendMessage(Constants.REMOVE_WAITING_PERSON, wp);
        }
    }

    public void getReservations() {
        AnswerClasses.Reservation reservation = new Reservation ();
        reservation.setUser_id (userInfo.getUser_id ());
        reservation.setIdLib (-1);
        if (mBoundService != null) {
            mBoundService.setCurrentContext(getApplicationContext());
            mBoundService.sendMessage(Constants.GET_USER_RESERVATION, reservation);
        }
    }

    // Called from EditProfileActivity to set new UserInfo if changed
    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    // Called after LOGOUT to go back to Login
    public void goToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void scan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult.getContents () != null) {
            Query q = new Query ();
            q.setIdentifier (scanResult.getContents ());
            if (mBoundService != null) {
                mBoundService.setCurrentContext(getApplicationContext());
                mBoundService.sendMessage(Constants.QUERY_ON_BOOKS_ALL_LIBRARIES, q);
            }
        }
    }

    public void checkNotifications() {
        // call shared Preferences
        SharedPreferences sp = getSharedPreferences(NOTIFICATIONS, MODE_PRIVATE);
        //Retrieve the values
        Gson gson = new Gson();
        String jsonText = sp.getString("New Notifications", null);
        Type type = new TypeToken<List<NotificationObj>> (){}.getType();
        ArrayList<NotificationObj> notificationObjsList = gson.fromJson(jsonText, type);
        if (notificationObjsList != null) {
            notifBt.setColorFilter (getResources ().getColor (R.color.colorYellow));
        } else {
            notifBt.setColorFilter (getResources ().getColor (R.color.colorWhite));
        }
    }
}
