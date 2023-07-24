package com.example.da1_group6.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_group6.R;
import com.example.da1_group6.model.HangMB;

import java.util.ArrayList;

public class Adapter_Recycler_hangmb_admin extends RecyclerView.Adapter<Adapter_Recycler_hangmb_admin.ViewHolder> {
    ArrayList<HangMB> list;
    Context context;

    public Adapter_Recycler_hangmb_admin(ArrayList<HangMB> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_Recycler_hangmb_admin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.layout_item1_hangmb, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Recycler_hangmb_admin.ViewHolder holder, int position) {
        HangMB hmb = list.get(position);
        holder.tv_tenhmb.setText(hmb.getTenmb());
        if(hmb.getMamb().equalsIgnoreCase("VNA")) {
            holder.img.setImageResource(R.drawable.logo_vnairlines);
        } else if (hmb.getMamb().equalsIgnoreCase("VJA")) {
            holder.img.setImageResource(R.drawable.logo_vietjet);
        } else if (hmb.getMamb().equalsIgnoreCase("BBA")) {
            holder.img.setImageResource(R.drawable.logo_bamboo);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenhmb;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenhmb = itemView.findViewById(R.id.tv_tenhmb);
            img = itemView.findViewById(R.id.logo_mb_hmb_admin);

        }
    }
}
