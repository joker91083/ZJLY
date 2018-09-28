package com.otitan.main.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

import com.otitan.base.BaseViewModel;
import com.otitan.main.model.Attribute;
import com.otitan.ui.mview.IEditFeature;

public class AttributeEditItemViewModel extends BaseViewModel {

    private IEditFeature mView;
    public ObservableField<Attribute> att = new ObservableField<>();

    public AttributeEditItemViewModel(Context context, IEditFeature mView) {
        setMContext(context);
        this.mView = mView;
    }

    public void editFeature() {
        mView.editFeature(att.get());
    }
}
