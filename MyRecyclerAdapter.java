package com.fit2081.assignment12081;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.CustomViewHolder>  {

    ArrayList<AddCategoryNavDrawer> data = new ArrayList<>();

    private ArrayList<AddCategoryNavDrawer> categories;

    public void setData(ArrayList<AddCategoryNavDrawer> data) {

        this.data = data;
    }



    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_add_category, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(v);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {


        holder.tvCategoryId.setText(data.get(position).getCategoryNavId());
        holder.tvCategoryName.setText(data.get(position).getCategoryNavName());
        holder.tvEventCount.setText(String.valueOf(data.get(position).getEventNavCount()));
        if (data.get(position).isNavActive()) {
            holder.tvIsActive.setText("Yes");
        }else{
            holder.tvIsActive.setText("No");
        }

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();

            Intent intent = new Intent(context, GoogleMapActivity.class);
            intent.putExtra("eventLocation", data.get(position).getEventLocation());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        if (this.data != null) { // if data is not null
            return this.data.size(); // then return the size of ArrayList
        }
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCategoryId;
        public TextView tvCategoryName;

        public TextView tvEventCount;

        public TextView tvIsActive;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryId = itemView.findViewById(R.id.tv_categoryId);
            tvCategoryName = itemView.findViewById(R.id.tv_categoryName);
            tvEventCount = itemView.findViewById(R.id.tv_eventCount);
            tvIsActive = itemView.findViewById(R.id.tv_isActive);
        }
    }
}
