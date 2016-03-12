package com.example.android.danga;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Joke {

    public static final String TAG = Joke.class.getSimpleName();

    public String getJoke() {
        return "This is a joke from Java Library !";
    }

    public String getRandomJokeFromChuckNorrisDatabase() {

        final String CHUCK_NORRIS_RANDOM_URL = "http://api.icndb.com/jokes/random";

        HttpURLConnection urlConn = null;
        BufferedReader reader = null;
        String jokeJsonString = null;
        String joke;

        try{
            URL url = new URL(CHUCK_NORRIS_RANDOM_URL);

            urlConn= (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");
            urlConn.connect();

            InputStream inputStream = urlConn.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
                buffer.append("\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            jokeJsonString = buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            jokeJsonString = "";
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }

        }

        // Get joke from JsonJokeString
        final String JSON_TYPE = "type";
        final String JSON_VALUE = "value";
        final String JSON_ID = "id";
        final String JSON_JOKE = "joke";
        final String JSON_SUCCESS = "success";

        JSONObject jokeJson = new JSONObject(jokeJsonString);
        if (!jokeJson.getString(JSON_TYPE).equalsIgnoreCase(JSON_SUCCESS)) {
            return null;
        } else {
            joke = (String) ((JSONObject) jokeJson.get(JSON_VALUE)).get(JSON_JOKE);
        }

        return joke;
    }
}
