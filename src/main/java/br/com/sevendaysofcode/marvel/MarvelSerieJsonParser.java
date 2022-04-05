package br.com.sevendaysofcode.marvel;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import br.com.sevendaysofcode.Content;
import br.com.sevendaysofcode.JsonParser;
import br.com.sevendaysofcode.Serie;

public class MarvelSerieJsonParser implements JsonParser {
    final String JSON;
    
    public MarvelSerieJsonParser(String jSON) {
        JSON = jSON;
    }

    @Override
    public List<Content> parse() {
        Pattern pattern = Pattern.compile("(\\\"\\w+\\\"):(?=\\{(.*?)|(.*?),)");
        Matcher matcher = pattern.matcher(JSON);
        
        String label = null;
        String value = null;
        
        Serie serie = null;
        List<Content> contents = new ArrayList<>(matcher.groupCount());

        while (matcher.find()) {
            label = formataTexto(matcher.group(1));
            value = formataTexto(matcher.group(3));

            switch (label) {
                case "title":
                    serie = new Serie().title(value);
                    break; 

                case "startYear":
                    serie.startYear(value);
                    break;

                case "endYear":
                    serie.endYear(value);
                    break;

                case "path":
                    serie.thumbnailPath(value);
                    break;

                case "extension":
                    serie.thumbnailExtension(value);
                    break;

                case "rating":
                    serie.rating(value);
                    break;
                
                case "type":
                    serie.type(value);
                    contents.add(serie);
                    break;
            }
        }
        return new LinkedHashSet<>(contents).stream().collect(Collectors.toList());
    }

    public String formataTexto(String string) {
        if (string == null)
            return string;

        if(string.endsWith(",") || string.endsWith("}"))
            string = string.substring(0, string.length() - 1);
        
        if(string.endsWith("}]"))
            string = string.substring(0, string.length() - 2);

        Pattern patternEntreAspasDuplas = Pattern.compile("^(\")(.*)(\")$");
        Matcher matcher = patternEntreAspasDuplas.matcher(string);

        if (matcher.matches()) {
            return matcher.group(2);
        }
        return string;
    }
}
