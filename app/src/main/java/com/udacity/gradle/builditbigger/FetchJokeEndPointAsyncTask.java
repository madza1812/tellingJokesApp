package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.danga.builitbigger.backend.jokeApi.JokeApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by An on 12/8/2015.
 */
public class FetchJokeEndPointAsyncTask extends AsyncTask<Void, Void, String> {

    public static final String TAG = FetchJokeEndPointAsyncTask.class.getSimpleName();

    private static JokeApi myApiService = null;
    private Context context;
    private IAsyncTaskListener<String> taskListener;

    public FetchJokeEndPointAsyncTask(Context context, IAsyncTaskListener<String> listener) {
        this.context = context;
        this.taskListener = listener;
    }


    @Override
    protected void onPreExecute() {
        taskListener.onTaskStarted(context);
    }

    @Override
    protected String doInBackground(Void... params) {
        JokeApi.Builder builder;
        boolean testLocal = true;
        if(myApiService == null) {  // Only do this once
            if (testLocal) {
                builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local server
                        .setRootUrl("http://10.0.3.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for local server
            } else {
                builder = new JokeApi.Builder(
                        AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(),
                        null).setRootUrl("https://builditbigger-danga.appspot.com/_ah/api/");
            }

            myApiService = builder.build();
            Log.v(MainActivity.TAG, myApiService.toString());
        }

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            Log.v(TAG, "doInBackground: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null && result.length() > 0)
            taskListener.onTaskComplete(context, result);
        else
            Util.displayToast(context, context.getString(R.string.unable_retrieve_joke));
    }
}
