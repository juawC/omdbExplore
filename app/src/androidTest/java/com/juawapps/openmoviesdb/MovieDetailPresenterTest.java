package com.juawapps.openmoviesdb;

import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.juawapps.openmoviesdb.Presenters.MovieDetailPresenter;
import com.juawapps.openmoviesdb.Presenters.MovieDetailView;
import com.juawapps.openmoviesdb.Presenters.MovieSearchPresenter;
import com.juawapps.openmoviesdb.Presenters.MovieSearchView;
import com.juawapps.openmoviesdb.data.MovieItemResponse;
import com.juawapps.openmoviesdb.data.MovieListItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class MovieDetailPresenterTest {

    Context mContext;

    @Before
    public void setup() {
        mContext =  InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void createPresenter() throws Exception {

        MovieDetailPresenter movieDetailPresenter = new MovieDetailPresenter();

        assertNotNull("MovieDetailPresenter not instantiated correctly", movieDetailPresenter);
    }

    @Test
    public void callMovieDetails() throws Exception {
        String validId = "tt0372784";

        MovieDetailsViewTest movieDetailsViewTest =
                new MovieDetailPresenterTest.MovieDetailsViewTest();
        MovieDetailPresenter movieDetailPresenter = new MovieDetailPresenter();
        movieDetailPresenter.attachView(movieDetailsViewTest);

        movieDetailPresenter.getMovieDetails(validId, getTestSchedulers());

        assertNotNull("Error setting movie details", movieDetailsViewTest.mMovieItem);

    }

    static public  <T> Observable.Transformer<T, T> getTestSchedulers() {
        return observable -> observable.subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate());
    }

    public class MovieDetailsViewTest implements MovieDetailView {
        public MovieItemResponse mMovieItem;

        @Override
        public void setMovieDetails(MovieItemResponse movieItem) {
            mMovieItem = movieItem;
        }
    }

}
