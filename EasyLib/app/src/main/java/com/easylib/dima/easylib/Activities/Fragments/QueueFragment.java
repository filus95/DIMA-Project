package com.easylib.dima.easylib.Activities.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easylib.dima.easylib.Adapters.QueueAdapter;
import com.easylib.dima.easylib.Adapters.QueueRecyclerItemTouchHelper;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

public class QueueFragment extends Fragment
        implements QueueRecyclerItemTouchHelper.QueueRecyclerItemTouchHelperListener {

    private static final String USER_INFO = "User Info";
    private ArrayList<AnswerClasses.Book> waitingBooks;
    private AnswerClasses.User userInfo;

    private TextView noBooksText;

    private RecyclerView mRecyclerView;
    private QueueAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_queue, container, false);

        noBooksText = (TextView) root.findViewById (R.id.fragment_queue_nobooks);

        if (waitingBooks.size () != 0) {
            // RecycleView setup
            mRecyclerView = (RecyclerView) root.findViewById (R.id.recycle_queue);
            mRecyclerView.setVisibility (View.VISIBLE);
            // improve performance
            mRecyclerView.setHasFixedSize (true);
            // used linear layout
            if ((getResources ().getConfiguration ().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
                // used linear layout
                mLayoutManager = new LinearLayoutManager (getContext ());
            } else if ((getResources ().getConfiguration ().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                // used grid layout
                mLayoutManager = new GridLayoutManager (getContext (), 2);
            }
            mRecyclerView.setLayoutManager (mLayoutManager);
            mRecyclerView.setItemAnimator (new DefaultItemAnimator ());
            // specify an adapter
            mAdapter = new QueueAdapter (getContext (), waitingBooks, userInfo);
            mRecyclerView.setAdapter (mAdapter);

            // adding item touch helper
            ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new QueueRecyclerItemTouchHelper (0, ItemTouchHelper.RIGHT, this);
            new ItemTouchHelper (itemTouchHelperCallback).attachToRecyclerView (mRecyclerView);
        } else {
            noBooksText.setVisibility (View.VISIBLE);
        }

        return root;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof QueueAdapter.QueueHolder) {
            // get the removed item name to display it in snack bar
            String name = waitingBooks.get(viewHolder.getAdapterPosition()).getTitle();

            // backup of removed item for undo purpose
            final AnswerClasses.Book deletedItem = waitingBooks.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            mAdapter.removeItem(viewHolder.getAdapterPosition());
        }
    }

    public void setData(AnswerClasses.User userInfo, ArrayList<AnswerClasses.Book> waitingBooks) {
        this.userInfo = userInfo;
        this.waitingBooks = waitingBooks;
    }
}
