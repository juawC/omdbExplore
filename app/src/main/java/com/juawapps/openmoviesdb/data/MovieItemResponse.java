package com.juawapps.openmoviesdb.data;

import com.google.gson.annotations.SerializedName;

/**
 * Response object for a single movie query.
 * <p>
 * Created by joaocevada on 08/10/16.
 */

public class MovieItemResponse {

    @SerializedName("Actors")
    String actors;
    @SerializedName("Awards")
    String awards;
    @SerializedName("Country")
    String country;
    @SerializedName("Director")
    String director;
    @SerializedName("Genre")
    String genre;
    @SerializedName("imdbID")
    String imdbID;
    @SerializedName("imdbRating")
    String imdbRating;
    @SerializedName("imdbVotes")
    String imdbVotes;
    @SerializedName("Language")
    String language;
    @SerializedName("Metascore")
    String metascore;
    @SerializedName("Plot")
    String plot;
    @SerializedName("Poster")
    String poster;
    @SerializedName("Rated")
    String rated;
    @SerializedName("Released")
    String released;
    @SerializedName("Response")
    String response;
    @SerializedName("Runtime")
    String runtime;
    @SerializedName("Title")
    String title;
    @SerializedName("Type")
    String type;
    @SerializedName("Writer")
    String writer;
    @SerializedName("Year")
    String year;

    public MovieItemResponse(String actors, String awards, String country, String director, String genre,
                             String imdbID, String imdbRating, String imdbVotes, String language,
                             String metascore, String plot, String poster, String rated, String released,
                             String response, String runtime, String title, String type, String writer,
                             String year) {

        this.actors = actors;
        this.awards = awards;
        this.country = country;
        this.director = director;
        this.genre = genre;
        this.imdbID = imdbID;
        this.imdbRating = imdbRating;
        this.imdbVotes = imdbVotes;
        this.language = language;
        this.metascore = metascore;
        this.plot = plot;
        this.poster = poster;
        this.rated = rated;
        this.released = released;
        this.response = response;
        this.runtime = runtime;
        this.title = title;
        this.type = type;
        this.writer = writer;
        this.year = year;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMetascore() {
        return metascore;
    }

    public void setMetascore(String metascore) {
        this.metascore = metascore;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
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

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
