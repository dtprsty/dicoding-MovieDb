package com.example.prasetyo.moviedb.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.example.prasetyo.moviedb.ui.widget.MovieRemoteViewsFactory;

public class MovieWidgetService extends RemoteViewsService {

    @Override public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MovieRemoteViewsFactory(this);
    }
}
