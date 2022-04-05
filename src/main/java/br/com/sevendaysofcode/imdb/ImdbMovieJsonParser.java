package br.com.sevendaysofcode.imdb;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.sevendaysofcode.Content;
import br.com.sevendaysofcode.JsonParser;
import br.com.sevendaysofcode.Movie;

public class ImdbMovieJsonParser implements JsonParser {
    final String JSON;
    
    public ImdbMovieJsonParser(String jSON) {
        JSON = jSON;
    }

    @Override
    public List<Content> parse() {
        Pattern pattern = Pattern.compile("(\\\"\\w+\\\"):(\\\".*?\\\")");
        Matcher matcher = pattern.matcher(JSON);
        
        String label = null;
        String value = null;
        
        Movie movie = null;
        List<Content> contents = new ArrayList<>(matcher.groupCount());

        while (matcher.find()) {
            label = getTextoEntreAspasDuplas(matcher.group(1));
            value = getTextoEntreAspasDuplas(matcher.group(2));

            switch (label) {
                case "id":
                    movie = new Movie().id(value);
                    break;

                case "rank":
                    movie.rank(value);
                    break;

                case "title":
                    movie.title(value);
                    break;

                case "fullTitle":
                    movie.fullTitle(value);
                    break;

                case "year":
                    movie.year(value);
                    break;

                case "image":
                    movie.image(value);
                    break;

                case "crew":
                    movie.crew(value);
                    break;

                case "imDbRating":
                    movie.imDbRating(value);
                    break;

                case "imDbRatingCount":
                    movie.imDbRatingCount(value);
                    contents.add(movie);
                    break;
                case "errorMessage":
                    if(value !=  null && !value.isEmpty()){
                        System.err.println();
                    }
                    break;
                default:
                    System.out.println("Campo nao mapeado -> " + label + " = " + value+"\n\n");
                break;
            }
        }
        return contents;
    }

    public String getTextoEntreAspasDuplas(String string) {
        if (string == null)
            return string;

        Pattern patternEntreAspasDuplas = Pattern.compile("^(\")(.*)(\")$");
        Matcher matcher = patternEntreAspasDuplas.matcher(string);

        if (matcher.matches()) {
            return matcher.group(2);
        }
        return string;
    }
}
