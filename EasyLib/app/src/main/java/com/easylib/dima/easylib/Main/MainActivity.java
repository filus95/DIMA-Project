package com.easylib.dima.easylib.Main;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easylib.dima.easylib.Main.QueueFragment.QueueFragment;
import com.easylib.dima.easylib.R;
import com.easylib.dima.easylib.Search.Search;

public class MainActivity extends AppCompatActivity
            implements HomeFragment.OnFragmentInteractionListener,
            CalendarFragment.OnFragmentInteractionListener,
            ProfileFragment.OnFragmentInteractionListener,
            QueueFragment.OnFragmentInteractionListener,
            ScanFragment.OnFragmentInteractionListener {


    // Frame for Fragments and BottomNavBar
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    // Fragments available from main
    private HomeFragment homeFragment;
    private CalendarFragment calendarFragment;
    private ScanFragment scanFragment;
    private QueueFragment queueFragment;
    private ProfileFragment profileFragment;
    // Search items
    private EditText searchText;
    private ImageButton searchBt;
    private LinearLayout searchLay;
    private ImageButton clearbt;
    private LinearLayout advancesSearchLay;
    private EditText advSearchAuthor;
    private EditText advSearchGenre;
    private EditText advSearchLib;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Frame for Fragments and BottomNavBar
        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.bottom_navbar);
        // Fragments available from main
        homeFragment = new HomeFragment();
        calendarFragment = new CalendarFragment();
        scanFragment = new ScanFragment();
        queueFragment = new QueueFragment();
        profileFragment = new ProfileFragment();
        // Search items
        searchText = (EditText) findViewById(R.id.search_text);
        searchBt = (ImageButton) findViewById(R.id.search_icon);
        searchLay = (LinearLayout) findViewById(R.id.search_lin_layout);
        clearbt = (ImageButton) findViewById(R.id.clear_bt);
        // Advancd Search items
        advancesSearchLay = (LinearLayout) findViewById(R.id.advanced_search_linear_layout);
        advSearchAuthor = (EditText) findViewById(R.id.search_author);
        advSearchGenre = (EditText) findViewById(R.id.search_genre);
        advSearchLib = (EditText) findViewById(R.id.search_biblo);

        // Change the Enter key on keyborad in a Search button
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch("ciao");
                    return true;
                }
                return false;
            }
        });
        advSearchAuthor.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch("ciao");
                    return true;
                }
                return false;
            }
        });
        advSearchGenre.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch("ciao");
                    return true;
                }
                return false;
            }
        });
        advSearchLib.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch("ciao");
                    return true;
                }
                return false;
            }
        });

        // Set Initial Fragment to be visualized
        setFragment(homeFragment);

        // Change fragment based on icon clicked on bottomNavBar
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

        searchBt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                activateAdvancedSearch();
                return true;
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

    // Method called when the Search icon is clicked
    public void activateSearch(View view) {
        // Set Text Visible or Not when Search icon is pressed
        if (searchLay.getVisibility() == View.VISIBLE) {
            searchLay.setVisibility(View.INVISIBLE);
            advancesSearchLay.setVisibility(View.INVISIBLE);
            searchBt.setColorFilter(Color.WHITE);
        }
        else {
            searchLay.setVisibility(View.VISIBLE);
            searchBt.setColorFilter(getResources().getColor(R.color.colorYellow));
        }
    }

    public void activateAdvancedSearch() {
        if (advancesSearchLay.getVisibility() == View.INVISIBLE
                && searchLay.getVisibility() == View.VISIBLE) {
            advancesSearchLay.setVisibility(View.VISIBLE);
        }
        else if (advancesSearchLay.getVisibility() == View.INVISIBLE
                && searchLay.getVisibility() == View.INVISIBLE) {
            advancesSearchLay.setVisibility(View.VISIBLE);
            searchLay.setVisibility(View.VISIBLE);
            searchBt.setColorFilter(getResources().getColor(R.color.colorYellow));
        }
        else {
            advancesSearchLay.setVisibility(View.INVISIBLE);
        }
    }

    // When X button is pressed the search text is cleared
    public void clearSearchText(View view) {
        searchText.getText().clear();
    }

    public void performSearch(String searchedText) {
        Intent intent = new Intent(this, Search.class);
        intent.putExtra("searched", searchedText);
        startActivity(intent);
    }
}
