package com.example.prasetyo.moviedb.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class FavProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int MOVIE      = 1;
    private static final int MOVIE_ID   = 2;

    private FavMovieHelper favMovieHelper;

    static {
        sUriMatcher.addURI(DatabaseConstruct.CONTENT_AUTHORITY, DatabaseConstruct.TABLE_FAVORITES, MOVIE);
        sUriMatcher.addURI(DatabaseConstruct.CONTENT_AUTHORITY, DatabaseConstruct.TABLE_FAVORITES + "/#", MOVIE_ID);
    }


    @Override public boolean onCreate() {
        favMovieHelper = new FavMovieHelper(getContext());
        favMovieHelper.open();
        return true;
    }

    @Nullable @Override public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = (sUriMatcher.match(uri) == MOVIE)
                ? favMovieHelper.queryProvider()
                : favMovieHelper.queryByIdProvider(uri.getLastPathSegment());
        if (cursor != null) cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable @Override public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable @Override public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added = (sUriMatcher.match(uri) == MOVIE)
                ? favMovieHelper.insertProvider(values)
                : 0;
        if (added > 0) getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(DatabaseConstruct.CONTENT_URI + "/" + added);
    }

    @Override public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted = (sUriMatcher.match(uri) == MOVIE_ID)
                ? favMovieHelper.deleteProvider(uri.getLastPathSegment())
                : 0;
        if (deleted > 0) getContext().getContentResolver().notifyChange(uri, null);
        return deleted;
    }

    @Override public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updated = (sUriMatcher.match(uri) == MOVIE_ID)
                ? favMovieHelper.updateProvider(uri.getLastPathSegment(), values)
                : 0;
        if (updated > 0) getContext().getContentResolver().notifyChange(uri, null);
        return updated;
    }
}
