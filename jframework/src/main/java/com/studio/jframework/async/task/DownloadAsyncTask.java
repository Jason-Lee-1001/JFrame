package com.studio.jframework.async.task;

import android.os.AsyncTask;

/**
 * Created by Jason
 * <p/>
 * A simple async task for downloading file
 */
public class DownloadAsyncTask extends AsyncTask<String, Integer, Byte[]> {

    @Override
    protected Byte[] doInBackground(String... params) {
        return new Byte[0];
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onCancelled(Byte[] bytes) {
        super.onCancelled(bytes);

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Byte[] bytes) {
        super.onPostExecute(bytes);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
