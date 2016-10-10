package com.juawapps.openmoviesdb.data;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Singleton client for retrieving a retrofit instance with the omdb api.
 * <p>
 * Created by joaocevada on 08/10/16.
 */
public class OmdbClient {

    public static final String BASE_URL = "http://www.omdbapi.com";
    private static OmdbClient sOmdbClient;
    private final Retrofit mRetrofit;

    private OmdbClient() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static OmdbClient getInstance() {
        if (sOmdbClient == null) {
            synchronized (OmdbClient.class) {
                if (sOmdbClient == null)
                    sOmdbClient = new OmdbClient();
            }
        }
        return sOmdbClient;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

}