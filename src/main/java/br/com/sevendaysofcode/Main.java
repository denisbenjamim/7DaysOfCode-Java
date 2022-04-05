package br.com.sevendaysofcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.sevendaysofcode.html.HTMLGenerator;
import br.com.sevendaysofcode.imdb.ImdbApiClient;
import br.com.sevendaysofcode.imdb.ImdbMovieJsonParser;
import br.com.sevendaysofcode.marvel.MarvelApiClient;
import br.com.sevendaysofcode.marvel.MarvelSerieJsonParser;

public final class Main {
    public static void main(String[] args) throws IOException {
        final List<Content> contents = new ArrayList<>();
        final String jsonIMDB = new ImdbApiClient("k_388n6dm6").getBody();
        final String jsonMarvel = new MarvelApiClient("7716de952c52bc070a6fe92fd890d7db", "3acc2f7b778ed1215f7506a595a1013620404456").getBody();
        
        contents.addAll(new ImdbMovieJsonParser(jsonIMDB).parse());
        contents.addAll(new MarvelSerieJsonParser(jsonMarvel).parse());
        
        Collections.sort(contents, Comparator.reverseOrder());
        try (HTMLGenerator generator = new HTMLGenerator("index.html")) {
            generator.generate(contents);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
