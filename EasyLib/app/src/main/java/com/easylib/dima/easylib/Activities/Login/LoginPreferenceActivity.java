package com.easylib.dima.easylib.Activities.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.easylib.dima.easylib.Activities.Fragments.MainActivity;
import com.easylib.dima.easylib.Adapters.PrefLibAdapter;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

import AnswerClasses.LibraryDescriptor;

public class LoginPreferenceActivity extends AppCompatActivity {

    private ArrayList<LibraryDescriptor> libraries = new ArrayList<LibraryDescriptor>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_preference);

        // JUST FOR TEST
        int i;
        for(i=0; i<15; i++) {
            LibraryDescriptor lib = new LibraryDescriptor();
            lib.setAddress("via bla bla bla "+i+" (MI)");
            lib.setLib_name("Library Name"+i);
            lib.setImage_link("https://www.ucl.ac.uk/library/sites/library/files/students-studying.jpg");
            libraries.add(lib);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_pref);

        // improve performance
        mRecyclerView.setHasFixedSize(true);

        // used linear layout
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new PrefLibAdapter(this, libraries);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void skip(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
