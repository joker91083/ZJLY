package com.otitan.main.adapter;

import android.content.Context;

import com.otitan.base.BaseAdapter;
import com.otitan.main.model.Attribute;
import com.otitan.main.viewmodel.AttributeEditItemViewModel;
import com.otitan.ui.mview.IEditFeature;
import com.otitan.zjly.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AttributeAdapter extends BaseAdapter {
    private Context context;
    private List<Attribute> items;
    private IEditFeature mView;

    public void setData(List<Attribute> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    public AttributeAdapter(Context context, List<Attribute> items, IEditFeature mView) {
        this.context = context;
        this.items = items;
        this.mView = mView;
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.item_attribute;
    }

    @NotNull
    @Override
    protected Object getLayoutViewModel(int position) {
        AttributeEditItemViewModel viewModel = new AttributeEditItemViewModel(context, mView);
        viewModel.att.set(items.get(position));
        return viewModel;
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }
}
