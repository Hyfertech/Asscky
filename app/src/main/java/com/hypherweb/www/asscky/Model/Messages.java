package com.hypherweb.www.asscky.Model;

/**
 * Created by AirUnknown on 2016-10-24.
 */

public class Messages {

    String message;
    String owner;
    String mKey;
    String date;

    public Messages() {
    }


    public Messages(String message, String owner, String date) {
        this.message = message;
        this.owner = owner;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public String getOwner() {
        return owner;
    }

    public String getDate() {
        return date;
    }


//        public HashMap<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        HashMap<String, Object> message = new HashMap<>();
//        message.put(Constants.MESSAGE_TEXT, getMessage());
//        message.put(Constants.MESSAGE_OWNER, getOwner());
//        result.put(getKey(), message);
//        return result;
//    }
}
