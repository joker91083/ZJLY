package com.otitan.model;

import com.esri.arcgisruntime.data.FeatureTable;
import com.esri.arcgisruntime.layers.FeatureLayer;

import java.io.Serializable;

public class MyLayer implements Serializable {
    private static final long serialVersionUID = 4733695836140679276L;
    //gdb数据文件夹名称
    private String pName;
    //gdb文件名称
    private String cName;
    //gdb文件地址
    private String path;
    //gdb子图层名称
    private String lName;
    //子图层
    private FeatureLayer layer;
    private FeatureTable table;

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public FeatureLayer getLayer() {
        return layer;
    }

    public void setLayer(FeatureLayer layer) {
        this.layer = layer;
    }

    public FeatureTable getTable() {
        return table;
    }

    public void setTable(FeatureTable table) {
        this.table = table;
    }
}
