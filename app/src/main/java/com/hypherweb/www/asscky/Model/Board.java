package com.hypherweb.www.asscky.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AirUnknown on 2016-10-23.
 */

public class Board implements Parcelable {

    String mTitle;
    String mOwner;
    String mDescription;
    String mBoardNumber;
    String mAdditionalNotes;


    public Map<String, Boolean> boards = new HashMap<>();

    public Board() {
    }


    public Board(String title, String description, String owner, String boardNumber, String additionalNotes) {
        mTitle = title;
        mOwner = owner;
        mDescription = description;
        mBoardNumber = boardNumber;
        if (additionalNotes.equals(null) || additionalNotes.isEmpty() || additionalNotes.equals("")) {

            mAdditionalNotes = "N/A";
        } else {
            mAdditionalNotes = additionalNotes;
        }
    }

    protected Board(Parcel in) {
        mTitle = in.readString();
        mOwner = in.readString();
        mDescription = in.readString();
        mBoardNumber = in.readString();
        mAdditionalNotes = in.readString();
    }

    public Board(HashMap<String, Object> boardMap) {
        mTitle = (String) boardMap.get(Constants.BOARD_TITLE);
        mOwner = (String) boardMap.get(Constants.BOARD_OWNER_EMAIL);
        mDescription = (String) boardMap.get(Constants.BOARD_DESCRIPTION);
        mBoardNumber = (String) boardMap.get(Constants.BOARD_NUMBER);
    }

    public static final Creator<Board> CREATOR = new Creator<Board>() {
        @Override
        public Board createFromParcel(Parcel in) {
            return new Board(in);
        }

        @Override
        public Board[] newArray(int size) {
            return new Board[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public String getOwner() {
        return mOwner;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getAdditionalNotes() {
        return mAdditionalNotes;
    }

    public String getBoardNumber() {
        return mBoardNumber;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setOwner(String owner) {
        mOwner = owner;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setBoardNumber(String boardNumber) {
        mBoardNumber = boardNumber;
    }

    public void setAdditionalNotes(String additionalNotes) {
        mAdditionalNotes = additionalNotes;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put(Constants.BOARD_TITLE, mTitle);
        result.put(Constants.BOARD_OWNER_EMAIL, getOwner());
        result.put(Constants.BOARD_DESCRIPTION, getDescription());
        result.put(Constants.BOARD_ADDITIONAL_NOTES, getAdditionalNotes());
        result.put(Constants.BOARD_NUMBER, getBoardNumber());
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mOwner);
        dest.writeString(mDescription);
        dest.writeString(mBoardNumber);
        dest.writeString(mAdditionalNotes);
    }
}
