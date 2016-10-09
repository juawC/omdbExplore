package com.juawapps.openmoviesdb.Presenters;

import android.database.Cursor;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.juawapps.openmoviesdb.data.MovieListItem;

import java.util.List;

/**
 *  Movie search view interface.
 *  <p>
 * Created by joaocevada on 08/10/16.
 */

public interface MovieSearchView extends MvpView {

    void setMovieList(List<MovieListItem> movieList);
    void setAutoComplete(Cursor moviesNames);
    void setErrorMessage(Throwable e);
}
