package com.example.prasetyo.moviedb.ui.upcoming;


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
import com.example.prasetyo.moviedb.model.Movie;
import com.example.prasetyo.moviedb.movie.GridAdapter;
import com.example.prasetyo.moviedb.movie.MovieAdapter;
import com.example.prasetyo.moviedb.movie.MoviePresenter;
import com.example.prasetyo.moviedb.movie.MovieView;
import com.example.prasetyo.moviedb.ui.detail.DetailMovieActivity;
import com.example.prasetyo.moviedb.ui.favorites.FavoritesActivity;
import com.example.prasetyo.moviedb.ui.search.SearchActivity;
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

    private boolean gridOn = false;
    private Menu menu;

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
        this.menu = menu;
        setGrid();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {

            case R.id.action_favorite:
                i = new Intent(getActivity(), FavoritesActivity.class);
                startActivity(i);
                break;

            case R.id.action_grid:
                if(!gridOn) {
                    menu.getItem(0).setIcon(R.drawable.ic_grid_on_white_24dp);
                    gridOn = true;
                    showGridView();
                }else{
                    menu.getItem(0).setIcon(R.drawable.ic_grid_off_white_24dp);
                    gridOn = false;
                    initRecyclerView();
                }
                break;

            case R.id.action_search:
                i = new Intent(getActivity(), SearchActivity.class);
                startActivity(i);
                break;

            case R.id.action_setting:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setGrid() {
        if (!gridOn) {
            menu.getItem(0).setIcon(R.drawable.ic_grid_off_white_24dp);
            initRecyclerView();
        } else {
            menu.getItem(0).setIcon(R.drawable.ic_grid_on_white_24dp);
            showGridView();
        }
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

                        i.putExtra(DetailMovieActivity.EXTRA_MOVIE, m.parcelMovie());
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
