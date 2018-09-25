package com.otitan.main.viewmodel;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.otitan.model.MyLayer;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class SketchEditorViewModel {


    /*添加小班*/
    public void addFeature(MyLayer myLayer,Geometry geometry){

        Feature feature = Objects.requireNonNull(myLayer.getTable()).createFeature();
        feature.setGeometry(geometry);
        final ListenableFuture<Void> result = myLayer.getTable().addFeatureAsync(feature);
        result.addDoneListener(new Runnable() {
            @Override
            public void run() {
                try {
                    result.get();
                    if(result.isDone()){
                        //添加成功
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*修班保存*/
    public void editor(MyLayer myLayer,Geometry geometry,Feature feature){



    }




}
