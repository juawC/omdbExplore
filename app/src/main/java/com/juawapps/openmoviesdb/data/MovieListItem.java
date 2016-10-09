package com.juawapps.openmoviesdb.data;

import com.google.gson.annotations.SerializedName;

/**
 *  Object that represents a movie on a movie list query.
 * <p>
 * Created by joaocevada on 08/10/16.
 */

public class MovieListItem {
    @SerializedName("imdbID")
    private String imdbID;
    @SerializedName("Poster")
    private String poster;
    @SerializedName("Title")
    private String title;
    @SerializedName("Type")
    private String type;
    @SerializedName("Year")
    private String year;

    public MovieListItem(String imdbID, String poster, String title, String type, String year) {
        this.imdbID = imdbID;
        this.poster = poster;
        this.title = title;
        this.type = type;
        this.year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
