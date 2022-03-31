package br.com.sevendaysofcode;

import java.io.IOException;

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
        HttpGet get = new HttpGet("https://imdb-api.com/en/API/Top250Movies/k_388n6dm6");
        try(
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(get)
        ) {
            if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
                String jsonPretty = EntityUtils.toString(response.getEntity());
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonElement jsonElement = JsonParser.parseString(jsonPretty);
                
                System.out.println(gson.toJson(jsonElement));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        
       
    }
}
