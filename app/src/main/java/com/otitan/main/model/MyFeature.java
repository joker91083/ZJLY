package com.otitan.main.model;

import com.esri.arcgisruntime.data.Feature;

import java.io.Serializable;

public class MyFeature implements Serializable {

    private static final long serialVersionUID = 6231100848741685554L;

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public MyFeature(Feature feature){
        this.feature = feature;
    }

    private Feature feature;

}
