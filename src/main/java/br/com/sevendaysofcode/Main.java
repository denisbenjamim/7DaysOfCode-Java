package br.com.sevendaysofcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Hello world!
 */
public final class Main {
    public static void main(String[] args) {
        final String url = "https://imdb-api.com/en/API/Top250Movies/k_388n6dm6";
        final String json = response(url);

        final List<Movie> movies = parseToFilmesList(json);

        imprimirFilmePeloRank(movies, 1);
    }

    public static void imprimirFilmePeloRank(List<Movie> movies, int rank){
        if(movies == null || movies.isEmpty())
            throw new RuntimeException("Lista nao poder ser vazia");
        
        final int totMovies = movies.size();
       
        if(rank > totMovies - 1 || rank < 0)
            throw new IllegalArgumentException("O indice selecionar nao existe");
        
        System.out.println(movies.stream().collect(Collectors.toMap(Movie::getRank, Function.identity())).get(rank));
    }

    public static List<Movie> parseToFilmesList(String json) {
        
        Pattern pattern = Pattern.compile("(\\\"\\w+\\\"):(\\\".*?\\\")");
        Matcher matcher = pattern.matcher(json);
        
        String label = null;
        String value = null;
        
        Movie movie = null;
        List<Movie> movies = new ArrayList<>(matcher.groupCount());
        while (matcher.find()) {
            label = getTextoEntreAspasDuplas(matcher.group(1));
            value = getTextoEntreAspasDuplas(matcher.group(2));

            switch (label) {
                case "id":
                    movie = new Movie().id(value);
                    break;

                case "rank":
                    movie.rank(Integer.valueOf(value));
                    break;

                case "title":
                    movie.title(value);
                    break;

                case "fullTitle":
                    movie.fullTitle(value);
                    break;

                case "year":
                    movie.year(Integer.valueOf(value));
                    break;

                case "image":
                    movie.image(value);
                    break;

                case "crew":
                    movie.crew(value);
                    break;

                case "imDbRating":
                    movie.imDbRating(Double.valueOf(value));
                    break;

                case "imDbRatingCount":
                    movie.imDbRatingCount(Integer.valueOf(value));
                    movies.add(movie);
                    break;

                default:
                    System.out.println("Campo nao mapeado -> " + label + " = " + value+"\n\n");
                break;
            }
        }
        return movies;
    }

    public static String getTextoEntreAspasDuplas(String string) {
        if (string == null)
            return string;

        Pattern patternEntreAspasDuplas = Pattern.compile("^(\")(.*)(\")$");
        Matcher matcher = patternEntreAspasDuplas.matcher(string);

        if (matcher.matches()) {
            return matcher.group(2);
        }
        return string;
    }

    public static String response(String url) {
        HttpGet get = new HttpGet("https://imdb-api.com/en/API/Top250Movies/k_388n6dm6");
        try (
                CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(get)) {
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void imprimeJsonFormatado(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jsonElement = JsonParser.parseString(json);

        System.out.println(gson.toJson(jsonElement));
    }
}
