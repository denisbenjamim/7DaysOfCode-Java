package br.com.sevendaysofcode;

import java.text.MessageFormat;

public class Serie implements Content{
    private String title;
    private String type;
    private String startYear;
    private String endYear;
    private String thumbnailPath;
    private String thumbnailExtension;
    private String rating;

    @Override
    public String urlImage() {
        if(thumbnailPath == null || thumbnailPath.endsWith("image_not_available")){
            return "Sem Imagem Disponivel";
        }
        return MessageFormat.format("{0}/{1}.{2}", thumbnailPath ,"portrait_uncanny", thumbnailExtension);
    }

    public Serie thumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
        return this;
    }

    public Serie thumbnailExtension(String thumbnailExtension) {
        this.thumbnailExtension = thumbnailExtension;
        return this;
    }

    @Override
    public String title() {
        return title;
    }
    public Serie title(String title) {
        this.title = title;
        return this;
    }
    
    @Override
    public String year() {
        return startYear;
    }

    public Serie startYear(String startYear) {
        this.startYear = startYear;
        return this;
    }
    public Serie endYear(String endYear) {
        this.endYear = endYear;
        return this;
    }

    @Override
    public String rating() {
        return rating;
    }
    public Serie rating(String rating) {
        if(rating == null || rating.trim().isEmpty()){
            rating = "N/A";
        }
        this.rating = rating;
        return this;
    }

    @Override
    public String type() {
        return this.type;
    } 

    public Serie type(String type) {
        this.type = type;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Serie other = (Serie) obj;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    @Override
    public int compareTo(Content o) {
        return this.year().compareToIgnoreCase(o.year());
    }

    
}
