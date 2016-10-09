package com.juawapps.openmoviesdb.Presenters;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.juawapps.openmoviesdb.data.MovieItemResponse;
import com.juawapps.openmoviesdb.data.OmdbHelper;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Presenter to get movies details for the {@link com.juawapps.openmoviesdb.ui.DetailActivity}.
 * <p>
 * Created by joaocevada on 09/10/16.
 */

public class MovieDetailPresenter extends MvpBasePresenter<MovieDetailView> {
    private Subscription mDetailSubscription;
    private OmdbHelper mOmdbHelper;

    private static final String TAG = "MovieDetailPresenter";

    public MovieDetailPresenter() {

        mOmdbHelper = new OmdbHelper();

    }

    public void getMovieDetails(String id) {


        if (mDetailSubscription != null && !mDetailSubscription.isUnsubscribed()) {
            mDetailSubscription.unsubscribe();
        }

        mDetailSubscription = mOmdbHelper.getMovieDetailsById(id)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(movieDetailSubscriber());

    }


    private Subscriber<MovieItemResponse> movieDetailSubscriber() {
        return new Subscriber<MovieItemResponse>() {

            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(MovieItemResponse movieItemResponse) {

                if (isViewAttached()) {
                    //Prevents NPE's
                    getView().setMovieDetails(movieItemResponse);
                }
            }
        };
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);

        Log.d(TAG, "Detaching presenter from view");

        if (mDetailSubscription != null && !mDetailSubscription.isUnsubscribed()) {
            mDetailSubscription.unsubscribe();
        }
    }
}
