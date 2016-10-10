package com.juawapps.openmoviesdb.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 *  Object that represents a movie on a movie list query.
 * <p>
 * Created by joaocevada on 08/10/16.
 */

public class MovieListItem implements Parcelable {
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


    protected MovieListItem(Parcel in) {
        imdbID = in.readString();
        poster = in.readString();
        title = in.readString();
        type = in.readString();
        year = in.readString();
    }

    public static final Creator<MovieListItem> CREATOR = new Creator<MovieListItem>() {
        @Override
        public MovieListItem createFromParcel(Parcel in) {
            return new MovieListItem(in);
        }

        @Override
        public MovieListItem[] newArray(int size) {
            return new MovieListItem[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imdbID);
        dest.writeString(poster);
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(year);
    }
}
