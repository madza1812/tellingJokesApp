package com.example.android.danga.displayjokeactivity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DisplayJokeActivityFragment extends Fragment {

    public static final String TAG = DisplayJokeActivityFragment.class.getSimpleName();
    public static final String SAVED_JOKE_KEY = "key_joke_saved";

    String joke;

    public DisplayJokeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_display_joke, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.getExtras() != null) {
            Log.v(TAG, "Get joke from Intent");
            joke = intent.getStringExtra(DisplayJokeActivity.JOKE_KEY);
        } else if (savedInstanceState != null) {
            Log.v(TAG, "Get joke from savedInstanceState.");
            joke = (String) savedInstanceState.get(SAVED_JOKE_KEY);
        } else {
            Log.v(TAG, "There is somethign wrong here.");
            joke = getString(R.string.exception_joke_display);
        }
        TextView tvJokeDisplay = (TextView) rootView.findViewById(R.id.textView_joke_display);
        if (joke != null && joke.length() != 0)
            tvJokeDisplay.setText(joke);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(SAVED_JOKE_KEY, joke);
        super.onSaveInstanceState(outState);
    }
}
