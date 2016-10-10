package com.juawapps.openmoviesdb.Presenters;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.juawapps.openmoviesdb.Utils;
import com.juawapps.openmoviesdb.data.MovieListItem;
import com.juawapps.openmoviesdb.data.MovieListResponse;
import com.juawapps.openmoviesdb.data.OmdbHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 *  Presenter to get a movie list for the {@link com.juawapps.openmoviesdb.ui.MainActivity}.
 *  <p>
 * Created by joaocevada on 08/10/16.
 */

public class MovieSearchPresenter extends MvpBasePresenter<MovieSearchView> {

    private static final String TAG = "MovieSearchPresenter";
    public static final String [] AUTO_COMPLETE_CURSOR_COLUMNS = new String[] {"_id", "title"};
    public static final int AUTO_COMPLETE_CURSOR_ID = 0;
    public static final int AUTO_COMPLETE_CURSOR_TITLE = 1;

    private static final int AUTO_COMPLETE_WAIT_MILISECONDS = 400;

    private Subscription mAutoCompleteSubscription;
    private Subscription mSearchSubscription;
    private OmdbHelper mOmdbHelper;

    public MovieSearchPresenter() {

        mOmdbHelper = new OmdbHelper();

    }

    public void updateAutoComplete(String title) {
        updateAutoComplete(title, Utils.getSchedulers());
    }

    public void updateAutoComplete(String title,
                                   Observable.Transformer<MatrixCursor, MatrixCursor> schedulers) {

        try {
            if (mAutoCompleteSubscription != null && !mAutoCompleteSubscription.isUnsubscribed()) {
                mAutoCompleteSubscription.unsubscribe();
            }

            title+="*"; //Wildcard to allow queries with partial names
            String encodedTitle= URLEncoder.encode(title, "UTF-8");

            mAutoCompleteSubscription = mOmdbHelper.getMoviesByTitle(encodedTitle)
                    .debounce(AUTO_COMPLETE_WAIT_MILISECONDS, TimeUnit.MILLISECONDS)
                    .map(movieResponse -> {
                        MatrixCursor cursor = getAutoCompleteCursor();

                        int i = 0;
                        for (MovieListItem movie: movieResponse.getResults()) {
                            cursor.addRow(new String[] {String.valueOf(i++), movie.getTitle()});
                        }
                        return cursor;
                    })
                    .compose(schedulers)
                    .subscribe(autoCompleteSubscriber());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e(TAG, "Error encoding movie title screen");
        }
    }

    public void searchMoviesList(String title) {
        searchMoviesList(title, Utils.getSchedulers());
    }
    public void searchMoviesList(String title, Observable.Transformer<MovieListResponse,
            MovieListResponse> schedulers) {

        try {
            if (mSearchSubscription != null && !mSearchSubscription.isUnsubscribed()) {
                mSearchSubscription.unsubscribe();
            }

            title+="*"; //Wildcard to allow queries with partial names
            String encodedTitle= URLEncoder.encode(title, "UTF-8");
            mSearchSubscription = mOmdbHelper.getMoviesByTitle(encodedTitle)
                    .compose(schedulers)
                    .subscribe(movieListSubscriber());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e(TAG, "Error encoding movie title screen");
        }
    }

    private Subscriber<Cursor> autoCompleteSubscriber() {
        return new Subscriber<Cursor>() {

            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (isViewAttached()) {
                    getView().setAutoComplete(getAutoCompleteCursor());
                }
            }

            @Override
            public void onNext(Cursor moviesNames) {

                if (isViewAttached()) {
                    getView().setAutoComplete(moviesNames);
                }
            }
        };
    }

    private Subscriber<MovieListResponse> movieListSubscriber() {
        return new Subscriber<MovieListResponse>() {

            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (isViewAttached()) {
                    getView().setErrorMessage(e);
                }
            }

            @Override
            public void onNext(MovieListResponse movieListResponse) {
                List<MovieListItem> movies = movieListResponse.getResults();

                if (isViewAttached()) {
                    //Prevents NPE's
                    getView().setMovieList(movies != null ? movies : new ArrayList<>());
                }
            }
        };
    }

    public static MatrixCursor getAutoCompleteCursor() {
        return new MatrixCursor(AUTO_COMPLETE_CURSOR_COLUMNS);
    }


    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);

        Log.d(TAG, "Detaching presenter from view");

        if (mSearchSubscription != null && !mSearchSubscription.isUnsubscribed()) {
            mSearchSubscription.unsubscribe();
        }

        if (mAutoCompleteSubscription != null && !mAutoCompleteSubscription.isUnsubscribed()) {
            mAutoCompleteSubscription.unsubscribe();
        }
    }

}
