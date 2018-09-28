package com.otitan.main.model;

import com.esri.arcgisruntime.data.Feature;

import java.io.Serializable;

public class MyFeature implements Serializable {

    private static final long serialVersionUID = 6231100848741685554L;

    public static Feature getFeature() {
        return feature;
    }

    public static void setFeature(Feature feature) {
        MyFeature.feature = feature;
    }

    private static Feature feature;

}
