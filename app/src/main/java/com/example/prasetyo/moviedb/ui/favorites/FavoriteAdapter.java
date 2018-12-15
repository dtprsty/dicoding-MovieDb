package com.example.prasetyo.moviedb.ui.favorites;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.prasetyo.moviedb.R;
import com.example.prasetyo.moviedb.database.DatabaseConstruct;
import com.example.prasetyo.moviedb.model.Movie;
import com.example.prasetyo.moviedb.ui.detail.DetailMovieActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Context context;
    private Cursor list;

    public FavoriteAdapter(Context context, Cursor list) {
        this.context = context;
        this.list = list;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgPoster)
        ImageView moviePoster;
        @BindView(R.id.txTitle)
        TextView txTitle;
        @BindView(R.id.txDate)
        TextView txDate;
        @BindView(R.id.cardView)
        CardView cardview;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // membuat view baru
        View v = LayoutInflater.from(context).inflate(R.layout.grid_movie_row, parent, false);
        FavoriteAdapter.ViewHolder vh = new FavoriteAdapter.ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(FavoriteAdapter.ViewHolder holder, final int position) {
        holder.txTitle.setText(getItem(position).getTitle());
        holder.txDate.setText(getItem(position).getDate());
        Glide.with(context)
                .load(getItem(position).getPoster())
                .into(holder.moviePoster);
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent i = new Intent(v.getContext(), DetailMovieActivity.class);
                Uri uri = Uri.parse(DatabaseConstruct.CONTENT_URI+"/"+getItem(position).getId());
                i.setData(uri);
                v.getContext().startActivity(i);
            }
        });
    }

    private Movie getItem(int position){
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Movie(list);
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.getCount();
    }
}
