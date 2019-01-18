package com.easylib.dima.easylib.Activities.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.easylib.dima.easylib.Activities.RatedBooksActivity;
import com.easylib.dima.easylib.Adapters.LibraryAdapter;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

import AnswerClasses.LibraryDescriptor;

public class ProfileFragment extends Fragment {

    private ArrayList<LibraryDescriptor> libraries = new ArrayList<LibraryDescriptor>();

    private RecyclerView mRecyclerView;
    private LibraryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        // JUST FOR TEST
        int i;
        for(i=0; i<15; i++) {
            LibraryDescriptor lib = new LibraryDescriptor();
            lib.setAddress("via bla bla bla "+i+" (MI)");
            lib.setLib_name("Library Name"+i);
            lib.setImage_link("https://www.ucl.ac.uk/library/sites/library/files/students-studying.jpg");
            libraries.add(lib);
        }

        // Set Listener on Buttons
        Button button1 = (Button) root.findViewById(R.id.profile_fragment_rated_books_button);
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), RatedBooksActivity.class);
                startActivity(intent);
            }
        });
        Button button2 = (Button) root.findViewById(R.id.profile_fragment_logout_button);
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO : implement logout
            }
        });
        ImageButton button3 = (ImageButton) root.findViewById(R.id.profile_fragment_edit_button);
        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO : implement logout
            }
        });

        // Recycle setup
        mRecyclerView = (RecyclerView) root.findViewById(R.id.profile_library_recycle);
        // improve performance
        mRecyclerView.setHasFixedSize(true);
        // used linear layout
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // specify an adapter
        mAdapter = new LibraryAdapter(getContext(), libraries);
        mRecyclerView.setAdapter(mAdapter);

        return root;
    }
}
