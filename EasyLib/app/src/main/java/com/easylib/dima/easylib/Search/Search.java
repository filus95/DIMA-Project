package com.easylib.dima.easylib.Search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easylib.dima.easylib.Main.QueueFragment.QueueAdapter;
import com.easylib.dima.easylib.Model.Book;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    private ArrayList<Book> books = new ArrayList<Book>();

    // recycle view
    private RecyclerView mRecyclerView;
    private SearchAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // Toolbar
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
        setContentView(R.layout.search);

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
                    // TODO: performSearch();
                    return true;
                }
                return false;
            }
        });
        advSearchAuthor.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // TODO: performSearch();
                    return true;
                }
                return false;
            }
        });
        advSearchGenre.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // TODO: performSearch();
                    return true;
                }
                return false;
            }
        });
        advSearchLib.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // TODO: performSearch();
                    return true;
                }
                return false;
            }
        });

        searchText.setText(getIntent().getStringExtra("searched"));

        int i;
        for(i=0; i<15; i++) {
            books.add(new Book("Book Title"+i,"Book Author "+i, "https://upload.wikimedia.org/wikipedia/en/thumb/7/70/Brisingr_book_cover.png/220px-Brisingr_book_cover.png", i));
        }

        // Recycle View Settings
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_search);
        mRecyclerView.setHasFixedSize(true);
        // used linear layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // specify an adapter
        mAdapter = new SearchAdapter(this, books);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void activateAdvancedSearch(View view) {
        if (advancesSearchLay.getVisibility() == View.INVISIBLE) {
            advancesSearchLay.setVisibility(View.VISIBLE);
            searchBt.setColorFilter(getResources().getColor(R.color.colorYellow));
        } else {
            advancesSearchLay.setVisibility(View.INVISIBLE);
            searchBt.setColorFilter(Color.WHITE);
        }
    }

    // When X button is pressed the search text is cleared
    public void clearSearchText(View view) {
        searchText.getText().clear();
    }
}
