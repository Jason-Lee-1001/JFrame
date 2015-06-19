package com.studio.jframework.async.task;

import android.os.AsyncTask;

/**
 * Created by Jason
 */
public class UploadAsyncTask extends AsyncTask<String, Integer, Byte[]> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Byte[] bytes) {
        super.onPostExecute(bytes);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Byte[] bytes) {
        super.onCancelled(bytes);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Byte[] doInBackground(String... params) {
        return new Byte[0];
    }
}
