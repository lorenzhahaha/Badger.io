package com.brogrammers.badger.io.Model;

/**
 * Created by Lorenz-PC on 3/23/2018.
 */

public class Users {
    private String email, status;

    public Users() {

    }

    public Users(String email, String status) {
        this.email = email;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }
}
