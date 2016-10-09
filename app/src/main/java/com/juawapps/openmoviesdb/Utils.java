package com.juawapps.openmoviesdb;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Utils class.
 * <p>
 * Created by joaocevada on 09/10/16.
 */

public class Utils {


    /**
     *  Checks if the device is connected to the internet.
     *  <p>
     *  @param context context
     *  @return true if it is connected false otherwise
     */
    public static boolean isConnected(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
