package com.example.prasetyo.moviedb.ui.detail;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.prasetyo.moviedb.R;
import com.example.prasetyo.moviedb.database.FavMovieHelper;
import com.example.prasetyo.moviedb.model.Movie;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {

    public static String EXTRA_MOVIE = "extra_movie";

    @BindView(R.id.txTitle)
    TextView txTitle;
    @BindView(R.id.txt_ratings)
    TextView txRating;
    @BindView(R.id.txt_votes)
    TextView txVotes;
    @BindView(R.id.txDate)
    TextView txDate;
    @BindView(R.id.txOverview)
    TextView txOverview;
    @BindView(R.id.imgPoster)
    ImageView imgPoster;
    @BindView(R.id.img_banner)
    ImageView imgBanner;
    @BindView(R.id.rootView)
    CoordinatorLayout root;

    private boolean isFavorite = false;
    private Menu menu;
    private Movie movie;
    private FavMovieHelper favMovieHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        init();

    }

    private void init() {

        ButterKnife.bind(this);
        Uri uri = getIntent().getData();

        favMovieHelper = new FavMovieHelper(this);
        favMovieHelper.open();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                movie = new Movie(cursor);
                cursor.close();
            } else {
                onBackPressed();
                finish();
            }
        } else {
            movie = new Gson().fromJson(getIntent().getStringExtra(EXTRA_MOVIE), Movie.class);
        }

        favoriteState();

        loadMovieDetail(movie);

    }

    private void loadMovieDetail(Movie movie) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(movie.getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        txTitle.setText(movie.getTitle());
        txRating.setText(movie.getRating());
        txVotes.setText(movie.getVoter());
        txDate.setText(movie.getDate());
        txOverview.setText(movie.getOverview());
        Glide.with(this)
                .load(movie.getBanner())
                .into(imgBanner);
        Glide.with(this)
                .load(movie.getPoster())
                .into(imgPoster);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail_menu, menu);
        this.menu = menu;
        setFavorite();
        return super.onCreateOptionsMenu(menu);
    }

    private void favoriteState() {
        int favCount = favMovieHelper.queryByIdProvider(
                String.valueOf(movie.getId())).getCount();

        if (favCount > 0) {
            isFavorite = true;
        } else {
            isFavorite = false;
        }
    }

    private void addToFav() {

        int favCount = favMovieHelper.queryByIdProvider(
                String.valueOf(movie.getId())).getCount();
        if (favCount > 0) {
            favMovieHelper.updateProvider(String.valueOf(movie.getId()), movie);
        } else {
            favMovieHelper.insert(movie);
        }
        Snackbar snackbar = Snackbar
                .make(root, "Added To Favorite", Snackbar.LENGTH_LONG);
        snackbar.show();
        menu.getItem(0).setIcon(R.drawable.ic_added_to_fav);
        isFavorite = true;

    }

    private void removeFromFav() {
        favMovieHelper.delete(String.valueOf(movie.getId()));
        Snackbar snackbar = Snackbar
                .make(root, "Removed from Favorite", Snackbar.LENGTH_LONG);
        snackbar.show();
        menu.getItem(0).setIcon(R.drawable.ic_add_to_fav);
        isFavorite = false;
    }

    private void setFavorite() {
        if (isFavorite) {
            menu.getItem(0).setIcon(R.drawable.ic_added_to_fav);
        } else {
            menu.getItem(0).setIcon(R.drawable.ic_add_to_fav);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.add_to_favorite:
                if (!isFavorite) {
                    addToFav();
                } else {
                    removeFromFav();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
