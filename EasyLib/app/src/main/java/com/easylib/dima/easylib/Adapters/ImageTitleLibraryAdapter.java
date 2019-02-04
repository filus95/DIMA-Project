package com.easylib.dima.easylib.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.Activities.Fragments.MainActivity;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

import AnswerClasses.LibraryDescriptor;

public class ImageTitleLibraryAdapter extends RecyclerView.Adapter<ImageTitleLibraryAdapter.ImageTitleLibraryHolder> {

    ArrayList<LibraryDescriptor> libraries;
    Context context;

    public ImageTitleLibraryAdapter(Context context, ArrayList libraries) {
        this.context = context;
        this.libraries = libraries;
    }

    @Override
    public ImageTitleLibraryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item
        View v = LayoutInflater.from(context).inflate(R.layout.image_and_title_item, parent, false);
        return new ImageTitleLibraryHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageTitleLibraryHolder holder, int position) {
        // set the data in items
        LibraryDescriptor library = libraries.get(position);
        holder.title.setText(library.getLib_name());
        Glide.with(context)
                .load(library.getImage_link())
                .into(holder.image);

        // implemented onClickListener event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).showLibrary(library);
            }
        });
    }

    @Override
    public int getItemCount() {
        return libraries.size();
    }

    static class ImageTitleLibraryHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView title;

        public ImageTitleLibraryHolder(View v) {
            super(v);
            image = v.findViewById(R.id.image_and_title_item_image);
            title = v.findViewById(R.id.image_and_title_item_title);
        }
    }
}
