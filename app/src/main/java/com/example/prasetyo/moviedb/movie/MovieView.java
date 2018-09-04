package com.example.prasetyo.moviedb.movie;

import com.example.prasetyo.moviedb.model.Movie;

import java.util.ArrayList;

public interface MovieView {
    void showLoading();

    void hideLoading();

    void showSnackbar(String message);

    void getMovie(ArrayList<Movie> data);
}
