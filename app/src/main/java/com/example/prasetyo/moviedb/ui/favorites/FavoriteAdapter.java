package com.example.prasetyo.moviedb.ui.favorites;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.prasetyo.moviedb.R;

import static com.example.prasetyo.moviedb.database.DatabaseConstruct.getColumnString;
import com.example.prasetyo.moviedb.database.DatabaseConstruct.FavColumns;

public class FavoriteAdapter extends CursorAdapter {

    public FavoriteAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_movie_row, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        if (cursor != null){
            TextView txTitle = (TextView)view.findViewById(R.id.txTitle);
            TextView txDate = (TextView)view.findViewById(R.id.txDate);
            ImageView imgPoster = (ImageView)view.findViewById(R.id.imgPoster);

            txTitle.setText(getColumnString(cursor,FavColumns.getTitle()));
            txDate.setText(getColumnString(cursor,FavColumns.getDate()));
            Glide.with(context)
                    .load(FavColumns.getPoster())
                    .into(imgPoster);
        }
    }
}
