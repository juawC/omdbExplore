package com.juawapps.openmoviesdb.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juawapps.openmoviesdb.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.photo) ImageView mPhotoView;
    @BindView(R.id.app_bar) Toolbar mToolbarLayout;
    @BindView(R.id.article_title) TextView mTitleView;
    @BindView(R.id.plot) TextView mPlot;
    @BindView(R.id.imdb_score) TextView mImdbScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbarLayout);

    }
}
