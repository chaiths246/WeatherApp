package com.example.chaithra.scribd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Date;
import androidx.recyclerview.widget.RecyclerView;

public class weatherAdapter extends RecyclerView.Adapter<weatherAdapter.MyViewHolder> {
    private java.util.List<List> mDataset;

    public weatherAdapter(java.util.List<List> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public weatherAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvDate.setText((mDataset.get(position).getDtTxt()));
        holder.tvhumadity.setText("Humidity: "+mDataset.get(position).getMain().getHumidity());
        holder.tvTempmax.setText("TempMax: "+mDataset.get(position).getMain().getTempMax());
        holder.tvTempmin.setText("TempMin: "+mDataset.get(position).getMain().getTempMin());

//        holder.ivWeather.setImageBitmap(mDataset.get(position).getWeather().get(position).getIcon());

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDate;
        public TextView tvTempmax;
        public TextView tvTempmin;
        public TextView tvhumadity;
        public ImageView ivWeather;

        public MyViewHolder(View v) {
            super(v);
            tvTempmax = v.findViewById(R.id.tvTempmax);
            tvTempmin = v.findViewById(R.id.tvTempmin);
            tvhumadity = v.findViewById(R.id.tvhumadity);
            ivWeather = v.findViewById(R.id.ivWeather);
            tvDate = v.findViewById(R.id.tvDate);
        }
    }



}