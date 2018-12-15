package com.example.prasetyo.moviedb.ui.popular;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
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

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
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
import com.example.prasetyo.moviedb.ui.setting.SettingActivity;
import com.example.prasetyo.moviedb.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PopularFragment extends Fragment implements MovieView, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    Unbinder unbinder;
    HashMap<String, String> url_images = new HashMap<String, String>();
    @BindView(R.id.rootView)
    NestedScrollView root;
    @BindView(R.id.listMovie)
    RecyclerView recyclerView;
    @BindView(R.id.slider)
    SliderLayout mDemoSlider;
    private MovieAdapter movieAdapter;
    private MoviePresenter presenter;
    private ArrayList<Movie> itemList = new ArrayList<Movie>();
    private LinearLayoutManager layoutManager;
    private ProgressDialog pDialog;
    private boolean gridOn = false;
    private Menu menu;


    public PopularFragment() {
        // Required empty public constructor
    }

    private void init() {
        ApiEndPoint apiEndPoint = new ApiEndPoint();
        presenter = new MoviePresenter(this, apiEndPoint);
        recyclerView.setHasFixedSize(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popular, container, false);

        // bind view using butter knife
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        init();

        if (savedInstanceState != null) {
            itemList = savedInstanceState.getParcelableArrayList("movies");
        } else {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            presenter.getPopularMovie();
        }

        initRecyclerView();

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
                if (!gridOn) {
                    menu.getItem(0).setIcon(R.drawable.ic_grid_on_white_24dp);
                    gridOn = true;
                    showGridView();
                } else {
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
                i = new Intent(getActivity(), SettingActivity.class);
                startActivity(i);
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


    private void showGridView() {
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        // untuk mengisi data dari JSON ke dalam adapter
        GridAdapter gridAdapter = new GridAdapter(getActivity(), itemList);
        recyclerView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
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


    private void initImageSlider(ArrayList<Movie> movies){
        for (int i = 0; i < movies.size(); i++) {
            if(i<3) {
                url_images.put(movies.get(i).getTitle(), movies.get(i).getBanner());
            }else continue;
        }

        for (String name1 : url_images.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name1)
                    .image(url_images.get(name1))
                    .setScaleType(BaseSliderView.ScaleType.FitCenterCrop)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name1);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

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
        initImageSlider(itemList);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movies", new ArrayList<>(movieAdapter.getMovies()));
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}