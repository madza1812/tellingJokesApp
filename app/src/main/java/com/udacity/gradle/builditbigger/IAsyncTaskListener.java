package com.udacity.gradle.builditbigger;

import android.content.Context;

/**
 * Created by An on 12/8/2015.
 */
public interface IAsyncTaskListener<T> {
    public void onTaskStarted (Context context);
    public void onTaskComplete(Context context, T result);
}
