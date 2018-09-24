package com.example.prasetyo.moviedb.ui.favorites;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.prasetyo.moviedb.R;

import static com.example.prasetyo.moviedb.database.DatabaseConstruct.getColumnString;
import com.example.prasetyo.moviedb.database.DatabaseConstruct.FavColumns;
import com.example.prasetyo.moviedb.model.Movie;
import com.example.prasetyo.moviedb.movie.GridAdapter;

import java.util.ArrayList;

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
    public void onBindViewHolder(FavoriteAdapter.ViewHolder holder, int position) {
        holder.txTitle.setText(getItem(position).getTitle());
        holder.txDate.setText(getItem(position).getDate());
        Glide.with(context)
                .load(getItem(position).getPoster())
                .into(holder.moviePoster);
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
