package com.otitan.main.model;

import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureTable;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.layers.FeatureLayer;

import java.io.Serializable;
import java.util.Map;

/**
 * 小班修改撤销信息
 */
public class RepealInfo implements Serializable {

    private static final long serialVersionUID = 7492244532276134304L;
    //小班id
    private String objectid;
    //小班几何
    private Geometry geometry;
    //修改的小班
    private Feature feature;
    //修改类型 add添加 del删除 update修改
    private String type;
    //小班所在图层
    private FeatureLayer layer;
    //图层所在数据表
    private FeatureTable table;
    //小班属性集合
    private Map<String, Object> att;

    public String getObjectid() {
        return objectid;
    }

    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Map<String, Object> getAtt() {
        return att;
    }

    public void setAtt(Map<String, Object> att) {
        this.att = att;
    }
}
