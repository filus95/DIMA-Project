package com.easylib.dima.easylib.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easylib.dima.easylib.Activities.Fragments.MainActivity;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

import AnswerClasses.LibraryDescriptor;

public class PrefLibAdapter extends RecyclerView.Adapter<PrefLibAdapter.PrefLibHolder> {

    ArrayList<LibraryDescriptor> libraries;
    Context context;

    public PrefLibAdapter(Context context, ArrayList libraries) {
        this.context = context;
        this.libraries = libraries;
    }

    @Override
    public PrefLibHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item
        View v = LayoutInflater.from(context).inflate(R.layout.login_preference_item, parent, false);
        return new PrefLibHolder(v);
    }

    @Override
    public void onBindViewHolder(PrefLibHolder holder, int position) {
        // set the data in items
        LibraryDescriptor library = libraries.get(position);
        Glide.with(context)
                .load(library.getImage_link())
                .into(holder.image);
        holder.name.setText(library.getLib_name());
        holder.location.setText(library.getAddress());

        // implemented onClickListener event
        holder.itemView.setOnClickListener(new OnClickListener() {
            // TODO : onClick set Preference
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return libraries.size();
    }

    static class PrefLibHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView name;
        protected TextView location;

        public PrefLibHolder(View v) {
            super(v);
            image = v.findViewById(R.id.login_pref_image);
            name = v.findViewById(R.id.login_pref_name);
            location = v.findViewById(R.id.login_pref_addr);
        }
    }
}
