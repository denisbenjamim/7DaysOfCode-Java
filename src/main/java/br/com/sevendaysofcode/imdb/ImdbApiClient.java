package br.com.sevendaysofcode.imdb;

import java.io.IOException;
import java.text.MessageFormat;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ImdbApiClient {
    final String ENDPOINT;

    public ImdbApiClient(final String API_KEY) {
       ENDPOINT = MessageFormat.format("https://imdb-api.com/en/API/Top250Movies/{0}", API_KEY);
    }

    public String getBody() {
        HttpGet get = new HttpGet(ENDPOINT);
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
}
