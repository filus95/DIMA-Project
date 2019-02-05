package com.easylib.dima.easylib.Activities.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.easylib.dima.easylib.Adapters.HomeAdapter;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

import AnswerClasses.LibraryDescriptor;


public class HomeFragment extends Fragment {

    private ArrayList<LibraryDescriptor> librariesPref;
    private AnswerClasses.User userInfo;

    // recycle view
    private RecyclerView mRecyclerView;
    private HomeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Set Listener on Buttons
        Button button = (Button) root.findViewById(R.id.allLibraries_home_fragment);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getActivity()).getAllLibraries();
            }
        });

        // Recycle View Settings
        mRecyclerView = (RecyclerView) root.findViewById(R.id.fragment_home_recycle);
        mRecyclerView.setHasFixedSize(true);
        // used linear layout
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // specify an adapter
        // TODO : change adapter construction
        mAdapter = new HomeAdapter(getContext(), librariesPref, userInfo);
        mRecyclerView.setAdapter(mAdapter);

        return root;
    }

    public void setData(ArrayList<LibraryDescriptor> libraries, AnswerClasses.User userInfo) {
        this.librariesPref = libraries;
        this.userInfo = userInfo;
    }
}
