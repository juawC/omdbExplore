package com.juawapps.openmoviesdb.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Response object for a movie list query.
 * <p>
 * Created by joaocevada on 08/10/16.
 */

public class MovieListResponse {

    @SerializedName("Search")
    private List<MovieListItem> results;
    @SerializedName("totalResults")
    private String totalResults;
    @SerializedName("Response")
    private String response;

    public MovieListResponse(List<MovieListItem> results, String totalResults, String response) {
        this.results = results;
        this.totalResults = totalResults;
        this.response = response;
    }

    public List<MovieListItem> getResults() {
        return results;
    }

    public void setResults(List<MovieListItem> results) {
        this.results = results;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
