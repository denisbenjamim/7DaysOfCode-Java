package br.com.sevendaysofcode;

public class Movie implements Content{
    private String id;
    private String rank;
    private String title;
    private String fullTitle;
    private String year;
    private String crew;
    private String urlImage;
    private String rating;
    private String imDbRatingCount;

    @Override
    public String type() {
        return "movie";
    }

    @Override
    public String urlImage() {
        return urlImage;
    }
    public Movie image(String image) {
        this.urlImage = image;
        return this;
    }
    public String getId() {
        return id;
    }
    public Movie id(String id) {
        this.id = id;
        return this;
    }
    public String getRank() {
        return rank;
    }
    public Movie rank(String rank) {
        this.rank = rank;
        return this;
    }
    @Override
    public String title() {
        return title;
    }
    public Movie title(String title) {
        this.title = title;
        return this;
    }
    public String getFullTitle() {
        return fullTitle;
    }
    public Movie fullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
        return this;
    }
    
    @Override
    public String year() {
        return year;
    }
    public Movie year(String year) {
        this.year = year;
        return this;
    }
    public String getCrew() {
        return crew;
    }
    public Movie crew(String crew) {
        this.crew = crew;
        return this;
    }

    public String rating() {
        return rating;
    }
    public Movie imDbRating(String imDbRating) {
        this.rating = imDbRating;
        return this;
    }
    public String getImDbRatingCount() {
        return imDbRatingCount;
    }
    public Movie imDbRatingCount(String imDbRatingCount) {
        this.imDbRatingCount = imDbRatingCount;
        return this;
    }

    @Override
    public int compareTo(Content o) {
        return this.year().compareToIgnoreCase(o.year());
    }
}
