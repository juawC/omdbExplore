package com.juawapps.openmoviesdb.data;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Interface with GET queries to the omdb api.
 * <p>
 * Created by joaocevada on 08/10/16.
 */
public interface OmdbInterface {
    @GET("/")
    Observable<MovieListResponse> getMoviesByTitle(@Query("s") String title,
                                                   @Query("type") String type,
                                                   @Query("r") String format);

    @GET("/")
    Observable<MovieItemResponse> getMovieById(@Query("i") String title, @Query("plot") String plot,
                                               @Query("type") String type,
                                               @Query("r") String format);

}