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

import com.easylib.dima.easylib.Activities.Lists.LibraryListActivity;
import com.easylib.dima.easylib.Activities.RatedBooksActivity;
import com.easylib.dima.easylib.Adapters.ImageTitleLibraryAdapter;
import com.easylib.dima.easylib.Adapters.RatedBooksAdapter;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

import AnswerClasses.Book;
import AnswerClasses.LibraryDescriptor;
import AnswerClasses.User;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    private ArrayList<LibraryDescriptor> prefLibraries;
    private ArrayList<Book> ratedBooks;
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
    // for Rated Books
    private RecyclerView mRecyclerView2;
    private RatedBooksAdapter mAdapter2;
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
                Intent intent = new Intent(getContext(), LibraryListActivity.class);
                startActivity(intent);
            }
        });
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
                SharedPreferences sp = getContext().getSharedPreferences(LOGIN, MODE_PRIVATE);
                sp.edit().clear().apply();
            }
        });
        ImageButton button3 = (ImageButton) root.findViewById(R.id.profile_fragment_edit_button);
        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO : implement edit profile activity
            }
        });

        // setup user info
        name = (TextView) root.findViewById(R.id.profile_name);
        email = (TextView) root.findViewById(R.id.profile_email);
        userID = (TextView) root.findViewById(R.id.profile_fragment_user_id);
        name.setText(userInfo.getName() + " " + userInfo.getSurname());
        email.setText(userInfo.getEmail());
        userID.setText(String.valueOf(userInfo.getUser_id()));

        // Recycle setup Favourite Libraries
        mRecyclerView = (RecyclerView) root.findViewById(R.id.profile_fav_lib_recycle);
        // improve performance
        mRecyclerView.setHasFixedSize(true);
        // used linear layout
        mLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // specify an adapter
        mAdapter = new ImageTitleLibraryAdapter(getContext(), prefLibraries);
        mRecyclerView.setAdapter(mAdapter);

        // Recycle setup Favourite Libraries
        mRecyclerView2 = (RecyclerView) root.findViewById(R.id.profile_rated_books_recycle);
        // improve performance
        mRecyclerView2.setHasFixedSize(true);
        // used linear layout
        mLayoutManager2 = new GridLayoutManager(getContext(), 3);
        mRecyclerView2.setLayoutManager(mLayoutManager2);
        mRecyclerView2.setItemAnimator(new DefaultItemAnimator());
        // specify an adapter
        mAdapter2 = new RatedBooksAdapter(getContext(), ratedBooks);
        mRecyclerView2.setAdapter(mAdapter2);

        return root;
    }

    public void setData (User userInfo, ArrayList<LibraryDescriptor> prefLibraries, ArrayList<Book> ratedBooks) {
        this.userInfo = userInfo;
        this.prefLibraries = prefLibraries;
        this.ratedBooks = ratedBooks;
    }
}
