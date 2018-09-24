package com.example.prasetyo.moviedb.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseConstruct {
    public static final String DB_NAME = "dbFavMovie";
    public static final String TABLE_FAVORITES = "favorites";

    public static final int DB_VERSION = 1;

    public static final String CONTENT_AUTHORITY = "com.example.prasetyo.moviedb";

    public static final class FavColumns implements BaseColumns {

        private static final String title = "title";
        private static final String id = "id";

        public static String getId() {
            return id;
        }

        private static final String date = "date";
        private static final String poster = "poster";
        private static final String overview = "overview";
        private static final String banner = "banner";
        private static final String rating = "rating";
        private static final String voter = "voter";


        public static String getBanner() {
            return banner;
        }

        public static String getRating() {
            return rating;
        }

        public static String getVoter() {
            return voter;
        }

        public static String getTitle() {
            return title;
        }

        public static String getOverview() {
            return overview;
        }

        public static String getDate() {
            return date;
        }

        public static String getPoster() {
            return poster;
        }

    }

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(CONTENT_AUTHORITY)
            .appendPath(TABLE_FAVORITES)
            .build();

    public static String createTable() {
        return "CREATE TABLE " + TABLE_FAVORITES + " (" +
                FavColumns._ID + " TEXT PRIMARY KEY," +
                FavColumns.getTitle() + " TEXT," +
                FavColumns.getDate() + " TEXT," +
                FavColumns.getOverview() + " TEXT," +
                FavColumns.getRating() + " TEXT," +
                FavColumns.getPoster() + " TEXT," +
                FavColumns.getBanner() + " TEXT," +
                FavColumns.getVoter() + " TEXT)";
    }

    public static String dropTable() {
        return "DROP TABLE IF EXISTS " + TABLE_FAVORITES;
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
}
