package com.otitan.main.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lling.photopicker.PhotoPickerActivity;
import com.otitan.TitanApplication;
import com.otitan.base.BaseFragment;
import com.otitan.main.adapter.Recyc_imageAdapter;
import com.otitan.main.model.UpInfo;
import com.otitan.main.viewmodel.IUpinfo;
import com.otitan.main.view.ImageBrowseActivity;
import com.otitan.main.viewmodel.UpinfoViewModel;
import com.otitan.permissions.PermissionsActivity;
import com.otitan.permissions.PermissionsChecker;
import com.otitan.util.Constant;
import com.otitan.zjly.BR;
import com.otitan.zjly.R;
import com.otitan.zjly.databinding.FmUpinfoBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 */
public class UpinfoFragment extends BaseFragment<FmUpinfoBinding,UpinfoViewModel>
        implements IUpinfo,Recyc_imageAdapter.PicOnclick{

    private UpinfoViewModel upinfoViewModel;

    @Override
    public void showSlectPic() {
        // 缺少权限时, 进入权限配置页面
        if (new PermissionsChecker(this.getActivity()).lacksPermissions(Constant.INSTANCE.getCAMERA())) {
            PermissionsActivity.startActivityForResult(this.getActivity(), PermissionsActivity.PERMISSIONS_REQUEST_CODE, Constant.INSTANCE.getCAMERA());
            return;
        }

        if (upinfoViewModel.picList.get().size()>0 && upinfoViewModel.picList.get().size()<9) {
            final Activity activity = this.getActivity();
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("重新选择会覆盖之前的图片");
            builder.setMessage("是否重新选择");
            builder.setCancelable(true);
            builder.setPositiveButton("重新选择", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(activity,PhotoPickerActivity.class);
                    intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);//是否显示相机
                    intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI);//选择模式（默认多选模式）
                    intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, PhotoPickerActivity.DEFAULT_NUM);//最大照片张数
                    startActivityForResult(intent, Constant.INSTANCE.getPICSEL());
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(16.0f);
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(16.0f);
        }

        if (upinfoViewModel.picList.get().size() == 0) {
            Intent intent = new  Intent(this.getActivity(), PhotoPickerActivity.class);
            intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);//是否显示相机
            intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI);//选择模式（默认多选模式）
            intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, PhotoPickerActivity.DEFAULT_NUM);//最大照片张数
            startActivityForResult(intent, Constant.INSTANCE.getPICSEL());
        }
    }

    @Override
    public void sure(UpInfo upinfo) {

    }

    @Override
    public void closeActivity() {
        getActivity().finish();
    }

    @Override
    public void setPicOnclick(View item, int position) {
        Intent intent = new Intent(this.getActivity(), ImageBrowseActivity.class);
        intent.putStringArrayListExtra("images", upinfoViewModel.picList.get());
        intent.putExtra("position", position);
        this.getActivity().startActivity(intent);
    }

    private static class Holder{
        private static final UpinfoFragment instance = new UpinfoFragment();
    }

    public static UpinfoFragment getInstance(){
        return Holder.instance;
    }

    @Override
    public int initContentView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fm_upinfo;
    }

    @Override
    public int initVariableId() {
        return BR.upinfoViewModel;
    }

    @NotNull
    @Override
    public UpinfoViewModel initViewModel() {
        if(upinfoViewModel == null){
            upinfoViewModel = UpinfoViewModel.getInstance(this.getContext(),this);
        }
        return upinfoViewModel;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Constant.INSTANCE.getPICSEL() && resultCode==-1){
            ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);

            upinfoViewModel.picList.get().clear();
            upinfoViewModel.picList.set(list);
            loadPhoto();
        }
    }
    /*选择照片后加载照片*/
    void loadPhoto(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.getBinding().upinfoRecyc.setLayoutManager(layoutManager);
        int length = TitanApplication.Companion.getInstances().getScreen().getWidthPixels()/4;
        Recyc_imageAdapter adapter = new Recyc_imageAdapter(this.getActivity(), upinfoViewModel.picList.get(),length);
        this.getBinding().upinfoRecyc.setAdapter(adapter);
        adapter.setPicOnclick(this);
    }

}
