package com.otitan.main.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.otitan.base.BaseFragment;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class AttributeEditFragment extends BaseFragment<FmAttributeBinding, AttributeEditViewModel>
        implements IEditFeature {
    private AttributeEditViewModel viewmodel;
    private List<Attribute> items = new ArrayList<>();
    private MyFeature myFeature;
    private MyLayer myLayer;

    @Override
    public int initContentView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
            viewmodel = new AttributeEditViewModel(getActivity(), this);
        }
        return viewmodel;
    }

    @Override
    public void initParam() {
        super.initParam();
        Bundle bundle = getArguments();
        if (bundle != null) {
            myLayer = (MyLayer) bundle.get("myLayer");
            myFeature = (MyFeature) bundle.get("feature");
//            HashMap<String, Object> map = (HashMap<String, Object>) bundle.getSerializable("feature");
//            conversion(map);
            conversion(myFeature.getFeature().getAttributes());
        }
    }

    @Override
    public void initData() {
        super.initData();
        setHasOptionsMenu(true);
        Toolbar toolbar = getBinding().toolbarAtt;
        toolbar.setTitle("属性编辑");
        if (getActivity() != null) {
            final Activity activity = getActivity();
            ((AppCompatActivity) activity).setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.finish();
                }
            });
        }

        RecyclerView rvAtt = getBinding().rvAtt;
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
        rvAtt.setLayoutManager(manager);
        if (getActivity() != null) {
            rvAtt.addItemDecoration(new TitanItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, 0));
            AttributeAdapter adapter = new AttributeAdapter(getActivity(), items, this);
            rvAtt.setAdapter(adapter);
        }
    }

    @Override
    public void editFeature(@NonNull Attribute att) {
        new MaterialDialog.Builder(getActivity())
                .title(att.getName())
                .input("请输入属性值", att.getValue(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        Log.e("tag",input.toString());

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

    public void attEdit(Feature feature, MyLayer myLayer) {
        final ListenableFuture<Void> future = Objects.requireNonNull(MyLayer.getTable()).updateFeatureAsync(feature);
        future.addDoneListener(new Runnable() {
            @Override
            public void run() {
                try {
                    future.get();
                    if (future.isDone()) {
                        //添加成功
                        Log.e("tag", "小班修改成功");
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
