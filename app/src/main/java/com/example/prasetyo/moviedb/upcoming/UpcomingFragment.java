package com.example.prasetyo.moviedb.upcoming;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.prasetyo.moviedb.R;
import com.example.prasetyo.moviedb.api.ApiEndPoint;
import com.example.prasetyo.moviedb.detail.DetailMovieActivity;
import com.example.prasetyo.moviedb.model.Movie;
import com.example.prasetyo.moviedb.movie.GridAdapter;
import com.example.prasetyo.moviedb.movie.MovieAdapter;
import com.example.prasetyo.moviedb.movie.MoviePresenter;
import com.example.prasetyo.moviedb.movie.MovieView;
import com.example.prasetyo.moviedb.search.SearchActivity;
import com.example.prasetyo.moviedb.util.RecyclerItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment implements MovieView {

    private MovieAdapter movieAdapter;
    private MoviePresenter presenter;
    private ArrayList<Movie> itemList = new ArrayList<Movie>();

    private LinearLayoutManager layoutManager;
    private ProgressDialog pDialog;

    Unbinder unbinder;

    @BindView(R.id.rootView)
    LinearLayout root;
    @BindView(R.id.listMovie)
    RecyclerView recyclerView;


    public UpcomingFragment() {
        // Required empty public constructor
    }

    private void init() {
        ApiEndPoint apiEndPoint = new ApiEndPoint();
        presenter = new MoviePresenter(this, apiEndPoint);
        recyclerView.setHasFixedSize(true);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        presenter.getMovieUpcoming();
        initRecyclerView();
    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        // untuk mengisi data dari JSON ke dalam adapter
        movieAdapter = new MovieAdapter(getActivity(), itemList);
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();
    }

    private void showGridView() {
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        // untuk mengisi data dari JSON ke dalam adapter
        GridAdapter gridAdapter = new GridAdapter(getActivity(), itemList);
        recyclerView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_grid:
                showGridView();
                break;

            case R.id.action_cardview:
                initRecyclerView();
                break;

            case R.id.action_search:
                Intent i = new Intent(getActivity(), SearchActivity.class);
                startActivity(i);
                break;

            case R.id.action_setting:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.data_movie, container, false);

        // bind view using butter knife
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        init();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        Movie m = itemList.get(position);

                        Intent i = new Intent(getActivity(), DetailMovieActivity.class);
                        i.putExtra(DetailMovieActivity.EXTRA_TITLE, m.getTitle());
                        i.putExtra(DetailMovieActivity.EXTRA_DATE, m.getDate());
                        i.putExtra(DetailMovieActivity.EXTRA_OVERVIEW, m.getOverview());
                        i.putExtra(DetailMovieActivity.EXTRA_POSTER, m.getPoster());
                        i.putExtra(DetailMovieActivity.EXTRA_BANNER, m.getBanner());
                        i.putExtra(DetailMovieActivity.EXTRA_RATING, m.getRating());
                        i.putExtra(DetailMovieActivity.EXTRA_VOTER, m.getVoter());
                        startActivity(i);
                    }
                })
        );

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // unbind the view to free some memory
        unbinder.unbind();
    }

    @Override
    public void showLoading() {
        if (!pDialog.isShowing())
            pDialog.show();

    }

    @Override
    public void hideLoading() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar snackbar = Snackbar
                .make(root, message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    @Override
    public void getMovie(ArrayList<Movie> data) {

        itemList.clear();
        itemList.addAll(data);
        movieAdapter.notifyDataSetChanged();
    }

}
