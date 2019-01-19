package com.easylib.dima.easylib.Activities.Lists;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.easylib.dima.easylib.Adapters.LibraryAdapter;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

import AnswerClasses.LibraryDescriptor;

public class LibraryListActivity extends AppCompatActivity {

    private ArrayList<LibraryDescriptor> libraries = new ArrayList<LibraryDescriptor>();

    // recycle view
    private RecyclerView mRecyclerView;
    private LibraryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        int i;
        for(i=0; i<15; i++) {
            LibraryDescriptor lib = new LibraryDescriptor();
            lib.setAddress("via bla bla bla "+i+" (MI)");
            lib.setLib_name("Library Name"+i);
            lib.setImage_link("https://www.ucl.ac.uk/library/sites/library/files/students-studying.jpg");
            libraries.add(lib);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.list_recycle);
        // improve performance
        mRecyclerView.setHasFixedSize(true);
        // used linear layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // specify an adapter
        mAdapter = new LibraryAdapter(this, libraries);
        mRecyclerView.setAdapter(mAdapter);
    }
}
