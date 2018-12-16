package com.easylib.dima.easylib.Main;

import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.easylib.dima.easylib.R;

public class MainActivity extends AppCompatActivity
            implements HomeFragment.OnFragmentInteractionListener,
            CalendarFragment.OnFragmentInteractionListener,
            ProfileFragment.OnFragmentInteractionListener,
            QueueFragment.OnFragmentInteractionListener,
            ScanFragment.OnFragmentInteractionListener {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private HomeFragment homeFragment;
    private CalendarFragment calendarFragment;
    private ScanFragment scanFragment;
    private QueueFragment queueFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.bottom_navbar);

        homeFragment = new HomeFragment();
        calendarFragment = new CalendarFragment();
        scanFragment = new ScanFragment();
        queueFragment = new QueueFragment();
        profileFragment = new ProfileFragment();

        setFragment(homeFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.home_item :
                        setFragment(homeFragment);
                        return true;

                    case R.id.calendar_item :
                        setFragment(calendarFragment);
                        return true;

                    case R.id.scan_item :
                        setFragment(scanFragment);
                        return true;

                    case R.id.queue_item :
                        setFragment(queueFragment);
                        return true;

                    case R.id.profile_item :
                        setFragment(profileFragment);
                        return true;

                    default :
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    public void onFragmentInteraction(Uri uri) {
        //empty
    }

    public void activateSearch(View view) {
        EditText searchText = (EditText) findViewById(R.id.search_text);
        ImageButton searchBt = (ImageButton) findViewById(R.id.search_icon);
        if (searchText.getVisibility() == View.VISIBLE) {
            searchText.setVisibility(View.INVISIBLE);
            searchBt.setColorFilter(Color.WHITE);
        }
        else {
            searchText.setVisibility(View.VISIBLE);
            searchBt.setColorFilter(getResources().getColor(R.color.colorYellow));
        }
    }
}
