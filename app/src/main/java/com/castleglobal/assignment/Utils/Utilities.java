package com.castleglobal.assignment.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.castleglobal.assignment.Objects.RestaurantItemObject;
import com.castleglobal.assignment.Objects.Restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by naveenkumar on 24/09/17.
 */

public class Utilities {

    /* Checks if any connected network
        @param Context contect
        @return true if any connected network exists else false
     */
    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if(netInfo != null)
            return  netInfo.isConnected();
        else
            return false;
    }



}
