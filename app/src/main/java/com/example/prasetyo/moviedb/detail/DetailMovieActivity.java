package com.example.prasetyo.moviedb.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;
import com.example.prasetyo.moviedb.R;
import com.example.prasetyo.moviedb.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    ANImageView imgPoster;
    @BindView(R.id.img_banner)
    ANImageView imgBanner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String date = getIntent().getStringExtra(EXTRA_DATE);
        String overview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        String poster = getIntent().getStringExtra(EXTRA_POSTER);
        String rating = getIntent().getStringExtra(EXTRA_RATING);
        String voter = getIntent().getStringExtra(EXTRA_VOTER);
        String banner = getIntent().getStringExtra(EXTRA_BANNER);

        init();
        txTitle.setText(title);
        txRating.setText(rating);
        txVotes.setText(voter);
        txDate.setText(date);
        txOverview.setText(overview);
        imgPoster.setImageUrl(poster);
        imgBanner.setImageUrl(banner);
    }

    private void init() {

        ButterKnife.bind(this);

        setTitle(getString(R.string.moviedetail));

    }
}
