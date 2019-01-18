package com.easylib.dima.easylib.Activities.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public void editProfile(View view) {

    }

    public void readBooks(View view) {

    }

    public void logout(View view) {

    }
}
