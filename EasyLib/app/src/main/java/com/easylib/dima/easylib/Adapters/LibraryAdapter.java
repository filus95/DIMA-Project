package com.easylib.dima.easylib.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.Activities.Lists.LibraryListActivity;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

import AnswerClasses.LibraryDescriptor;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryHolder> {

    ArrayList<LibraryDescriptor> libraries;
    Context context;

    public LibraryAdapter(Context context, ArrayList libraries) {
        this.context = context;
        this.libraries = libraries;
    }

    @Override
    public LibraryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item
        View v = LayoutInflater.from(context).inflate(R.layout.library_list_item, parent, false);
        return new LibraryHolder(v);
    }

    @Override
    public void onBindViewHolder(LibraryHolder holder, int position) {
        // set the data in items
        LibraryDescriptor library = libraries.get(position);
        Glide.with(context)
                .load(library.getImage_link())
                .into(holder.image);
        holder.name.setText(library.getLib_name());
        holder.location.setText(library.getAddress());

        // Set idLib to all News
        for (AnswerClasses.Event event : library.getLibraryContent ().getEvents ()){
            event.setIdLib (library.getId_lib ());
        }

        // implemented onClickListener event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LibraryListActivity)context).showLibrary(library);
            }
        });
    }

    @Override
    public int getItemCount() {
        return libraries.size();
    }

    static class LibraryHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView name;
        protected TextView location;

        public LibraryHolder(View v) {
            super(v);
            image = v.findViewById(R.id.library_item_img);
            name = v.findViewById(R.id.library_item_name);
            location = v.findViewById(R.id.library_item_location);
        }
    }
}
