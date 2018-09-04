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

    @BindView(R.id.txTitle)
    TextView txTitle;
    @BindView(R.id.txDate)
    TextView txDate;
    @BindView(R.id.txOverview)
    TextView txOverview;
    @BindView(R.id.imgPoster)
    ANImageView imgPoster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String date = getIntent().getStringExtra(EXTRA_DATE);
        String overview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        String poster = getIntent().getStringExtra(EXTRA_POSTER);

        init();
        txTitle.setText(title);
        txDate.setText(date);
        txOverview.setText(overview);
        imgPoster.setImageUrl(poster);
    }

    @OnClick(R.id.btnBack)
    public void btnBackClick(View view) {
        Intent i = new Intent(DetailMovieActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void init() {

        ButterKnife.bind(this);

        setTitle(getString(R.string.moviedetail));

    }
}
