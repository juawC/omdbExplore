package com.juawapps.openmoviesdb;

import android.content.Context;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.juawapps.openmoviesdb.Presenters.MovieSearchPresenter;
import com.juawapps.openmoviesdb.Presenters.MovieSearchView;
import com.juawapps.openmoviesdb.data.MovieItemResponse;
import com.juawapps.openmoviesdb.data.MovieListItem;
import com.juawapps.openmoviesdb.data.MovieListResponse;
import com.juawapps.openmoviesdb.data.OmdbClient;
import com.juawapps.openmoviesdb.data.OmdbHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


@RunWith(AndroidJUnit4.class)
public class MovieSearchPresenterTest {

    Context mContext;

    @Before
    public void setup() {
        mContext =  InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void createPresenter() throws Exception {

        MovieSearchPresenter movieSearchPresenter = new MovieSearchPresenter();

        assertNotNull("MovieSearchPresenter not instantiated correctly", movieSearchPresenter);
    }

    @Test
    public void callSearchMoviesList() throws Exception {
        String mediumString = "Star";

        MovieSearchViewTest movieSearchViewTest = new MovieSearchViewTest();
        MovieSearchPresenter movieSearchPresenter = new MovieSearchPresenter();
        movieSearchPresenter.attachView(movieSearchViewTest);

        movieSearchPresenter.searchMoviesList(mediumString, getTestSchedulers());

        assertNotEquals("Error setting movie list adapter", 0,
                movieSearchViewTest.mMovieList.size());

    }

    @Test
    public void callUpdateAutoComplete() throws Exception {
        String mediumString = "Star";

        MovieSearchViewTest movieSearchViewTest = new MovieSearchViewTest();
        MovieSearchPresenter movieSearchPresenter = new MovieSearchPresenter();
        movieSearchPresenter.attachView(movieSearchViewTest);

        movieSearchPresenter.updateAutoComplete(mediumString, getTestSchedulers());

        assertNotEquals("Error setting movie list auto complete", 0,
                movieSearchViewTest.mMoviesNames.getCount());

    }


    static public  <T> Observable.Transformer<T, T> getTestSchedulers() {
        return observable -> observable.subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate());
    }

    public class MovieSearchViewTest implements MovieSearchView {

        public List<MovieListItem> mMovieList;
        public Cursor mMoviesNames;
        public Throwable mThrowable;


        @Override
        public void setMovieList(List<MovieListItem> movieList) {
            this.mMovieList = movieList;
        }

        @Override
        public void setAutoComplete(Cursor moviesNames) {
            this.mMoviesNames = moviesNames;
        }

        @Override
        public void setErrorMessage(Throwable e) {
            this.mThrowable = e;
        }
    }

}
