package com.otitan.main.model;

import java.io.Serializable;

public class Attribute implements Serializable{

    private static final long serialVersionUID = -8166332502512820271L;
    //小班字段名
    private String name;
    //字段值
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
