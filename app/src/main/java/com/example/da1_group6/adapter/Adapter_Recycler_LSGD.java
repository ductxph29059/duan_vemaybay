package com.example.da1_group6.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_group6.R;
import com.example.da1_group6.model.LSGD;

import java.util.ArrayList;

public class Adapter_Recycler_LSGD extends RecyclerView.Adapter<Adapter_Recycler_LSGD.ViewHolder> {
    ArrayList<LSGD> list;
    Context context;

    public Adapter_Recycler_LSGD(ArrayList<LSGD> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.layout_item1_lsgd, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LSGD ls = list.get(position);
        holder.tvtitle.setText(ls.getTitle());
        holder.tvtime.setText(ls.getTime());

        String title = ls.getTitle();

        String sotienstr = String.valueOf(ls.getSotien());
        StringBuilder str = new StringBuilder(sotienstr);
        for (int i = str.length(); i > 0; i -= 3) {
            str.insert(i, " ");
        }

        if(title.equalsIgnoreCase("Mua vé máy bay")) {
            holder.tvsotien.setText( "- " + str + " Đ");
        } else {
            holder.tvsotien.setText( "+ " + str + " Đ");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvtitle, tvsotien, tvtime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtime = itemView.findViewById(R.id.tv_time_lsgd);
            tvsotien = itemView.findViewById(R.id.tv_sotien_lsgd);
            tvtitle = itemView.findViewById(R.id.tv_title_lsgd);
        }
    }
}
