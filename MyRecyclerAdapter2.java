package com.fit2081.assignment12081;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerAdapter2 extends RecyclerView.Adapter<MyRecyclerAdapter2.CustomViewHolder> {

    ArrayList<EventNavDrawer> data2 = new ArrayList<>();

    public void setData(ArrayList<EventNavDrawer> data) {

        this.data2 = data;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_event_list, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {


        holder.tvEventId.setText(data2.get(position).getEventNavId());
        holder.tvEventName.setText(data2.get(position).getEventNavName());
        holder.tvCategoryId.setText(data2.get(position).getCategoryAddNavId());
        holder.tvTicketCount.setText(String.valueOf(data2.get(position).getTicketNavCount()));
        if (data2.get(position).isAddNavActive()) {
            holder.tvIsAddActive.setText("Active");
        }else{
            holder.tvIsAddActive.setText("Inactive");
        }

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();

            Intent intent = new Intent(context, EventGoogleResult.class);
            intent.putExtra("eventName", data2.get(position).getEventNavName());
            context.startActivity(intent);
        });



    }

    @Override
    public int getItemCount() {
        if (this.data2 != null) { // if data is not null
            return this.data2.size(); // then return the size of ArrayList
        }
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView tvEventId;
        public TextView tvEventName;
        public TextView tvCategoryId;

        public TextView tvTicketCount;

        public TextView tvIsAddActive;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEventId= itemView.findViewById(R.id.tv_event_id);
            tvEventName= itemView.findViewById(R.id.tv_event_name);
            tvCategoryId= itemView.findViewById(R.id.tv_category_id);
            tvTicketCount= itemView.findViewById(R.id.tv_tickets_count);
            tvIsAddActive= itemView.findViewById(R.id.tv_is_add_active);

        }
    }
}
