package com.juawapps.openmoviesdb.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.juawapps.openmoviesdb.Presenters.MovieSearchPresenter;
import com.juawapps.openmoviesdb.Presenters.MovieSearchView;
import com.juawapps.openmoviesdb.R;
import com.juawapps.openmoviesdb.Utils;
import com.juawapps.openmoviesdb.adapters.MoviesListAdapter;
import com.juawapps.openmoviesdb.data.MovieListItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpActivity<MovieSearchView, MovieSearchPresenter>
        implements MovieSearchView,  SearchView.OnQueryTextListener,
        SearchView.OnSuggestionListener, MoviesListAdapter.OnListInteractionListener{

    private static final String ADAPTER_STATE_KEY = "adapter_state";
    @BindView(R.id.empty_view) TextView mEmptyView;
    @BindView(R.id.progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.error_view) TextView mErrorView;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.search) SearchView mSearch;
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    private MoviesListAdapter moviesListAdapter;
    private SimpleCursorAdapter mAutoCompleteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.setDebug(false);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        //Setting up RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new VerticalItemDecoration(this,
                R.dimen.list_separator_height));
        moviesListAdapter = new MoviesListAdapter(getApplicationContext(), this);
        mRecyclerView.setAdapter(moviesListAdapter);

        //Restore movies adapter
        if(savedInstanceState != null) {
            ArrayList<MovieListItem> movies = savedInstanceState.getParcelableArrayList(
                    ADAPTER_STATE_KEY);
            moviesListAdapter.setMovies(movies);
        }

        //Setting up SearchView
        mSearch.setIconifiedByDefault(false);
        mSearch.setOnQueryTextListener(this);
        mSearch.setOnSuggestionListener(this);
        mAutoCompleteAdapter = new SimpleCursorAdapter(
                this,R.layout.list_search_sugestion,
                MovieSearchPresenter.getAutoCompleteCursor(),
                new String[]{MovieSearchPresenter.AUTO_COMPLETE_CURSOR_COLUMNS[1]},
                new int[] {android.R.id.text1}, 0);
        mSearch.setSuggestionsAdapter(mAutoCompleteAdapter);

        //Show an empty starting state
        if (moviesListAdapter.getItemCount() == 0) showEmptyState();
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        //Save movies adapter
        super.onSaveInstanceState(state);
        state.putParcelableArrayList(ADAPTER_STATE_KEY,
                (ArrayList<MovieListItem>)moviesListAdapter.getItems());
    }

    @NonNull
    @Override
    public MovieSearchPresenter createPresenter() {
        return new MovieSearchPresenter();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setErrorMessage(Throwable e) {
        showErrorView(e.getMessage());
    }


    @Override
    public void setMovieList(List<MovieListItem> data) {

        if(data.size() == 0) {
            showEmptyState();
        } else {
            showContentView();
        }
        moviesListAdapter.setMovies(data);
        moviesListAdapter.notifyDataSetChanged();
    }

    @Override
    public void setAutoComplete(Cursor moviesNames) {
        mAutoCompleteAdapter.changeCursor(moviesNames);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {

        if (query.length() > 2) {
            hideSoftKeyboard();
            startQueryNewList(query);

        } else {
            //resets the movies list
            setMovieList(new ArrayList<>());
            //TODO add error msg
        }
        return true;

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length() > 2 ) {
            getPresenter().updateAutoComplete(newText);
            return false;
        } else if (newText.length() == 0) { //If the text is completely cleared

            //resets everything
            setAutoComplete(MovieSearchPresenter.getAutoCompleteCursor());
            setMovieList(new ArrayList<>());
            return true;
        }else {
            //resets the autocomplete
            setAutoComplete(MovieSearchPresenter.getAutoCompleteCursor());
            return true;
        }
    }

    @Override
    public boolean onSuggestionClick(int position) {

        hideSoftKeyboard();

        Cursor cursor = (Cursor) mAutoCompleteAdapter.getItem(position);
        String title = cursor.getString(MovieSearchPresenter.AUTO_COMPLETE_CURSOR_TITLE);
        mSearch.setQuery(title, false);

        startQueryNewList(title);
        return true;
    }

    @Override
    public void onMovieClick(MovieListItem item) {

        Intent intent = new Intent(this, DetailActivity.class);

        intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, item.getImdbID());
        intent.putExtra(DetailActivity.EXTRA_MOVIE_TITLE, item.getTitle());

        startActivity(intent);
    }

    public void startQueryNewList(String query) {
        showLoadingView();
        getPresenter().searchMoviesList(query);
    }

    public void showEmptyState() {

        if (Utils.isConnected(this) ) {
            showEmptyView();
        } else {
            showErrorView(getString(R.string.no_connectivity));
        }
    }

    public void showContentView() {

        mRecyclerView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.INVISIBLE);
        mEmptyView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    public void showLoadingView() {

        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorView.setVisibility(View.INVISIBLE);
        mEmptyView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void showEmptyView() {

        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorView.setVisibility(View.INVISIBLE);
        mEmptyView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    public void showErrorView(String errorMessage) {

        mErrorView.setText(errorMessage);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorView.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);

    }

    private void hideSoftKeyboard() {
        mSearch.clearFocus();

        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    //Not used
    @Override
    public boolean onSuggestionSelect(int position) {
        return false;
    }

}
