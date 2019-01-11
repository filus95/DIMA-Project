package com.easylib.dima.easylib.Main.QueueFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easylib.dima.easylib.Model.Book;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QueueFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QueueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QueueFragment extends Fragment
        implements QueueRecyclerItemTouchHelper.QueueRecyclerItemTouchHelperListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ArrayList<Book> books = new ArrayList<Book>();

    private RecyclerView mRecyclerView;
    private QueueAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public QueueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QueueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QueueFragment newInstance(String param1, String param2) {
        QueueFragment fragment = new QueueFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_queue, container, false);

        // JUST FOR TEST
        int i;
        for(i=0; i<15; i++) {
            books.add(new Book("Book Title"+i,"Book Author "+i, "https://upload.wikimedia.org/wikipedia/en/thumb/7/70/Brisingr_book_cover.png/220px-Brisingr_book_cover.png", i));
        }

        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycle_queue);

        // improve performance
        mRecyclerView.setHasFixedSize(true);

        // used linear layout
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));

        // specify an adapter
        mAdapter = new QueueAdapter(getContext(), books);
        mRecyclerView.setAdapter(mAdapter);

        // adding item touch helper
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new QueueRecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);

        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof QueueAdapter.QueueHolder) {
            // get the removed item name to display it in snack bar
            String name = books.get(viewHolder.getAdapterPosition()).getTitle();

            // backup of removed item for undo purpose
            final Book deletedItem = books.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            mAdapter.removeItem(viewHolder.getAdapterPosition());
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
