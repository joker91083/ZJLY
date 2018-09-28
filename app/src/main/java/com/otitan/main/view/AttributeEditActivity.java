package com.otitan.main.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.otitan.base.BaseActivity;
import com.otitan.main.adapter.AttributeAdapter;
import com.otitan.main.model.Attribute;
import com.otitan.main.model.MyFeature;
import com.otitan.main.viewmodel.AttributeEditViewModel;
import com.otitan.model.MyLayer;
import com.otitan.ui.mview.IEditFeature;
import com.otitan.util.TitanItemDecoration;
import com.otitan.zjly.BR;
import com.otitan.zjly.R;
import com.otitan.zjly.databinding.FmAttributeBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class AttributeEditActivity extends BaseActivity<FmAttributeBinding, AttributeEditViewModel>
        implements IEditFeature {
    private AttributeEditViewModel viewmodel;
    private List<Attribute> items = new ArrayList<>();
    private MyFeature myFeature;
    private MyLayer myLayer;
    private AttributeAdapter adapter;

    @Override
    public int initContentView(@Nullable Bundle savedInstanceState) {
        return R.layout.fm_attribute;
    }

    @Override
    public int initVariableId() {
        return BR.viewmodel;
    }

    @NotNull
    @Override
    public AttributeEditViewModel initViewModel() {
        if (viewmodel == null) {
            viewmodel = new AttributeEditViewModel(this, this);
        }
        return viewmodel;
    }

    @Override
    public void initParam() {
        super.initParam();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            myLayer = (MyLayer) bundle.get("myLayer");
            myFeature = (MyFeature) bundle.get("feature");
            conversion(myFeature.getFeature().getAttributes());
        }
    }

    @Override
    public void initData() {
        super.initData();
        Toolbar toolbar = getBinding().toolbarAtt;
        toolbar.setTitle("属性编辑");
        this.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttributeEditActivity.this.finish();
            }
        });

        RecyclerView rvAtt = getBinding().rvAtt;
        LinearLayoutManager manager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        rvAtt.setLayoutManager(manager);
        rvAtt.addItemDecoration(new TitanItemDecoration(this, LinearLayoutManager.VERTICAL, 0));
        adapter = new AttributeAdapter(this, items, this);
        rvAtt.setAdapter(adapter);
    }

    @Override
    public void editFeature(@NonNull final Attribute att) {
        new MaterialDialog.Builder(this)
                .title(att.getName())
                .input("请输入属性值", att.getValue(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        Feature feature = myFeature.getFeature();
                        Map<String, Object> map = feature.getAttributes();
                        String name = att.getName();
                        String value = input.toString();
                        map.put(name, value);
                        Feature newFeature = myLayer.getTable().createFeature(map, feature.getGeometry());
                        attEdit(newFeature);
                    }
                })
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void attEdit(final Feature newFeature) {
        final ListenableFuture<Void> future = myLayer.getTable().updateFeatureAsync(newFeature);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                try {
                    future.get();
                    if (future.isDone()) {
                        //添加成功
                        Log.e("tag", "小班修改成功");
                        conversion(newFeature.getAttributes());
                        adapter.setData(items);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    Log.e("tag", "小班修改失败：" + e);
                }
            }
        });
    }

    private void conversion(Map<String, Object> att) {
        if (att == null) {
            return;
        }
        items.clear();
        for (String key : att.keySet()) {
            Attribute attribute = new Attribute();
            attribute.setName(key);
            if (att.get(key) == null || att.get(key).toString().trim().equals("")) {
                attribute.setValue("");
            } else {
                attribute.setValue(att.get(key).toString().trim());
            }
            items.add(attribute);
        }
    }
}
