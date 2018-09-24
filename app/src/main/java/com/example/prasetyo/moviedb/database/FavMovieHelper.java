package com.example.prasetyo.moviedb.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.prasetyo.moviedb.database.DatabaseConstruct.FavColumns;
import com.example.prasetyo.moviedb.model.Movie;

public class FavMovieHelper {
    private Context context;
    private DbHelper databaseHelper;
    private SQLiteDatabase database;

    public FavMovieHelper(Context context) {
        this.context = context;
    }

    public FavMovieHelper open() throws SQLException {
        databaseHelper = new DbHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }

    public long insert(Movie movie) {
        return database.insert(DatabaseConstruct.TABLE_FAVORITES, null, movieContentValues(movie));
    }

    private ContentValues movieContentValues(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(FavColumns._ID, movie.getId());
        values.put(FavColumns.getTitle(), movie.getTitle());
        values.put(FavColumns.getDate(), movie.getDate());
        values.put(FavColumns.getOverview(), movie.getOverview());
        values.put(FavColumns.getPoster(), movie.getPoster());
        values.put(FavColumns.getBanner(), movie.getBanner());
        values.put(FavColumns.getRating(), movie.getRating());
        values.put(FavColumns.getVoter(), movie.getVoter());
        return values;
    }

    public int delete(String id) {
        return database.delete(
                DatabaseConstruct.TABLE_FAVORITES,
                FavColumns._ID + " = ?", new String[]{id}
        );
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DatabaseConstruct.TABLE_FAVORITES, null
                ,FavColumns._ID + " = ?", new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }

    public Cursor queryProvider(){
        return database.query(DatabaseConstruct.TABLE_FAVORITES
                ,null
                ,null
                ,null
                ,null
                ,null
                ,FavColumns._ID + " DESC");
    }

    public long insertProvider(ContentValues values){
        return database.insert(
                DatabaseConstruct.TABLE_FAVORITES, null, values
        );
    }

    public int updateProvider(String id, Movie movie){
        return database.update(
                DatabaseConstruct.TABLE_FAVORITES,
                movieContentValues(movie),
                FavColumns._ID +" = ?",new String[]{id}
        );
    }

    public int updateProvider(String id, ContentValues values){
        return database.update(
                DatabaseConstruct.TABLE_FAVORITES,
                values,
                FavColumns._ID +" = ?",new String[]{id}
        );
    }

    public int deleteProvider(String id){
        return database.delete(
                DatabaseConstruct.TABLE_FAVORITES,
                FavColumns._ID + " = ?", new String[]{id}
        );
    }
}
