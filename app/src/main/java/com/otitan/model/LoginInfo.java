package com.otitan.model;

import java.io.Serializable;

public class LoginInfo implements Serializable {

    private static final long serialVersionUID = -8800256994208284825L;
    private String username;
    private String password;
    private String grant_type = "password";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGrant_type() {
        return grant_type;
    }
}
