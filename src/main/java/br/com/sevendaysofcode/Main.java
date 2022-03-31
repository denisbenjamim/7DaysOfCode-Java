package br.com.sevendaysofcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        final List<List<String>> filmes = parseToFilmesList(json);

        imprimirFilmePeloIndice(filmes, 249);
      
    }

    public static void imprimirFilmePeloIndice(List<List<String>> filmes, int indiceSelecionado){
        if(filmes == null || filmes.isEmpty())
            throw new RuntimeException("Lista nao poder ser vazia");
        
        final int totalFilmes = filmes.get(0).size();
       
        if(indiceSelecionado > totalFilmes - 1 || indiceSelecionado < 0)
            throw new IllegalArgumentException("O indice selecionar nao existe");
        
        for(List<String> campo: filmes){
            System.out.println(campo.get(indiceSelecionado));
        }

        System.out.println();
    }
    
    public static List<List<String>> parseToFilmesList(String json) {
        List<String> ids = new ArrayList<>();
        List<String> ranks = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<String> fullTitles = new ArrayList<>();
        List<String> years = new ArrayList<>();
        List<String> images = new ArrayList<>();
        List<String> crews = new ArrayList<>();
        List<String> imDbRatings = new ArrayList<>();
        List<String> imDbRatingCounts = new ArrayList<>();
        List<List<String>> filmes = Arrays.asList(ids, ranks, titles, fullTitles, years, images,crews, imDbRatings, imDbRatingCounts);

        Pattern pattern = Pattern.compile("(\\\"\\w+\\\"):(\\\".*?\\\")");
        Matcher matcher = pattern.matcher(json);

        String label = null;
        String value = null;

        while (matcher.find()) {
            label = getTextoEntreAspasDuplas(matcher.group(1));
            value = getTextoEntreAspasDuplas(matcher.group(2));

            switch (label) {
                case "id":
                    ids.add(value);
                    break;

                case "rank":
                    ranks.add(value);
                    break;

                case "title":
                    titles.add(value);
                    break;

                case "fullTitle":
                    fullTitles.add(value);
                    break;

                case "year":
                    years.add(value);
                    break;

                case "image":
                    images.add(value);
                    break;

                case "crew":
                    crews.add(value);
                    break;

                case "imDbRating":
                    imDbRatings.add(value);
                    break;

                case "imDbRatingCount":
                    imDbRatingCounts.add(value);
                    break;

                default:
                    System.out.println("Campo nao mapeado -> " + label + " = " + value);
                break;
            }
        }
        return filmes;
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
