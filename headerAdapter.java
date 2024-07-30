package com.fit2081.assignment12081;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class headerAdapter extends RecyclerView.Adapter<headerAdapter.CustomViewHolder> {

    ArrayList<Header> data3 = new ArrayList<>();

    public void setData3(ArrayList<Header> data3) {

        this.data3.add(new Header("", "", "", ""));
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.headercardview, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.tvTheId.setText("ID");
        holder.tvtheName.setText("Name");
        holder.tvtheCount.setText("Event Count");
        holder.tvtheActive.setText("Active?");




    }

    @Override
    public int getItemCount() {
        if (this.data3 != null) { // if data is not null
            return this.data3.size(); // then return the size of ArrayList
        }
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTheId;
        public TextView tvtheName;

        public TextView tvtheCount;

        public TextView tvtheActive;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTheId = itemView.findViewById(R.id.tv_the_Id);
            tvtheName = itemView.findViewById(R.id.tv_the_Name);
            tvtheCount = itemView.findViewById(R.id.tv_the_Count);
            tvtheActive = itemView.findViewById(R.id.tv_the_Active);
        }
    }
}
