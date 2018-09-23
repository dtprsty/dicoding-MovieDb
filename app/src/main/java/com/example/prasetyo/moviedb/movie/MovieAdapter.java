package com.example.prasetyo.moviedb.movie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.prasetyo.moviedb.R;
import com.example.prasetyo.moviedb.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends
        RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Movie> list;

    public MovieAdapter(Context context, ArrayList<Movie> list) {
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txTitle)
        TextView txTitle;
        @BindView(R.id.txDate)
        TextView txDate;
        @BindView(R.id.txOverview)
        TextView txOverview;
        @BindView(R.id.imgPoster)
        ImageView moviePoster;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // membuat view baru
        View v = LayoutInflater.from(context).inflate(R.layout.list_movie_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.txTitle.setText(list.get(position).getTitle());
        holder.txOverview.setText(list.get(position).getOverview());
        holder.txDate.setText(list.get(position).getDate());
        Glide.with(context)
                .load(list.get(position).getPoster())
                .into(holder.moviePoster);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

}