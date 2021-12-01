package com.azhar.laundry.view.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.azhar.laundry.R;
import com.azhar.laundry.model.nearby.ModelResults;

import java.util.ArrayList;

/**
 * Created by Azhar Rivaldi on 19-11-2021
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    ArrayList<ModelResults> modelResultArrayList = new ArrayList<>();
    Context context;

    public MainAdapter(Context context) {
        this.context = context;
    }

    public void setLocationAdapter(ArrayList<ModelResults> items) {
        modelResultArrayList.clear();
        modelResultArrayList.addAll(items);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rekomendasi, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        ModelResults modelResult = modelResultArrayList.get(position);

        //set rating
        float newValue = (float) modelResult.getRating();
        holder.ratingBar.setNumStars(5);
        holder.ratingBar.setStepSize((float) 0.5);
        holder.ratingBar.setRating(newValue);

        holder.tvNamaJalan.setText(modelResult.getVicinity());
        holder.tvNamaLokasi.setText(modelResult.getName());
        holder.tvRating.setText("(" + modelResult.getRating() + ")");
    }

    @Override
    public int getItemCount() {
        return modelResultArrayList.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearRute;
        TextView tvNamaJalan, tvNamaLokasi, tvRating;
        RatingBar ratingBar;

        public MainViewHolder(View itemView) {
            super(itemView);
            linearRute = itemView.findViewById(R.id.linearRute);
            tvNamaJalan = itemView.findViewById(R.id.tvNamaJalan);
            tvNamaLokasi = itemView.findViewById(R.id.tvNamaLokasi);
            tvRating = itemView.findViewById(R.id.tvRating);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }

}
