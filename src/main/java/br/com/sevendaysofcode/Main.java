package br.com.sevendaysofcode;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.com.sevendaysofcode.html.HTMLGenerator;
import br.com.sevendaysofcode.imdb.ImdbApiClient;
import br.com.sevendaysofcode.imdb.ImdbMovieJsonParser;

public final class Main {
    public static void main(String[] args) {
        final String JSON = new ImdbApiClient("k_388n6dm6").getBody();
        final List<Movie> movies = new ImdbMovieJsonParser(JSON).parse();

        try(HTMLGenerator generator = new HTMLGenerator("index.html")){
           generator.generate(movies); 
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void imprimirFilmePeloRank(List<Movie> movies, int rank){
        if(movies == null || movies.isEmpty())
            throw new RuntimeException("Lista nao poder ser vazia");
        
        final int totMovies = movies.size();
       
        if(rank > totMovies - 1 || rank < 0)
            throw new IllegalArgumentException("O indice selecionar nao existe");
        
        System.out.println(movies.stream().collect(Collectors.toMap(Movie::getRank, Function.identity())).get(rank));
    }
}
