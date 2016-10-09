package com.juawapps.openmoviesdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.juawapps.openmoviesdb.R;
import com.juawapps.openmoviesdb.data.MovieListItem;
import com.juawapps.openmoviesdb.ui.ImageLoaderHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Recycler View adapter for the {@link com.juawapps.openmoviesdb.ui.MainActivity} activity.
 * <p>
 * Created by joaocevada on 08/10/16.
 */
public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>  {


    private static final String TAG = "MoviesListAdapter";

    private List<MovieListItem> mMoviesList;
    private final Context mContext;
    private final OnListInteractionListener mListener;

    public MoviesListAdapter(Context context, OnListInteractionListener listener) {
        this.mMoviesList = new ArrayList<>();
        this.mContext = context.getApplicationContext();
        this.mListener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_movie, parent, false));
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        MovieListItem movieListItem = mMoviesList.get(position);

        String imageUrl = movieListItem.getPoster();

            holder.mImageView.setErrorImageResId(R.drawable.ic_place_holder);
            holder.mImageView.setImageUrl(imageUrl, ImageLoaderHelper.getInstance(mContext)
                    .getImageLoader());

        holder.mYearView.setText(movieListItem.getYear());
        holder.mTitleView.setText(movieListItem.getTitle());

        int movieYear = 0;
        try {

            movieYear = Integer.valueOf(movieListItem.getYear());
        } catch (NumberFormatException e) {

            Log.e(TAG, "Error parsing year on movie: " + movieListItem.getImdbID());
        }

        Calendar currentDate = Calendar.getInstance();
        if (currentDate.get(Calendar.YEAR) == movieYear) {
            holder.mYearView.setTextColor(
                    mContext.getResources().getColor(R.color.colorAccent));
        } else {
            holder.mYearView.setTextColor(
                    mContext.getResources().getColor(R.color.textColorSecondary));
        }

        holder.mView.setOnClickListener(view ->
        { if(mListener!= null) mListener.onMovieClick(movieListItem);});

    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }

    public void setMovies(List<MovieListItem> movies) {
        this.mMoviesList = movies;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        protected NetworkImageView mImageView;
        protected TextView mTitleView;
        protected TextView mYearView;
        protected ViewGroup mView;

        public MovieViewHolder(View view) {
            super(view);
            this.mImageView = (NetworkImageView) view.findViewById(R.id.thumbnail);
            this.mTitleView = (TextView) view.findViewById(R.id.title);
            this.mYearView = (TextView) view.findViewById(R.id.year);
            this.mView = (ViewGroup) view.findViewById(R.id.list_view_layout);
        }
    }


    public interface OnListInteractionListener {

        void onMovieClick(MovieListItem item);
    }
}
