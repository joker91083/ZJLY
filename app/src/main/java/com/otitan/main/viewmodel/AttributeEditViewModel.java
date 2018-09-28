package com.otitan.main.viewmodel;

import android.content.Context;

import com.otitan.base.BaseViewModel;
import com.otitan.ui.mview.IEditFeature;

public class AttributeEditViewModel extends BaseViewModel {

    private IEditFeature mView;

    public AttributeEditViewModel(Context context, IEditFeature mView) {
        setMContext(context);
        this.mView = mView;
    }

}
