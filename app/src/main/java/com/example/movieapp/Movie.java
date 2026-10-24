package com.example.movieapp;

public class Movie {
    public final String title, type, rating, director, overview, imdb, metacritic, initials;
    public final int background;
    public Movie(String title, String type, String rating, String director, String overview, String imdb, String metacritic, String initials, int background) {
        this.title=title; this.type=type; this.rating=rating; this.director=director; this.overview=overview;
        this.imdb=imdb; this.metacritic=metacritic; this.initials=initials; this.background=background;
    }
}
