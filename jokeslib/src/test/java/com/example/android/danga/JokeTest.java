package com.example.android.danga;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by An on 12/6/2015.
 */
public class JokeTest {
    @Test
    public void verifyGetJoke() {
        Joke joke = new Joke();
        String receivedJoke = joke.getRandomJokeFromChuckNorrisDatabase();
        assertNotNull("Received joke is NULL", receivedJoke);
        assertTrue("Received joke's length is NOT GREATER THAN 0.", receivedJoke.length() > 0);
    }
}
