package com.easylib.dima.easylib_librarian.Main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.easylib.dima.easylib_librarian.R;

public class MainActivity extends AppCompatActivity {

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
}
