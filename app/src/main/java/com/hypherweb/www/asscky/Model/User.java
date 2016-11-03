package com.hypherweb.www.asscky.Model;

/**
 * Created by AirUnknown on 2016-11-02.
 */

public class User {

    String uuid;
    String email;

    public User() {
    }

    public User(String uuid, String email) {
        this.uuid = uuid;
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
