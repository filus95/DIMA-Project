package com.easylib.dima.easylib.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easylib.dima.easylib.Activities.Fragments.MainActivity;
import com.easylib.dima.easylib.Model.Biblo;
import com.easylib.dima.easylib.R;

import java.util.ArrayList;

public class PrefLibAdapter extends RecyclerView.Adapter<PrefLibAdapter.PrefLibHolder> {

    ArrayList<Biblo> libraries;
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
        Biblo biblo = libraries.get(position);
        holder.lName.setText(biblo.getName());
        holder.lAddr.setText(biblo.getAddr());

        // implemented onClickListener event
        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: call method
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
        protected TextView lName;
        protected TextView lAddr;

        public PrefLibHolder(View v) {
            super(v);
            lName = v.findViewById(R.id.lib_name);
            lAddr = v.findViewById(R.id.lib_addr);
        }
    }
}
