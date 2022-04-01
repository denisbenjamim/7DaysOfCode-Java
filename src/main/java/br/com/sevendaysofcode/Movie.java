package br.com.sevendaysofcode;

public class Movie {
    private String id;
    private int rank;
    private String title;
    private String fullTitle;
    private int year;
    private String crew;
    private String image;
    private double imDbRating;
    private int imDbRatingCount;

    @Override
    public String toString() {
        return "Movie [\n\tcrew=" + crew + ", \n\tfullTitle=" + fullTitle + ", \n\tid=" + id + ", \n\timDbRating=" + imDbRating
                + ", \n\timDbRatingCount=" + imDbRatingCount + ", \n\timage=" + image + ", \trank=" + rank + ", \n\ttitle="
                + title + ", \n\tyear=" + year + "\n]";
    }
    public String getImage() {
        return image;
    }
    public Movie image(String image) {
        this.image = image;
        return this;
    }
    public String getId() {
        return id;
    }
    public Movie id(String id) {
        this.id = id;
        return this;
    }
    public int getRank() {
        return rank;
    }
    public Movie rank(int rank) {
        this.rank = rank;
        return this;
    }
    public String getTitle() {
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
    public int getYear() {
        return year;
    }
    public Movie year(int year) {
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
    public double getImDbRating() {
        return imDbRating;
    }
    public Movie imDbRating(double imDbRating) {
        this.imDbRating = imDbRating;
        return this;
    }
    public int getImDbRatingCount() {
        return imDbRatingCount;
    }
    public Movie imDbRatingCount(int imDbRatingCount) {
        this.imDbRatingCount = imDbRatingCount;
        return this;
    } 
}
