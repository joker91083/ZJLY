package com.otitan.main.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureTable;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.GeometryType;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.PolylineBuilder;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.layers.OpenStreetMapLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.view.SketchCreationMode;
import com.esri.arcgisruntime.mapping.view.SketchEditor;
import com.esri.arcgisruntime.mapping.view.SketchStyle;
import com.esri.arcgisruntime.mapping.view.SpatialReferenceChangedEvent;
import com.esri.arcgisruntime.mapping.view.SpatialReferenceChangedListener;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.google.gson.Gson;
import com.otitan.TitanApplication;
import com.otitan.base.ContainerActivity;
import com.otitan.base.ValueCallBack;
import com.otitan.data.DataRepository;
import com.otitan.data.Injection;
import com.otitan.data.remote.RemoteDataSource;
import com.otitan.main.fragment.TrackManagerFragment;
import com.otitan.main.fragment.UpEventFragment;
import com.otitan.main.listener.ArcgisLocation;
import com.otitan.main.listener.GeometryChangedListener;
import com.otitan.main.model.ActionModel;
import com.otitan.main.model.Location;
import com.otitan.main.model.MainModel;
import com.otitan.main.model.TrackPoint;
import com.otitan.main.viewmodel.BootViewModel;
import com.otitan.main.viewmodel.CalloutViewModel;
import com.otitan.main.viewmodel.GeoViewModel;
import com.otitan.main.viewmodel.InitViewModel;
import com.otitan.main.viewmodel.SketchEditorViewModel;
import com.otitan.main.viewmodel.ToolViewModel;
import com.otitan.main.viewmodel.TrackManagerViewModel;
import com.otitan.model.MyLayer;
import com.otitan.ui.mview.IMap;
import com.otitan.ui.view.ImgManagerView;
import com.otitan.ui.view.LayerManagerView;
import com.otitan.ui.vm.LayerManagerViewModel;
import com.otitan.util.Constant;
import com.otitan.util.ConverterUtils;
import com.otitan.util.SpatialUtil;
import com.otitan.util.SymbolUtil;
import com.otitan.zjly.R;
import com.otitan.zjly.util.MaterialDialogUtil;
import com.titan.baselibrary.util.ToastUtil;
import com.otitan.zjly.databinding.ShareTckzBinding;
import com.titan.eventlibrary.EventActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MapCenterActivity extends AppCompatActivity implements ValueCallBack<Object>,
        ArcgisLocation, IMap, TrackManagerFragment.TrackManagerDialogListener {


    @BindView(R.id.mapview)
    MapView mapView;
    @BindView(R.id.share_isearch)
    ImageButton shareIsearch;

    @BindView(R.id.ib_distance)
    ImageButton ibDistance;
    @BindView(R.id.ib_sketch)
    ImageButton ibSketch;
    @BindView(R.id.ib_clean)
    ImageButton ibClean;
    @BindView(R.id.ib_location)
    ImageButton ibLocation;

    @BindView(R.id.tckz_imageview)
    TextView tckzImageview;
    @BindView(R.id.share_xtsz)
    TextView shareXtsz;
    @BindView(R.id.share_xcxxsb)
    TextView shareXcxxsb;
    @BindView(R.id.share_tcxr)
    TextView shareTcxr;
    @BindView(R.id.titleTextView)
    TextView titleTextView;
    @BindView(R.id.close_tuceng)
    AppCompatImageView closeTuceng;
    //    @BindView(R.id.cb_sl)
//    CheckBox cbSl;
//    @BindView(R.id.tile_extent)
//    ImageView tileExtent;
//    @BindView(R.id.cb_ys)
//    CheckBox cbYs;
//    @BindView(R.id.image_extent)
//    ImageView imageExtent;
//    @BindView(R.id.cb_dxt)
//    CheckBox cbDxt;
//    @BindView(R.id.dxt_extent)
//    ImageView dxtExtent;add
    @BindView(R.id.tckzExplv)
    ExpandableListView tckzExplv;
    @BindView(R.id.imgClose)
    AppCompatImageView imgClose;
    @BindView(R.id.rvImg)
    RecyclerView rvImg;
    @BindView(R.id.share_tckz)
    ImageButton shareTckz;
    @BindView(R.id.addfeature)
    RadioButton addFeature;

    /*include*/
    public View include_icTckz, include_img, include_edit;

    private SketchEditor sketchEditor;
    private ToolViewModel toolViewModel;
    private InitViewModel initViewModel;
    private CalloutViewModel calloutViewModel;
    private SketchEditorViewModel sketchEditorViewModel;
    private GeoViewModel geoViewModel;
    private BootViewModel bootViewModel;

    private OpenStreetMapLayer tiledLayer;
    private ArcGISTiledLayer gisTiledLayer;
    private ArrayList<MyLayer> layers = new ArrayList<>();
    //当前选择的矢量图层数据
    public static MyLayer myLayer;
    //当前选择的小班
    public static Feature feature;
    //已选中的小班
    public List<Feature> features = new ArrayList<>();
    private Location location;
    private ActionModel actionModel;
    private boolean isFirst = true;
    private DataRepository dataRepository;
    private String sbh;
    private LayerManagerView layerManagerView;
    public ShareTckzBinding binding;
    public ImgManagerView imgManager;
    //轨迹查询对话框
    private TrackManagerFragment mTrackManager;
    public SpatialReference spatialReference;
    //绘制图层
    public GraphicsOverlay mGraphicsOverlay;
    //轨迹状态 0查询轨迹
    int guijiState = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_center);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    void initView() {
        include_icTckz = findViewById(R.id.icTckz);
        include_img = findViewById(R.id.icImg);
        include_edit = findViewById(R.id.ic_feature_edit);
    }

    void initData() {

        sbh = TitanApplication.Companion.getInstances().getSbh();
        mapView.setAttributionTextVisible(false);
        mapView.addSpatialReferenceChangedListener(new SpatialReferenceChangedListener() {
            @Override
            public void spatialReferenceChanged(SpatialReferenceChangedEvent spatialReferenceChangedEvent) {
                spatialReference = spatialReferenceChangedEvent.getSource().getSpatialReference();
            }
        });

        SimpleMarkerSymbol mPointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.SQUARE, 0xFFFF0000, 20);
        SimpleLineSymbol mLineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, 0xFFFF8800, 4);
        SimpleFillSymbol mFillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.CROSS, 0x40FFA9A9, mLineSymbol);

        sketchEditor = new SketchEditor();
        SketchStyle sketchStyle = new SketchStyle();
        sketchStyle.setFeedbackVertexSymbol(mPointSymbol);
        sketchStyle.setLineSymbol(mLineSymbol);
        sketchStyle.setFillSymbol(mFillSymbol);
        sketchEditor.setSketchStyle(sketchStyle);

        sketchEditor.addGeometryChangedListener(new GeometryChangedListener(mapView, this));
        mapView.setSketchEditor(sketchEditor);
        initViewModel = InitViewModel.getInstance(this);
        toolViewModel = ToolViewModel.getInstance(this);
        calloutViewModel = CalloutViewModel.getInstance(this);
        geoViewModel = GeoViewModel.getInstance(this);
        bootViewModel = BootViewModel.getInstance(this, this);
        sketchEditorViewModel = new SketchEditorViewModel(mapView);

        //initViewModel.addTileLayer(mapView, this);
        tiledLayer = initViewModel.addOpenStreetMapLayer(mapView);
        location = gisLocation(mapView);
        mGraphicsOverlay = new GraphicsOverlay();
        mapView.getGraphicsOverlays().add(mGraphicsOverlay);

        layerManagerView = new LayerManagerView(this, this);
        LayerManagerViewModel managerViewModel = new LayerManagerViewModel(this, layerManagerView);
        layerManagerView.setViewModel(managerViewModel);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.share_tckz, null, false);
        binding.setViewmodel(managerViewModel);
//        include_icTckz = binding.getRoot();

        imgManager = new ImgManagerView(this, this);
        dataRepository = Injection.INSTANCE.provideDataRepository();

        mTrackManager = TrackManagerFragment.Companion.getInstance();
        TrackManagerViewModel model = new TrackManagerViewModel(mTrackManager, this);
        mTrackManager.setMViewModel(model);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            guijiState = bundle.getInt("guiji");
            if (guijiState == 0) {
                mTrackManager.show(getSupportFragmentManager(), "trackmanager");
            }
        }

    }


    @OnClick({R.id.share_isearch, R.id.ib_location, R.id.ib_clean, R.id.ib_distance,
            R.id.ib_sketch, R.id.share_xcxxsb, R.id.tckz_imageview, R.id.close_tuceng,
            R.id.addfeature, R.id.selectButton, R.id.xiubanButton, R.id.share_tcxr,
            R.id.imgClose})
    public void showInfo(View view) {
        switch (view.getId()) {
            case R.id.share_isearch:
                toolViewModel.cleanAllGraphics(mapView);
                toolViewModel.cleanSketch(mapView);
                actionModel = ActionModel.IQUERY;
                toolViewModel.showInfo(mapView);

                break;
            case R.id.ib_location:
                toolViewModel.myLocation(location);
                break;
            case R.id.ib_clean:
                toolViewModel.cleanAllGraphics(mapView);
                toolViewModel.cleanSketch(mapView);
                break;
            case R.id.ib_distance:
                toolViewModel.cleanAllGraphics(mapView);
                toolViewModel.cleanSketch(mapView);
                actionModel = ActionModel.DISTANCE;
                toolViewModel.distance(mapView);
                break;
            case R.id.ib_sketch:
                toolViewModel.cleanAllGraphics(mapView);
                toolViewModel.cleanSketch(mapView);
                actionModel = ActionModel.AREA;
                toolViewModel.area(mapView);
                break;
            case R.id.share_xcxxsb:
//                startActivity(EventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("lon", ConverterUtils.toString(location.getGpspoint().getX()));
                bundle.putString("lat", ConverterUtils.toString(location.getGpspoint().getY()));
                Intent intent = new Intent(this, ContainerActivity.class);
                intent.putExtra(ContainerActivity.BUNDLE, bundle);
                intent.putExtra(ContainerActivity.FRAGMENT, UpEventFragment.class.getCanonicalName());
                startActivity(intent);
                break;
            case R.id.tckz_imageview:
                bootViewModel.layerManger();
                break;
            case R.id.share_tcxr:
                bootViewModel.xbbj();
                break;
            case R.id.close_tuceng:
                layerManagerView.close();
                break;
            case R.id.imgClose:
                imgManager.close();
                break;
        }
    }

    @OnCheckedChanged({R.id.addfeature, R.id.addfeaturegb, R.id.copyButton, R.id.deleteButton,
            R.id.repealButton, R.id.hebingButton, R.id.qiegeButton, R.id.xiubanButton,
            R.id.attributButton, R.id.selectButton, R.id.removeButton})
    public void chedked(CompoundButton view, boolean ischanged) {
        if (!ischanged) {
            return;
        }
        clear();
        switch (view.getId()) {
            case R.id.addfeature:
                /*新增小班*/
                actionModel = ActionModel.ADDFEATURE;
                sketchEditorViewModel.setSketch(SketchCreationMode.FREEHAND_POLYGON);
                getMyLayer();
                break;
            case R.id.addfeaturegb:
                /*共边增班*/
                if (features.size() == 0) {
                    ToastUtil.setToast(this, "请选中一个小班进行共边增班");
                } else if (features.size() > 1) {
                    ToastUtil.setToast(this, "只能对一个小班进行共边增班");
                } else {
                    boolean flag = setMyLayer(features.get(0));
                    if (flag) {
                        actionModel = ActionModel.ADDFEATUREGB;
                        sketchEditorViewModel.setSketch(SketchCreationMode.FREEHAND_POLYGON);
                    }
                }
                break;
            case R.id.copyButton:
                /*小班复制*/
                if (features.isEmpty()) {
                    ToastUtil.setToast(this, "请选择要复制的小班");
                } else {
                    if (layers.size() > 0) {
                        actionModel = ActionModel.COPY;
                        choseLayer();
                    } else {
                        ToastUtil.setToast(this, "请先加载矢量图层");
                    }
                }
                break;
            case R.id.deleteButton:
                /*小班删除*/
                if (features.size() == 0) {
                    ToastUtil.setToast(this, "请选择要删除的小班");
                } else if (features.size() == 1) {
//                    sketchEditorViewModel.delFeature(myLayer, features.get(0), features);
                    delSureDialog(features.get(0));
                } else {
                    delFeatureDialog();
                }
                break;
            case R.id.repealButton:
                /*撤销*/
                sketchEditorViewModel.repealEdit();
                break;
            case R.id.hebingButton:
                /*合并*/
                if (features.size() < 2) {
                    ToastUtil.setToast(this, "请选择两个或以上的小班进行合并");
                } else {
                    boolean flag = checkFeature(features);
                    if (flag) {
                        hebingFeature();
                    } else {
                        ToastUtil.setToast(this, "合并的小班必须处于同一图层");
                    }
                }
                break;
            case R.id.qiegeButton:
                /*分割*/
                if (features.size() == 0) {
                    ToastUtil.setToast(this, "请选择一个小班进行分割");
                } else if (features.size() > 1) {
                    ToastUtil.setToast(this, "只能对一个小班进行分割");
                } else {
                    boolean flag = setMyLayer(features.get(0));
                    if (flag) {
                        actionModel = ActionModel.QIEGE;
                        sketchEditorViewModel.setSketch(SketchCreationMode.FREEHAND_LINE);
                    }
                }
                break;
            case R.id.xiubanButton:
                /*修班*/
                if (features.size() == 0) {
                    ToastUtil.setToast(this, "请选中一个小班面进行修班操作");
                } else if (features.size() > 1) {
                    ToastUtil.setToast(this, "只能对一个小班面进行修班操作");
                } else {
                    boolean flag = setMyLayer(features.get(0));
                    if (flag) {
                        actionModel = ActionModel.XIUBIAN;
                        sketchEditorViewModel.setSketch(SketchCreationMode.FREEHAND_LINE);
                    }
                }
                break;
            case R.id.attributButton:
                /*属性编辑*/
                if (features.size() == 1) {
                    feature = features.get(0);
                    toAttEdit(features.get(0));
                } else if (features.size() > 1) {
                    editAttFeatureDialog();
                } else {
                    ToastUtil.setToast(this, "请选择小班");
                }
                break;
            case R.id.selectButton:
                /*选择小班*/
                actionModel = ActionModel.SELECT;
                sketchEditorViewModel.setSketch(SketchCreationMode.FREEHAND_POLYGON);
                break;
            case R.id.removeButton:
                /*平移*/
                break;
        }
    }


    /**
     * 跳转属性编辑页面
     */
    private void toAttEdit(Feature feature) {
        boolean flag = setMyLayer(feature);
        if (!flag) {
            return;
        }
        Intent intent = new Intent(this, AttributeEditActivity.class);
        this.startActivity(intent);
    }

    private void clear() {
        toolViewModel.cleanAllGraphics(mapView);
        toolViewModel.cleanSketch(mapView);
        actionModel = ActionModel.NULL;
    }

    @Override
    public void onSuccess(Object o) {

    }

    @Override
    public void onFail(@NotNull String code) {

    }

    /*activity跳转*/
    private void startActivity(Class<?> clz) {
        Intent intent = new Intent(MapCenterActivity.this, clz);
        intent.putExtra("lon", ConverterUtils.toDouble(location.getGpspoint().getX()));
        intent.putExtra("lat", ConverterUtils.toDouble(location.getGpspoint().getY()));
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public Location gisLocation(final MapView mapView) {
        final Location location = new Location();

        final LocationDisplay display = mapView.getLocationDisplay();
        display.addLocationChangedListener(new LocationDisplay.LocationChangedListener() {
            @Override
            public void onLocationChanged(LocationDisplay.LocationChangedEvent event) {
                Point point = event.getLocation().getPosition();
                if (point != null) {
                    location.setGpspoint(point);
                }

                if (isFirst) {
                    mapView.setViewpointCenterAsync(point, Constant.INSTANCE.getDefalutScale());
                    isFirst = false;
                }

                Point map = display.getMapLocation();
                if (map != null) {
                    location.setMappoint(map);
                }

                addPoint(location);
            }
        });
        display.startAsync();
        location.setMapView(mapView);
        return location;
    }

    /**
     * 获取要编辑的矢量图数据
     */
    public void getMyLayer() {
        if (layers.isEmpty()) {
            sketchEditor.clearGeometry();
            sketchEditor.stop();
            ToastUtil.setToast(this, "请先加载矢量图层");
        } else if (layers.size() == 1) {
            myLayer = layers.get(0);
        } else {
            choseLayer();
        }
    }

    /**
     * 多图层选择
     */
    public void choseLayer() {
        List<String> layerNames = new ArrayList<>();
        for (MyLayer layer : layers) {
            layerNames.add(layer.getpName() + "-" + layer.getlName());
        }
        MaterialDialog dialog = MaterialDialogUtil.showSingleSelectionDialog(this, "图层选择", layerNames,
                new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        myLayer = layers.get(position);
                        if (actionModel == ActionModel.COPY) {
                            for (Feature f : features) {
                                if (myLayer.getTable().getGeometryType() == f.getGeometry().getGeometryType()) {
                                    sketchEditorViewModel.addFeature(myLayer, f.getGeometry(), f.getAttributes());
                                }
                            }
                        }
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }

    /**
     * 小班属性编辑
     */
    public void editAttFeatureDialog() {
        List<String> featureName = new ArrayList<>();
        for (Feature feature : features) {
            String id = feature.getAttributes().get("objectid").toString();
            String name = feature.getFeatureTable().getTableName();
            featureName.add(name + " objectid:" + id);
        }
        MaterialDialog dialog = MaterialDialogUtil.showSingleSelectionDialog(this, "小班选择", featureName,
                new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        Feature feature = features.get(position);
                        MapCenterActivity.feature = feature;
                        toAttEdit(feature);
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }

    /**
     * 删除小班
     */
    public void delFeatureDialog() {
        List<String> ids = new ArrayList<>();
        for (Feature feature : features) {
            String id = feature.getAttributes().get("objectid").toString();
            ids.add("object:" + id);
        }
        MaterialDialog dialog = MaterialDialogUtil.showSingleSelectionDialog(this, "小班删除", ids,
                new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        Feature feature = features.get(position);
                        delSureDialog(feature);
                    }
                });
        dialog.show();
    }

    /**
     * 删除小班确认提示弹窗
     *
     * @param feature 要删除的小班
     */
    public void delSureDialog(final Feature feature) {
        MaterialDialogUtil.showSureDialog(this, "是否确定删除小班", new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                boolean flag = setMyLayer(feature);
                if (flag) {
                    sketchEditorViewModel.delFeature(myLayer, feature, features);
                }
                dialog.dismiss();
            }
        }).show();
    }

    /**
     * 小班合并
     */
    public void hebingFeature() {
        List<String> ids = new ArrayList<>();
        for (Feature feature : features) {
            String id = feature.getAttributes().get("objectid").toString();
            ids.add("object:" + id);
        }
        feature = null;
        MaterialDialog dialog = new MaterialDialog.Builder(this).title("小班合并")
                .content("请选择合并之后要保留的小班属性").items(ids).itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        feature = features.get(position);
                        mGraphicsOverlay.getGraphics().clear();
                        Geometry geometry = feature.getGeometry();
                        Graphic graphic = new Graphic(geometry, SymbolUtil.risk5_Symbol);
                        mGraphicsOverlay.getGraphics().add(graphic);
                        mapView.setViewpointGeometryAsync(features.get(position).getGeometry());
                    }
                }).positiveText("确定").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (feature == null) {
                            ToastUtil.setToast(dialog.getContext(), "请选择要保留的小班");
                            return;
                        }
                        boolean flag = setMyLayer(feature);
                        if (flag) {
                            sketchEditorViewModel.hebing(myLayer, features, feature);
                            mGraphicsOverlay.getGraphics().clear();
                        }
                    }
                }).negativeText("取消").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mGraphicsOverlay.getGraphics().clear();
                        dialog.dismiss();
                    }
                }).autoDismiss(false).build();
        dialog.show();
    }

    /**
     * 检查小班是否都处于同一个表
     *
     * @param features
     * @return
     */
    public boolean checkFeature(List<Feature> features) {
        boolean flag = true;
        FeatureTable table = features.get(0).getFeatureTable();
        for (Feature f : features) {
            if (f.getFeatureTable() != table) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private boolean setMyLayer(Feature feature) {
        String tableName = feature.getFeatureTable().getTableName();
        myLayer = null;
        boolean flag = false;
        for (MyLayer layer : layers) {
            if (layer.getlName().equals(tableName)) {
                flag = true;
                myLayer = layer;
                break;
            }
        }
        if (!flag) {
            ToastUtil.setToast(MapCenterActivity.this, "没有获取到图层数据");
        }
        return flag;
    }

    @Override
    protected void onStart() {
        super.onStart();
        isFirst = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isFirst = true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isFirst = true;
    }

    /**
     * 上传轨迹点
     *
     * @param location
     */
    void addPoint(Location location) {
        final String lon = ConverterUtils.toString(location.getGpspoint().getX());
        final String lat = ConverterUtils.toString(location.getGpspoint().getY());
        String auth = TitanApplication.Companion.getSharedPreferences().getString("auth", null);
        if (auth == null) {
            ToastUtil.setToast(this, "登录信息验证失败");
            return;
        }
        auth = "Bearer " + auth;
        Gson gson = new Gson();
        TrackPoint trackPoint = new TrackPoint();
        trackPoint.setLat(lat);
        trackPoint.setLon(lon);
        trackPoint.setSbh(sbh);
        String json = gson.toJson(trackPoint);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        dataRepository.upTrack(auth, body, new RemoteDataSource.mCallback() {
            @Override
            public void onFailure(@NotNull String info) {
                dataRepository.addLocalPoint(lon, lat, sbh, "0");
            }

            @Override
            public void onSuccess(@NotNull Object result) {
                dataRepository.addLocalPoint(lon, lat, sbh, "1");
                mapView.getMap().getBasemap().getBaseLayers().remove(new ArcGISMap());
            }
        });
//        dataRepository.addPointToServer(auth, lon, lat, sbh, new ValueCallBack<Object>() {
//            @Override
//            public void onSuccess(Object o) {
//                dataRepository.addLocalPoint(lon, lat, sbh, "1");
//            }
//
//            @Override
//            public void onFail(@NotNull String code) {
//                dataRepository.addLocalPoint(lon, lat, sbh, "0");
//            }
//
//            @Override
//            public void onGeometry(@NotNull Geometry geometry) {
//
//            }
//        });

    }

    @Override
    public void drawTrackLine(@NotNull List<? extends TrackPoint> list) {
        PointCollection points = new PointCollection(spatialReference);
        for (TrackPoint tp : list) {
            Point point;
//            if (mapView.getSpatialReference() != null && mapView.getSpatialReference().getWkid() == 4326) {
            if (spatialReference != null && spatialReference.getWkid() == 4326) {
                point = (Point) GeometryEngine.project(new Point(Double.parseDouble(tp.getLon()),
                                Double.parseDouble(tp.getLat()), SpatialUtil.Companion.getSpatialWgs4326()),
                        spatialReference);
            } else {
                point = new Point(Double.parseDouble(tp.getLon()), Double.parseDouble(tp.getLat()));
            }
            if (!point.isEmpty()) {
                points.add(point);
            }
        }
        if (points.size() > 0) {
            PolylineBuilder polyline = new PolylineBuilder(points);
            if (polyline.isSketchValid()) {
                Graphic graphic = new Graphic(polyline.toGeometry(), SymbolUtil.measureline);
                mGraphicsOverlay.getGraphics().add(graphic);
                mapView.setViewpointGeometryAsync(graphic.getGeometry());
            }
        } else {
            ToastUtil.setToast(this, "未查询到轨迹点");
        }
    }

    @Override
    public void dismiss() {
        mTrackManager.dismiss();
    }

    @Override
    public void showTckz() {
        include_icTckz.setVisibility(View.VISIBLE);
        layerManagerView.initView();
    }

    @Override
    public void showXbbj() {
        if (include_edit != null) {
            if (include_edit.getVisibility() == View.VISIBLE) {
                include_edit.setVisibility(View.GONE);
            } else {
                include_edit.setVisibility(View.VISIBLE);
            }
        }
    }

    @Nullable
    @Override
    public OpenStreetMapLayer getOpenStreetLayer() {
        return tiledLayer;
    }

    @Override
    public void setSpatial(@NotNull SpatialReference spatialReference) {
        this.spatialReference = spatialReference;
    }

    @NotNull
    @Override
    public ArrayList<MyLayer> getLayers() {
        return layers;
    }

    @Nullable
    @Override
    public ArcGISTiledLayer getTiledLayer() {
        return gisTiledLayer;
    }

    @Override
    public void onGeometry(@NotNull Geometry geometry) {

        toolViewModel.cleanAllGraphics(mapView);
        if (!GeometryEngine.isSimple(geometry)) {
            geometry = GeometryEngine.simplify(geometry);
        }

        if (actionModel == ActionModel.IQUERY) {
            /*属性查询*/
            toolViewModel.iquery(mapView, geometry, calloutViewModel);
        } else if (actionModel == ActionModel.DISTANCE) {
            if (GeometryType.POLYLINE == geometry.getGeometryType()) {
                Point point = geometry.getExtent().getCenter();
                double length = Math.abs(GeometryEngine.length((Polyline) geometry));
                calloutViewModel.showValueInmap(mapView, point, length, " 米");
            }
        } else if (actionModel == ActionModel.AREA) {
            Point point = geometry.getExtent().getCenter();
            if (GeometryType.POLYGON == geometry.getGeometryType()) {
                double area = Math.abs(GeometryEngine.area((Polygon) geometry));
                calloutViewModel.showValueInmap(mapView, point, area, " 平方米");
            }
        } else if (actionModel == ActionModel.ADDFEATURE) {
            /*新增小班*/
            sketchEditorViewModel.addFeature(myLayer, geometry, null);
        } else if (actionModel == ActionModel.ADDFEATUREGB) {
            /*共边增班*/
            sketchEditorViewModel.addFeatureGb(myLayer, geometry, features.get(0));
        } else if (actionModel == ActionModel.QIEGE) {
            /*切割*/
            sketchEditorViewModel.qiege(myLayer, geometry, features.get(0));
        } else if (actionModel == ActionModel.XIUBIAN) {
            /*修班*/
            sketchEditorViewModel.editor(myLayer, geometry, features.get(0));
        } else if (actionModel == ActionModel.SELECT) {
            /*小班选择*/
            features.clear();
            for (MyLayer myLayer : layers) {
                sketchEditorViewModel.queryFeature(myLayer, geometry, features);
            }
        }

    }
}
