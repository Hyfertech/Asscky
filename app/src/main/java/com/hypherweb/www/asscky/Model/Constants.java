package com.hypherweb.www.asscky.Model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by AirUnknown on 2016-10-28.
 */

public class Constants {

    public static final String BOARD_TITLE = "title";

    public static final String BOARD_OWNER_EMAIL = "owner";

    public static final String BOARD_DESCRIPTION = "description";

    public static final String BOARD_ADDITIONAL_NOTES = "additional_notes";

    public static final String BOARD_MESSAGES = "messages";

    public static final String BOARD_NUMBER = "board_number";

    public static final String INTRO_MESSAGE = "Welcome, please be aware of what you post on this board. " +
            "Try to to hurt anyone with your posts.";

    public static final String INTRO_MESSAGE_KEY = "0000000000000000";

    public static final String MESSAGE_TEXT = "message";

    public static final String MESSAGE_OWNER = "owner";

    //public final String MESSAGE_UUID = "key";

    // Check network connection
    public static boolean isNetworkConnected( Context context){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
