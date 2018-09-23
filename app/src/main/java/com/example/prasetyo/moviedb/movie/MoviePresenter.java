package com.example.prasetyo.moviedb.movie;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.prasetyo.moviedb.api.ApiEndPoint;
import com.example.prasetyo.moviedb.model.Movie;
import com.example.prasetyo.moviedb.ui.search.SearchActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MoviePresenter {

    private MovieView view;
    private ApiEndPoint apiEndPoint;
    private ArrayList<Movie> data = new ArrayList<Movie>();


    private static final String TAG = SearchActivity.class.getSimpleName();

    public MoviePresenter(MovieView view, ApiEndPoint apiEndPoint) {
        this.apiEndPoint = apiEndPoint;
        this.view = view;
    }

    public void getMovie(String title) {

        data.clear();
        view.showLoading();
        AndroidNetworking.get(apiEndPoint.urlSearch(title))
                .setTag(this)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.d(TAG, "RESULT " + response.toString());
                        try {
                            JSONArray array = response.getJSONArray("results");
                            // Loop through the array elements
                            for (int i = 0; i < array.length(); i++) {
                                // Get current json object
                                JSONObject jsonObject = array.getJSONObject(i);

                                Movie movie = new Movie();

                                movie.setTitle(jsonObject.getString("original_title"));
                                movie.setId(jsonObject.getString("id"));
                                movie.setOverview(jsonObject.getString("overview"));
                                movie.setDate(parseDate(jsonObject.getString("release_date")));
                                movie.setRating(jsonObject.getString("vote_average"));
                                movie.setVoter(jsonObject.getString("vote_count"));
                                movie.setPoster("http://image.tmdb.org/t/p/w185" + jsonObject.getString("poster_path"));
                                movie.setBanner("http://image.tmdb.org/t/p/original" + jsonObject.getString("backdrop_path"));

                                data.add(movie);

                                view.getMovie(data);
                                view.hideLoading();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (data.isEmpty()) {
                            view.showSnackbar("Movie Not Found");
                        }
                        view.getMovie(data);
                        view.hideLoading();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, "ERROR : " + error.getMessage());
                        view.hideLoading();
                    }
                });


    }

    public void getMovieNowPlaying() {

        data.clear();
        view.showLoading();
        AndroidNetworking.get(apiEndPoint.urlNowPlaying())
                .setTag(this)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.d(TAG, "RESULT " + response.toString());
                        try {
                            JSONArray array = response.getJSONArray("results");
                            // Loop through the array elements
                            for (int i = 0; i < array.length(); i++) {
                                // Get current json object
                                JSONObject jsonObject = array.getJSONObject(i);

                                Movie movie = new Movie();

                                movie.setId(jsonObject.getString("id"));
                                movie.setTitle(jsonObject.getString("original_title"));
                                movie.setOverview(jsonObject.getString("overview"));
                                movie.setDate(parseDate(jsonObject.getString("release_date")));
                                movie.setRating(jsonObject.getString("vote_average"));
                                movie.setVoter(jsonObject.getString("vote_count"));
                                movie.setPoster("http://image.tmdb.org/t/p/w185" + jsonObject.getString("poster_path"));
                                movie.setBanner("http://image.tmdb.org/t/p/original" + jsonObject.getString("backdrop_path"));

                                data.add(movie);

                                view.getMovie(data);
                                view.hideLoading();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (data.isEmpty()) {
                            view.showSnackbar("Movie Not Found");
                        }
                        view.getMovie(data);
                        view.hideLoading();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, "ERROR : " + error.getMessage());
                        view.hideLoading();
                    }
                });


    }

    public void getMovieUpcoming() {

        data.clear();
        view.showLoading();
        AndroidNetworking.get(apiEndPoint.urlUpcoming())
                .setTag(this)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.d(TAG, "RESULT " + response.toString());
                        try {
                            JSONArray array = response.getJSONArray("results");
                            // Loop through the array elements
                            for (int i = 0; i < array.length(); i++) {
                                // Get current json object
                                JSONObject jsonObject = array.getJSONObject(i);

                                Movie movie = new Movie();

                                movie.setId(jsonObject.getString("id"));
                                movie.setTitle(jsonObject.getString("original_title"));
                                movie.setOverview(jsonObject.getString("overview"));
                                movie.setDate(parseDate(jsonObject.getString("release_date")));
                                movie.setRating(jsonObject.getString("vote_average"));
                                movie.setVoter(jsonObject.getString("vote_count"));
                                movie.setPoster("http://image.tmdb.org/t/p/w185" + jsonObject.getString("poster_path"));
                                movie.setBanner("http://image.tmdb.org/t/p/original" + jsonObject.getString("backdrop_path"));

                                data.add(movie);

                                view.getMovie(data);
                                view.hideLoading();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (data.isEmpty()) {
                            view.showSnackbar("Movie Not Found");
                        }
                        view.getMovie(data);
                        view.hideLoading();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, "ERROR : " + error.getMessage());
                        view.hideLoading();
                    }
                });


    }

    private String parseDate(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "E, dd - MMM - yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

}
