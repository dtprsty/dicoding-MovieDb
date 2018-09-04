package com.example.prasetyo.moviedb.movie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.widget.ANImageView;
import com.example.prasetyo.moviedb.R;
import com.example.prasetyo.moviedb.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridAdapter extends
        RecyclerView.Adapter<GridAdapter.ViewHolder> {


    private Context context;
    private ArrayList<Movie> list;

    public GridAdapter(Context context, ArrayList<Movie> list) {
        this.context = context;
        this.list = list;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgPoster)
        ANImageView moviePoster;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // membuat view baru
        View v = LayoutInflater.from(context).inflate(R.layout.grid_movie_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.moviePoster.setImageUrl(list.get(position).getPoster());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}