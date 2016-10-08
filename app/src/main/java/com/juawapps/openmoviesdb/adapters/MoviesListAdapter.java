package com.juawapps.openmoviesdb.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.juawapps.openmoviesdb.R;


/**
 * Recycler View adapter for the {@link com.juawapps.openmoviesdb.ui.MainActivity} activity.
 * <p>
 * Created by joaocevada on 08/10/16.
 */
public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>  {


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_movie, parent, false));
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        protected ImageView mImageView;
        protected TextView mTitleView;
        protected TextView mYearView;

        public MovieViewHolder(View view) {
            super(view);
            this.mImageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.mTitleView = (TextView) view.findViewById(R.id.title);
            this.mYearView = (TextView) view.findViewById(R.id.year);
        }
    }
}
