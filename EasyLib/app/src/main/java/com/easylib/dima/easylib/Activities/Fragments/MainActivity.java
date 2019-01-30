package com.easylib.dima.easylib.Activities.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.easylib.dima.easylib.Activities.SearchActivity;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

import AnswerClasses.LibraryDescriptor;
import AnswerClasses.User;

public class MainActivity extends AppCompatActivity {

    private static final String USER_PREFERENCES = "User Preferences";
    private static final String USER_INFO = "User Info";
    User userInfo;
    ArrayList<LibraryDescriptor> prefLibraries;

    // Frame for Fragments and BottomNavBar
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    // SearchActivity items
    private ImageButton searchBt;

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

        // Set Initial Fragment to be visualized
        setFragment(new HomeFragment());

        // Change fragment based on icon clicked on bottomNavBar
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
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
                        break;
                }
                setFragment(fragment);
                return true;
            }
        });
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
}
