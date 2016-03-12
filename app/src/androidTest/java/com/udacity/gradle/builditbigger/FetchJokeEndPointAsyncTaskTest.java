package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.UiThreadTest;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 * Created by An on 12/8/2015.
 */
public class FetchJokeEndPointAsyncTaskTest extends AndroidTestCase{

    FetchJokeEndPointAsyncTask fetchJoke;
    CountDownLatch signal;
    String joke = null;

    protected void setUp() throws Exception {
        super.setUp();
        signal = new CountDownLatch(1);
        fetchJoke = new FetchJokeEndPointAsyncTask(getContext(), new IAsyncTaskListener<String>() {
            @Override
            public void onTaskStarted(Context context) {
                // Do nothing
            }

            @Override
            public void onTaskComplete(Context context, String result) {
                joke = result;
                Log.v("TEST JOKE: ",joke);
                assertNotNull("Returned joke from endpoint is NULL", joke);
                assertTrue("Returned joke has length is NOT GREATER than 0", joke.length() > 0);
                signal.countDown();
            }
        });
    }

    @UiThreadTest
    public void testFetchJokeEndPointAsyncTask() throws InterruptedException {
        fetchJoke.execute();
        signal.await(30, TimeUnit.SECONDS);
        assertTrue("Time Out. Joke is Either NULL OR length is NOT GREATER than 0", (joke != null && joke.length() > 0));
    }

}
