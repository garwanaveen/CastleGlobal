package com.castleglobal.assignment.Utils;

/**
 * Created by naveenkumar on 24/09/17.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class to handles get request
 */
public class HTTPGetRequest {

    /**
     * constructor to get url and callback object and context
     * hits a get request to url and calls callback functions
     *
     * @param endpoint      Where request is to be made
     * @param callback object of callback
     * @param context
     */
    public HTTPGetRequest(final CallBackInterface callback, final String endpoint,
                          final Context context) {

        class getrequest extends AsyncTask<String, Integer, Integer> {

            HttpURLConnection request = null;
            BufferedReader reader = null;

            @Override
            protected Integer doInBackground(String... arg0) {
                try {

                    URL url = new URL(endpoint);

                    // Create the request and open the connection
                    request = (HttpURLConnection) url.openConnection();
                    request.setRequestProperty("user-key", Constants.API_KEY);
                    request.setRequestMethod("GET");
                    request.connect();

                    int totalLoad = request.getContentLength();

                    // Read the input stream into a String
                    InputStream inputStream = new BufferedInputStream(request.getInputStream());
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(request.getInputStream()));

                    String line; int count = 0;
                    while ((line = reader.readLine()) != null) {
                        count+= line.length();
                        publishProgress( (count * 100/totalLoad) );
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() != 0) {
                        callback.processResponse(endpoint, buffer.toString());
                    }

                    return  request.getResponseCode();

                } catch (IOException e) {
                    return null;
                } finally{
                    if (request != null) {
                        request.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                        }
                    }
                }
            }






            @Override
            protected void onPreExecute() {
                callback.preExecute(endpoint);
                super.onPreExecute();
            }


            protected void onProgressUpdate(int percentage) {
                callback.progressUpdate(endpoint, percentage);
            }

            @Override
            protected void onPostExecute(Integer statusCode) {
                try {
                    callback.postExecute(endpoint, statusCode);
                } catch (Exception e) {
                    Log.d("error", "cancel request");
                }

            }
        }
        if (Utilities.haveNetworkConnection(context)) {
            new getrequest().execute();
        }
    }

}
