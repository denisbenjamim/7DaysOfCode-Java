package br.com.sevendaysofcode.marvel;

import java.io.IOException;
import java.text.MessageFormat;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import br.com.sevendaysofcode.APIClient;

public class MarvelApiClient implements APIClient {
    final String ENDPOINT;

    public MarvelApiClient(final String API_KEY_PUBLIC, final String API_KEY_PRIVATE) {
        String ts =  String.valueOf(System.currentTimeMillis());
        final String hash = MessageFormat.format("{0}{1}{2}",ts, API_KEY_PRIVATE, API_KEY_PUBLIC);
        final String hashText = DigestUtils.md5Hex(hash);
        ENDPOINT = MessageFormat.format("https://gateway.marvel.com:443/v1/public/series?apikey={0}&hash={1}&ts={2}", API_KEY_PUBLIC, hashText, ts );
    }

    @Override
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
