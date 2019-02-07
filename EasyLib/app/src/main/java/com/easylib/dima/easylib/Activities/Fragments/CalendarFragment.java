package com.easylib.dima.easylib.Activities.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easylib.dima.easylib.Adapters.CalendarAdapter;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;


public class CalendarFragment extends Fragment {

    private ArrayList<AnswerClasses.Reservation> reservations;
    private AnswerClasses.User userInfo;
    private ArrayList<String> dates = new ArrayList<String>();

    // Dates RecycleView
    private RecyclerView mRecyclerView;
    private CalendarAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        // Inizialize first item with books to take
        for (AnswerClasses.Reservation r : reservations) {
            if (r.isTaken () == false)
                dates.add ("To Take");
        }
        // Fill dates ArrayList with distinct dates
        for (AnswerClasses.Reservation r : reservations) {
            for (String d : dates) {
                if (r.isTaken () == true) {
                    if (d == r.getEnd_res_date ().toString ().replace ("T", "  ")){
                        break;
                    }
                }
            }
            dates.add (r.getEnd_res_date ().toString ().replace ("T", "  "));
        }

        // RecycleView setup
        mRecyclerView = (RecyclerView) root.findViewById(R.id.fragment_calendar_recycle);
        // improve performance
        mRecyclerView.setHasFixedSize(true);
        // used linear layout
        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            // used linear layout
            mLayoutManager = new LinearLayoutManager(getContext());
        } else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            // used grid layout
            mLayoutManager = new GridLayoutManager(getContext(), 2);
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // specify an adapter
        mAdapter = new CalendarAdapter(getContext(), reservations, dates, userInfo);
        mRecyclerView.setAdapter(mAdapter);

        return root;
    }

    public void setData(ArrayList<AnswerClasses.Reservation> reservations, AnswerClasses.User userInfo) {
        this.reservations = reservations;
        this.userInfo = userInfo;
    }
}
