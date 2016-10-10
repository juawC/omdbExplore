package com.juawapps.openmoviesdb.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.juawapps.openmoviesdb.Presenters.MovieDetailPresenter;
import com.juawapps.openmoviesdb.Presenters.MovieDetailView;
import com.juawapps.openmoviesdb.R;
import com.juawapps.openmoviesdb.data.MovieItemResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends MvpActivity<MovieDetailView, MovieDetailPresenter>
implements MovieDetailView {

    @BindView(R.id.photo) NetworkImageView mPhotoView;
    @BindView(R.id.app_bar) Toolbar mToolbarLayout;
    @BindView(R.id.movie_title) TextView mTitleView;
    @BindView(R.id.plot) TextView mPlot;
    @BindView(R.id.imdb_score) TextView mImdbScore;

    public static final String EXTRA_MOVIE_ID = "extra_movie_id";
    public static final String EXTRA_MOVIE_TITLE = "extra_movie_title";

    private static final String TAG = "DetailActivity";

    String mTitle;
    String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            Log.e(TAG, "Error, bundle was null!");
            finish();
            return;
        }

        mTitle = bundle.getString(EXTRA_MOVIE_TITLE, "");
        mId = bundle.getString(EXTRA_MOVIE_ID, "");

        //Toolbar setup
        mTitleView.setText(mTitle);
        mToolbarLayout.setTitle(mTitle);
        mToolbarLayout.setNavigationIcon(R.drawable.ic_arrow_back);

        setSupportActionBar(mToolbarLayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        getPresenter().getMovieDetails(mId);

    }

    @NonNull
    @Override
    public MovieDetailPresenter createPresenter() {
        return new MovieDetailPresenter();
    }

    @Override
    public void setMovieDetails(MovieItemResponse movieItem) {

        mPhotoView.setImageUrl(movieItem.getPoster(), ImageLoaderHelper.getInstance(this)
                .getImageLoader());

        mImdbScore.setText(movieItem.getImdbRating());
        mPlot.setText(movieItem.getPlot());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
