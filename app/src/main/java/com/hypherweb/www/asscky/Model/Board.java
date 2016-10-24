package com.hypherweb.www.asscky.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AirUnknown on 2016-10-23.
 */

public class Board {

    public static final String BOARD_TITLE = "title";
    public static final String BOARD_OWNER_EMAIL = "owner";
    public static final String BOARD_DESCRIPTION = "description";
    public static final String BOARD_ADDITIONAL_NOTES = "additional_notes";
    public static final String BOARD_MESSAGES = "messages";

    String mTitle;
    String mOwner;
    String mDescription;
    String mAdditionalNotes;

    public Map<String, Boolean> boards = new HashMap<>();

    public Board() {
    }

    public Board(String title, String owner, String description, String additionalNotes) {
        mTitle = title;
        mOwner = owner;
        mDescription = description;
        if(additionalNotes.equals(null) || additionalNotes.isEmpty() || additionalNotes.equals("")) {

            mAdditionalNotes = "N/A";
        } else {
            mAdditionalNotes = additionalNotes;
        }
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put(BOARD_TITLE, mTitle);
        result.put(BOARD_OWNER_EMAIL, mOwner);
        result.put(BOARD_DESCRIPTION, mDescription);
        result.put(BOARD_ADDITIONAL_NOTES, mAdditionalNotes);
        result.put(BOARD_MESSAGES, null);
        return result;
    }


}
