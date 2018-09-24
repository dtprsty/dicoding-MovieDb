package com.example.prasetyo.moviedb.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.prasetyo.moviedb.database.DatabaseConstruct.FavColumns;
import com.google.gson.Gson;

import static com.example.prasetyo.moviedb.database.DatabaseConstruct.getColumnString;

public class Movie implements Parcelable{

    private String id;
    private String title;
    private String date;
    private String poster;
    private String overview;
    private String banner;
    private String rating;
    private String voter;

    protected Movie(Parcel in) {
        id = in.readString();
        title = in.readString();
        date = in.readString();
        poster = in.readString();
        overview = in.readString();
        banner = in.readString();
        rating = in.readString();
        voter = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie(Cursor cursor){
        this.id = getColumnString(cursor, FavColumns.getId());
        this.title = getColumnString(cursor, FavColumns.getTitle());
        this.date = getColumnString(cursor, FavColumns.getDate());
        this.overview= getColumnString(cursor, FavColumns.getOverview());
        this.banner= getColumnString(cursor, FavColumns.getBanner());
        this.poster= getColumnString(cursor, FavColumns.getPoster());
        this.rating= getColumnString(cursor, FavColumns.getRating());
        this.voter= getColumnString(cursor, FavColumns.getVoter());
    }

    public Movie(){

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getVoter() {
        return voter;
    }

    public void setVoter(String voter) {
        this.voter = voter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String parcelMovie() {
        return new Gson().toJson(this);
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(banner);
        dest.writeString(poster);
        dest.writeString(rating);
        dest.writeString(voter);
        dest.writeString(overview);
    }
}
