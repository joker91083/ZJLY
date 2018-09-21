package com.otitan.login;

import android.databinding.ObservableField;

import java.io.Serializable;

public class LoginUser implements Serializable{

    private static final long serialVersionUID = -9002462996412994180L;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String name;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    private String password;
    private Boolean checked;

}
