package com.udacity.gradle.builditbigger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.danga.displayjokeactivity.DisplayJokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.lang.Override;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    InterstitialAd mInterstitialAdNewJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(TAG, "Setup the Interstitial Ads");
        // Pre-loading Interstitial Ads
        mInterstitialAdNewJoke = new InterstitialAd(this);
        mInterstitialAdNewJoke.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAdNewJoke.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Log.v(TAG, "Ads is closed.");
                requestNewInterstitial();
                if (Util.isNetworkAvailable(MainActivity.this)) {
                    FetchJokeTaskCompleteListener listener = new FetchJokeTaskCompleteListener();
                    new FetchJokeEndPointAsyncTask(MainActivity.this, listener).execute();
                } else {
                    Util.displayNeutralAlert(MainActivity.this,
                            getString(R.string.no_internet_title),
                            getString(R.string.no_internet_message));
                }
            }
        });
        requestNewInterstitial();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        if (mInterstitialAdNewJoke.isLoaded()) {
            Log.v(TAG, "Interstitial Ads is loaded. It's gonna be showed");
            mInterstitialAdNewJoke.show();
        } else {
            Log.v(TAG, "Interstitial Ads is not loaded yet. It's NOT gonna be showed.");
            if (Util.isNetworkAvailable(MainActivity.this)) {
                FetchJokeTaskCompleteListener listener = new FetchJokeTaskCompleteListener();
                new FetchJokeEndPointAsyncTask(MainActivity.this, listener).execute();
            } else {
                Util.displayNeutralAlert(MainActivity.this,
                        getString(R.string.no_internet_title),
                        getString(R.string.no_internet_message));
            }
        }

    }

    private void requestNewInterstitial() {
        Log.v(TAG, "Interstitial Ads is requested to load.");
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAdNewJoke.loadAd(adRequest);
    }
}

// AsyncTask to get Joke from EndPoint
class FetchJokeTaskCompleteListener implements IAsyncTaskListener<String> {
    ProgressDialog loadingDialog;
    @Override
    public void onTaskStarted(Context context) {
        loadingDialog= new ProgressDialog(context);
        loadingDialog.setMessage(context.getString(R.string.retrieving_joke_message));
        loadingDialog.setCancelable(true);
        loadingDialog.show();
    }

    @Override
    public void onTaskComplete(Context context, String result) {
        if (loadingDialog.isShowing())
            loadingDialog.dismiss();
        Intent displayJokeIntent = new Intent(context, DisplayJokeActivity.class);
        displayJokeIntent.putExtra(DisplayJokeActivity.JOKE_KEY, result);
        if (displayJokeIntent.resolveActivity(context.getPackageManager()) != null)
            context.startActivity(displayJokeIntent);
    }
}

