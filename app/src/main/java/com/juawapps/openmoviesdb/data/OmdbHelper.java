package com.juawapps.openmoviesdb.data;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Helper class to get Observers from the omdb api.
 * <p>
 * Created by joaocevada on 09/10/16.
 */

public class OmdbHelper {

    private final OmdbInterface mOmdbInterface;

    public OmdbHelper() {
        OmdbClient omdbClient = OmdbClient.getInstance();
        Retrofit retrofit = omdbClient.getRetrofit();
        mOmdbInterface = retrofit.create(OmdbInterface.class);
    }
    /**
     * Gets a {@link MovieItemResponse} object with the details of a movie.
     * <p>
     * Has the following parameters as default: movie(type), full(plot), json(r).
     *
     * @param id imdb id
     *
     * @return Observer with a {@link MovieItemResponse} object
     */
    public Observable<MovieItemResponse> getMovieDetailsById(String id) {
        return mOmdbInterface.getMovieById(id, "full", "Movie", "json");
    }

    /**
     * Gets a {@link MovieListResponse} object with a list of movies by name.
     * <p>
     * Has the following parameters as default: movie(type), json(r).
     *
     * @param tileString title query
     *
     * @return Observer with a {@link MovieListResponse} object.
     */
    public Observable<MovieListResponse> getMoviesByTitle(String tileString) {
        return mOmdbInterface.getMoviesByTitle(tileString, "Movie", "json");
    }
}
