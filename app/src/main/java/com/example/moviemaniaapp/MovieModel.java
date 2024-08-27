package com.example.moviemaniaapp;

import java.util.List;

public class MovieModel {
    private String id;
    private String name;
    private double rating;
    private String director;
    private String genre;
    private String description;
    private List<String> comments;

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    private int photoId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public MovieModel(String id, String name, double rating, String director, String genre, String description, List<String> comments) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.director = director;
        this.genre = genre;
        this.description = description;
        this.comments = comments;
    }
}
