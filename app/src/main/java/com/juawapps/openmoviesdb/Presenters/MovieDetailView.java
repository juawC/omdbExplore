package com.juawapps.openmoviesdb.Presenters;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.juawapps.openmoviesdb.data.MovieItemResponse;

/**
 *  Movie detail view interface.
 *  <p>
 * Created by joaocevada on 09/10/16.
 */

public interface MovieDetailView extends MvpView {

    void setMovieDetails(MovieItemResponse movieItem);
}
