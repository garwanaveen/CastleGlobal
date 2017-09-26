package com.castleglobal.assignment.Utils;

/**
 * Created by naveenkumar on 24/09/17.
 */


/**
 * interface for callbacks when Get or Post request are created
 */

public interface CallBackInterface {

        public void postExecute(String url, int status);

        public void preExecute(String url);

        public void progressUpdate(String url, int percentage);

        public void processResponse(String url, String response);

}


