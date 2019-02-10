package com.easylib.dima.easylib.Activities.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.easylib.dima.easylib.Activities.EditProfileActivity;
import com.easylib.dima.easylib.Adapters.ImageTitleEventAdapter;
import com.easylib.dima.easylib.Adapters.ImageTitleLibraryAdapter;
import com.easylib.dima.easylib.Adapters.ReadBooksAdapter;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

import AnswerClasses.Book;
import AnswerClasses.LibraryDescriptor;
import AnswerClasses.User;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    private static final String USER_INFO = "User Info";
    private ArrayList<LibraryDescriptor> prefLibraries;
    ArrayList<AnswerClasses.Event> joinedEvents;
    private ArrayList<Book> readBooks;
    private User userInfo;

    private static final String LOGIN = "Login";

    // User info components
    private TextView name;
    private TextView email;
    private TextView userID;

    // for Favourite Libraries
    private RecyclerView mRecyclerView;
    private ImageTitleLibraryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    // for Events Joined
    private RecyclerView mRecyclerView1;
    private ImageTitleEventAdapter mAdapter1;
    private RecyclerView.LayoutManager mLayoutManager1;
    // for Rated Books
    private RecyclerView mRecyclerView2;
    private ReadBooksAdapter mAdapter2;
    private RecyclerView.LayoutManager mLayoutManager2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        // Set Listener on Buttons
        Button button0 = (Button) root.findViewById(R.id.profile_fragment_fav_lib_button);
        button0.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getActivity()).getPrefLibraries (false, false);
            }
        });
        Button button4 = (Button) root.findViewById(R.id.profile_fragment_events_button);
        button4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getActivity()).getJoinedEvents (false);
            }
        });
        Button button1 = (Button) root.findViewById(R.id.profile_fragment_rated_books_button);
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getActivity()).getReadBooks (false);
            }
        });
        Button button2 = (Button) root.findViewById(R.id.profile_fragment_logout_button);
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SharedPreferences sp = getContext().getSharedPreferences(LOGIN, MODE_PRIVATE);
                sp.edit().clear().apply();
                ((MainActivity)getActivity ()).goToLogin ();
            }
        });
        ImageButton button3 = (ImageButton) root.findViewById(R.id.profile_fragment_edit_button);
        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent editProfileIntent = new Intent(getContext (), EditProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(USER_INFO, userInfo);
                editProfileIntent.putExtras(bundle);
                ((MainActivity)getActivity ()).doUnbindService ();
                startActivity(editProfileIntent);
            }
        });

        // setup user info
        name = (TextView) root.findViewById(R.id.profile_name);
        email = (TextView) root.findViewById(R.id.profile_email);
        userID = (TextView) root.findViewById(R.id.profile_fragment_user_id);
        name.setText(userInfo.getName() + " " + userInfo.getSurname());
        email.setText(userInfo.getEmail());
        userID.setText(String.valueOf(userInfo.getUser_id()));

        // get first 3 items of news, events, books
        int i;
        ArrayList<LibraryDescriptor> fewPrefLibraries = new ArrayList<LibraryDescriptor>();
        ArrayList<AnswerClasses.Event> fewJoinedEvents = new ArrayList<AnswerClasses.Event>();
        ArrayList<Book> fewReadBooks = new ArrayList<Book>();
        for(i=0; i<3; i++) {
            if (i < prefLibraries.size ())
                fewPrefLibraries.add(prefLibraries.get(i));
            if (i < joinedEvents.size ())
                fewJoinedEvents.add(joinedEvents.get(i));
            if (i < readBooks.size ())
                fewReadBooks.add(readBooks.get(i));
        }

        // Recycle setup Favourite Libraries
        mRecyclerView = (RecyclerView) root.findViewById(R.id.profile_fav_lib_recycle);
        // improve performance
        mRecyclerView.setHasFixedSize(true);
        // used linear layout
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // specify an adapter
        mAdapter = new ImageTitleLibraryAdapter(getContext(), fewPrefLibraries);
        mRecyclerView.setAdapter(mAdapter);

        // Recycle setup Joined Events
        mRecyclerView1 = (RecyclerView) root.findViewById(R.id.profile_events_recycle);
        // improve performance
        mRecyclerView1.setHasFixedSize(true);
        // used linear layout
        mLayoutManager1 = new GridLayoutManager(getContext(), 3);
        mRecyclerView1.setLayoutManager(mLayoutManager1);
        mRecyclerView1.setItemAnimator(new DefaultItemAnimator());
        // specify an adapter
        mAdapter1 = new ImageTitleEventAdapter(getContext(), fewJoinedEvents, userInfo);
        mRecyclerView1.setAdapter(mAdapter1);

        // Recycle setup Read Books
        mRecyclerView2 = (RecyclerView) root.findViewById(R.id.profile_rated_books_recycle);
        // improve performance
        mRecyclerView2.setHasFixedSize(true);
        // used linear layout
        mLayoutManager2 = new GridLayoutManager(getContext(), 3);
        mRecyclerView2.setLayoutManager(mLayoutManager2);
        mRecyclerView2.setItemAnimator(new DefaultItemAnimator());
        // specify an adapter
        mAdapter2 = new ReadBooksAdapter (getContext(), fewReadBooks, userInfo);
        mRecyclerView2.setAdapter(mAdapter2);

        return root;
    }

    public void setData (User userInfo, ArrayList<LibraryDescriptor> prefLibraries, ArrayList<Book> readBooks, ArrayList<AnswerClasses.Event> joinedEvents) {
        this.userInfo = userInfo;
        this.prefLibraries = prefLibraries;
        this.joinedEvents = joinedEvents;
        this.readBooks = readBooks;
    }
}
