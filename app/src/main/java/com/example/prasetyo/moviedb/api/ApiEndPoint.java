package com.example.prasetyo.moviedb.api;

import com.example.prasetyo.moviedb.BuildConfig;

public class ApiEndPoint {

    public ApiEndPoint() {

    }

    public String urlNowPlaying() {
        return BuildConfig.BASE_URL + "3/movie/now_playing?api_key=" + BuildConfig.TSDB_API_KEY + "&language=en-US";
    }

    public String urlPopular() {
        return BuildConfig.BASE_URL + "3/movie/popular?api_key=" + BuildConfig.TSDB_API_KEY + "&language=en-US";
    }

    public String urlUpcoming() {
        return BuildConfig.BASE_URL + "3/movie/upcoming?api_key=" + BuildConfig.TSDB_API_KEY + "&language=en-US";
    }

    public String urlSearch(String title) {
        return BuildConfig.BASE_URL + "3/search/movie?api_key=" + BuildConfig.TSDB_API_KEY + "&language=en-US&query=" + title;
    }
}
