package com.easylib.dima.easylib.Activities.Fragments;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
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

import com.easylib.dima.easylib.Activities.LibraryActivity;
import com.easylib.dima.easylib.Activities.Lists.LibraryListActivity;
import com.easylib.dima.easylib.Activities.Login.LoginPreferenceActivity;
import com.easylib.dima.easylib.Activities.SearchActivity;
import com.easylib.dima.easylib.ConnectionLayer.ConnectionService;
import com.easylib.dima.easylib.ConnectionLayer.Constants;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import AnswerClasses.Book;
import AnswerClasses.LibraryDescriptor;
import AnswerClasses.User;

public class MainActivity extends AppCompatActivity {

    private static final String USER_PREFERENCES = "User Preferences";
    private static final String USER_INFO = "User Info";
    private static final String ALL_LIBRARIES_LIST = "All Libraries List";
    private User userInfo;
    private ArrayList<LibraryDescriptor> prefLibraries;
    private ArrayList<LibraryDescriptor> allLibrariesList;
    private Fragment fragment;

    // Library List Activity
    private Intent librariesListIntent;
    // Library Activity
    private static final String LIBRARY_IS_PREFERITE = "Library is Preferite";
    private static final String LIBRARY_INFO = "Library Info";
    private Intent libraryActivityIntent;
    private LibraryDescriptor libraryToShow;
    // Profile Fragment
    private Boolean prefLibCalledForProfile = false;

    //Comunication
    ConnectionService mBoundService;
    private boolean mIsBound;

    // Frame for Fragments and BottomNavBar
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    // SearchActivity items
    private ImageButton searchBt;

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

    private void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
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
                bundle.putSerializable(ALL_LIBRARIES_LIST, allLibrariesList);
                librariesListIntent.putExtras(bundle);
                doUnbindService();
                unregisterReceiver(mMessageReceiver);
                startActivity(librariesListIntent);
            }
            if (key.equals(Constants.GET_USER_PREFERENCES)) {
                prefLibraries = (ArrayList<LibraryDescriptor>) intent.getSerializableExtra(Constants.GET_USER_PREFERENCES);
                if (!prefLibCalledForProfile) {
                    Boolean libraryIsPref;
                    libraryIsPref = false;
                    for (LibraryDescriptor library : prefLibraries) {
                        if (library.getId_lib() == libraryToShow.getId_lib())
                            libraryIsPref = true;
                    }
                    libraryActivityIntent = new Intent(context, LibraryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(LIBRARY_INFO, libraryToShow);
                    bundle.putSerializable(LIBRARY_IS_PREFERITE, libraryIsPref);
                    bundle.putSerializable(USER_INFO, userInfo);
                    libraryActivityIntent.putExtras(bundle);
                    doUnbindService();
                    unregisterReceiver(mMessageReceiver);
                    startActivity(libraryActivityIntent);
                } else {
                    getRatedBooks();
                }
            }
            if (key.equals(Constants.GET_USER_RATED_BOOKS)) {
                ArrayList<Book> ratedBooks = (ArrayList<Book>) intent.getSerializableExtra(Constants.GET_USER_RATED_BOOKS);
                ((ProfileFragment)fragment).setData(userInfo, prefLibraries, ratedBooks);
                setFragment(fragment);
                prefLibCalledForProfile = false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Communication
        doBindService();
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_ALL_LIBRARIES));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_USER_PREFERENCES));
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_USER_RATED_BOOKS));

        userInfo = (User) getIntent().getSerializableExtra(USER_INFO);
        prefLibraries = (ArrayList<LibraryDescriptor>) getIntent().getSerializableExtra(USER_PREFERENCES);

        // Frame for Fragments and BottomNavBar
        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.bottom_navbar);
        // SearchActivity items
        searchBt = (ImageButton) findViewById(R.id.search_icon);

        // Set Initial Fragment to be visualized
        fragment = new HomeFragment();
        ((HomeFragment) fragment).setLibrariesPref(prefLibraries);
        setFragment(fragment);

        // Change fragment based on icon clicked on bottomNavBar
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home_item :
                        fragment = new HomeFragment();
                        break;

                    case R.id.calendar_item :
                        fragment = new CalendarFragment();
                        break;

                    case R.id.scan_item :
                        fragment = new ScanFragment();
                        break;

                    case R.id.queue_item :
                        fragment = new QueueFragment();
                        break;

                    case R.id.profile_item :
                        fragment = new ProfileFragment();
                        prefLibCalledForProfile = true;
                        getPrefLibraries();
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
        this.registerReceiver(mMessageReceiver, new IntentFilter(Constants.GET_USER_RATED_BOOKS));
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    // Method called when the SearchActivity icon is clicked
    public void goToSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    // Method called when the Notification icon is clicked
    public void goToNofitication(View view) {
        // TODO : make the call to Notification Activity
    }

    // Get all Libraries from Home
    public void getAllLibraries() {
        if (mBoundService != null) {
            mBoundService.setCurrentContext(getApplicationContext());
            mBoundService.sendMessage(Constants.GET_ALL_LIBRARIES, null);
        }
    }

    public void showLibrary(LibraryDescriptor library) {
        libraryToShow = library;
        getPrefLibraries();
    }

    public void getPrefLibraries() {
        if (mBoundService != null) {
            mBoundService.setCurrentContext(getApplicationContext());
            mBoundService.sendMessage(Constants.GET_USER_PREFERENCES, userInfo.getUser_id());
        }
    }

    public void getRatedBooks() {
        if (mBoundService != null) {
            mBoundService.setCurrentContext(getApplicationContext());
            mBoundService.sendMessage(Constants.GET_USER_RATED_BOOKS, userInfo.getUser_id());
        }
    }
}
