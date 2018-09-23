package com.example.prasetyo.moviedb.ui.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.prasetyo.moviedb.R;
import com.example.prasetyo.moviedb.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {

    public static String EXTRA_TITLE = "extra_title";
    public static String EXTRA_DATE = "extra_date";
    public static String EXTRA_OVERVIEW = "extra_overview";
    public static String EXTRA_POSTER = "extra_poster";
    public static String EXTRA_BANNER = "extra_banner";
    public static String EXTRA_RATING = "extra_rating";
    public static String EXTRA_VOTER = "extra_voter";

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

    private boolean isFavorite = false;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        init();

    }

    private void init() {

        ButterKnife.bind(this);
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String date = getIntent().getStringExtra(EXTRA_DATE);
        String overview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        String poster = getIntent().getStringExtra(EXTRA_POSTER);
        String rating = getIntent().getStringExtra(EXTRA_RATING);
        String voter = getIntent().getStringExtra(EXTRA_VOTER);
        String banner = getIntent().getStringExtra(EXTRA_BANNER);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        txTitle.setText(title);
        txRating.setText(rating);
        txVotes.setText(voter);
        txDate.setText(date);
        txOverview.setText(overview);
        Glide.with(this)
                .load(banner)
                .into(imgBanner);
        Glide.with(this)
                .load(poster)
                .into(imgPoster);

    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail_menu, menu);
        this.menu = menu;
        setFavorite();
        return super.onCreateOptionsMenu(menu);
    }

    private void setFavorite(){
        if (isFavorite) {
            menu.getItem(0).setIcon(R.drawable.ic_added_to_fav);
        }else {
            menu.getItem(0).setIcon(R.drawable.ic_add_to_fav);
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
