package com.zlb.memo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.PolylineOptions;
import com.haozhang.lib.SlantedTextView;

import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.coorchice.library.SuperTextView;
import com.vise.log.ViseLog;
import com.vise.xsnow.event.IEvent;
import com.vise.xsnow.event.Subscribe;
import com.vondear.rxtools.RxActivityTool;
import com.vondear.rxtools.RxDataTool;
import com.vondear.rxtools.view.RxToast;
import com.zlb.memo.R;
import com.zlb.memo.activity.base.BaseActivity;
import com.zlb.memo.adapter.refeshRecycle.BaseRefreshRecyclerAdapter;
import com.zlb.memo.adapter.refeshRecycle.BaseRefreshViewHolder;
import com.zlb.memo.api.XSnowHelper;
import com.zlb.memo.bean.MyPoiItem;
import com.zlb.memo.bean.trace.PointsUtil;
import com.zlb.memo.bean.trace.SmoothMarker;
import com.zlb.memo.eventmodel.PublishEvent;
import com.zlb.memo.listener.IDataRequestListener;
import com.zlb.memo.overall.C;
import com.zlb.memo.utils.SoftHideKeyBoardUtil;
import com.zlb.memo.utils.StatusBarUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

/**
 * Created by Administrator on 2017/12/5.
 */

public class PublishPingYouActivity extends BaseActivity implements AMap.OnMapClickListener, PoiSearch.OnPoiSearchListener,
        AMap.OnMarkerClickListener, AMap.InfoWindowAdapter, TextWatcher, View.OnClickListener, Inputtips.InputtipsListener
        , IDataRequestListener, AMap.OnMapScreenShotListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_confirm_save)
    TextView tvConfirmSave;
    @BindView(R.id.map_map)
    MapView mapMap;
    @BindView(R.id.et_search)
    AutoCompleteTextView etSearch;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    @BindView(R.id.rl_map)
    RelativeLayout rlMap;
    @BindView(R.id.rc_search_result)
    RecyclerView rcSearchResult;
    @BindView(R.id.rc_select)
    RecyclerView rcSelect;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.iv_slide)
    ImageView ivSlide;
    @BindView(R.id.textView8)
    TextView textView8;
    @BindView(R.id.handle)
    LinearLayout handle;
    @BindView(R.id.content)
    LinearLayout content;
    @BindView(R.id.slidingdrawer)
    SlidingDrawer slidingdrawer;
    @BindView(R.id.rl_select)
    RelativeLayout rlSelect;
    @BindView(R.id.img_yulan)
    ImageView imgYulan;
    @BindView(R.id.tv_next)
    TextView imgNext;

    private AMap aMap;
    private Marker marker;
    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;
    private List<MyPoiItem> poiItems = new ArrayList<>();// poi数据
    private ArrayList<MyPoiItem> selectpoiItems = new ArrayList<>();// poi数据
    private List<LatLng> pathPoints = new ArrayList<>();
    private SmoothMarker smoothMarker;
    private PolylineOptions po;

    BaseRefreshRecyclerAdapter searchap;
    BaseRefreshRecyclerAdapter selectap;
    String UUID;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_publish_pingyou);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hineTypewriting();
                finish();
            }
        });
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, toolbar);
        SoftHideKeyBoardUtil.assistActivity(this);
        initView();

        mapMap.onCreate(bundle);// 此方法必须重写
        aMap = mapMap.getMap();
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.setOnMapClickListener(this);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(C.latLng, 14));
        etSearch.addTextChangedListener(this);// 添加文本输入框监听事件
        doSearchQuery(new LatLonPoint(C.latLng.latitude, C.latLng.longitude));
        UUID = java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Override
    protected boolean isRegisterEvent() {
        return true;
    }

    private void initView() {
        LinearLayoutManager li1 = new LinearLayoutManager(this);
        rcSearchResult.setLayoutManager(li1);
        rcSearchResult.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        searchap = new BaseRefreshRecyclerAdapter<MyPoiItem>(poiItems, R.layout.item_publish_search_result) {
            @Override
            protected void onBindViewHolder(BaseRefreshViewHolder holder, final MyPoiItem model, final int position) {
                TextView tv_title = holder.getView(R.id.tv_title);
                TextView tv_detail = holder.getView(R.id.tv_detail);
                final ImageView img_select = holder.getView(R.id.img_select);
                tv_title.setText(model.getPoiItem().getTitle());
                tv_detail.setText("" + model.getPoiItem().getSnippet());
                img_select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < poiItems.size(); i++) {
                            poiItems.get(i).setSelected(false);
                            selectpoiItems.remove(poiItems.get(i));
                        }
                        if (model.isSelected()) {
                            model.setSelected(false);
                            selectpoiItems.remove(model);
                        } else {
                            model.setSelected(true);
                            selectpoiItems.add(model);
                        }
                        refreshView();
                    }
                });
                if (model.isSelected()) {
                    img_select.setBackgroundResource(R.drawable.qmui_icon_checkbox_checked);
                } else {
                    img_select.setBackgroundResource(R.drawable.qmui_icon_checkbox_normal);
                }
            }
        };
        rcSearchResult.setAdapter(searchap);
        searchap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    marker.remove();
                } catch (Exception e) {
                }
                try {
                    marker = aMap.addMarker(new MarkerOptions().position(new LatLng(poiItems.get(position).getPoiItem().getLatLonPoint().getLatitude(),
                            poiItems.get(position).getPoiItem().getLatLonPoint().getLongitude())));
                    aMap.animateCamera(CameraUpdateFactory.changeLatLng(new LatLng(poiItems.get(position).getPoiItem().getLatLonPoint().getLatitude(),
                            poiItems.get(position).getPoiItem().getLatLonPoint().getLongitude())));
                } catch (Exception e) {
                }
            }
        });

        LinearLayoutManager li2 = new LinearLayoutManager(this);
        li2.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcSelect.setLayoutManager(li2);
        selectap = new BaseRefreshRecyclerAdapter<MyPoiItem>(poiItems, R.layout.item_publish_pingyou_select) {
            @Override
            protected void onBindViewHolder(BaseRefreshViewHolder holder, MyPoiItem model, int position) {
                TextView tv_title = holder.getView(R.id.tv_title);
                SlantedTextView tv_detail = holder.getView(R.id.tv_detail);
                SuperTextView tv_add_num = holder.getView(R.id.tv_add_num);
                int pos = position + 1;
                tv_add_num.setText("" + pos);
                tv_title.setText(model.getPoiItem().getTitle());
                int r = new Random().nextInt(100);
                if (r % 2 == 0) {
                    tv_detail.setVisibility(View.GONE);
                } else {
                    tv_detail.setVisibility(View.VISIBLE);
                }
            }
        };
        rcSelect.setAdapter(selectap);
        selectap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (marker != null) {
                    marker.remove();
                }
                marker = aMap.addMarker(
                        new MarkerOptions()
                                .position(pathPoints.get(position))
                                .title(selectpoiItems.get(position).getPoiItem().getTitle()));
                marker.showInfoWindow();// 设置默认显示一个infowinfow
                aMap.animateCamera(CameraUpdateFactory.changeLatLng(pathPoints.get(position)));
            }
        });

        slidingdrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                ivSlide.setImageResource(R.drawable.normal_xiahua);
            }
        });
        slidingdrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                ivSlide.setImageResource(R.drawable.normal_shanghua);
            }
        });
        slidingdrawer.setOnDrawerScrollListener(new SlidingDrawer.OnDrawerScrollListener() {
            @Override
            public void onScrollEnded() {
            }

            @Override
            public void onScrollStarted() {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showIsEmpty() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //隐藏输入框
    private void hineTypewriting() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @OnClick({R.id.img_search, R.id.img_edit, R.id.img_yulan, R.id.tv_next, R.id.tv_confirm_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                if (selectpoiItems.size() > 0) {
                    new AsyncTask<Object, Integer, Object>() {

                        @Override
                        protected Object doInBackground(Object... params) {
                            uplpadPoi2();
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object object) {
                            Bundle b = new Bundle();
                            b.putString("UUID", UUID);
                            RxActivityTool.skipActivity(PublishPingYouActivity.this, PublishPingYouDetailsActivity.class, b);
                        }
                    }.execute();
                } else {
                    RxToast.error("至少选择一个地点才能进行下一步哦！");
                }
                break;
            case R.id.img_yulan:
                if (selectpoiItems.size() > 1) {
                    move();
                } else {
                    RxToast.error("至少选择两个点才能预览线路哦！");
                }
                break;
            case R.id.tv_confirm_save:
                if (selectpoiItems.size() > 0) {
                    uplpadPoi();
                } else {
                    RxToast.error("至少选择一个点才能保存线路哦！");
                }
                break;
            case R.id.img_search:
                if (RxDataTool.isEmpty(etSearch.getText().toString())) {
                    RxToast.error("请输入搜索关键字");
                    return;
                } else {
                    doSearchQuery();
                }
                break;
            case R.id.img_edit:
                Bundle b = new Bundle();
                b.putParcelableArrayList("SELECTPOINTS", selectpoiItems);
                RxActivityTool.skipActivityForResult(this, PublishPingYouEditActivity.class, b, 1000);
                break;
        }
    }

    /**
     */
    private Subscription subsLoading;

    private void uplpadPoi() {
        showLoading("请稍等");
        subsLoading = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                cutMap(subscriber);
            }
        })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())//生产事件在io
                .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        b = false;
                        dismissLoading();
                        try {
                            for (int i = 0; i < selectpoiItems.size(); i++) {
                                Map<String, RequestBody> map = new HashMap<>();
                                map.put("userId", RequestBody.create(MediaType.parse("text/plan"), C.user.getUserId()));
                                map.put("index", RequestBody.create(MediaType.parse("text/plan"), String.valueOf(i)));
                                map.put("pingyouId", RequestBody.create(MediaType.parse("text/plan"), UUID));
                                map.put("lat", RequestBody.create(MediaType.parse("text/plan"), String.valueOf(selectpoiItems.get(i).getPoiItem().getLatLonPoint().getLatitude())));
                                map.put("lon", RequestBody.create(MediaType.parse("text/plan"), String.valueOf(selectpoiItems.get(i).getPoiItem().getLatLonPoint().getLongitude())));
                                map.put("distance", RequestBody.create(MediaType.parse("text/plan"), String.valueOf(RxDataTool.getRoundUp(distance, 2))));
                                map.put("provence", RequestBody.create(MediaType.parse("text/plan"), selectpoiItems.get(i).getPoiItem().getProvinceName()));
                                map.put("city", RequestBody.create(MediaType.parse("text/plan"), selectpoiItems.get(i).getPoiItem().getCityName()));
                                map.put("zone", RequestBody.create(MediaType.parse("text/plan"), selectpoiItems.get(i).getPoiItem().getAdName()));
                                map.put("keyword", RequestBody.create(MediaType.parse("text/plan"), selectpoiItems.get(i).getPoiItem().getTitle()));
                                map.put("address", RequestBody.create(MediaType.parse("text/plan"), selectpoiItems.get(i).getPoiItem().getSnippet()));
                                map.put("gdImage", RequestBody.create(MediaType.parse("text/plan"), ""));
                                map.put("viewPointContentId", RequestBody.create(MediaType.parse("text/plan"), ""));
                                map.put("haveViewPoint", RequestBody.create(MediaType.parse("text/plan"), String.valueOf(0)));
                                File file = new File(Environment.getExternalStorageDirectory() + "/" + UUID + ".jpeg");
                                map.put("file" + 0 + "\"; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                                XSnowHelper.publishPoi(map, PublishPingYouActivity.this);
                            }
                            RxToast.success("保存草稿成功，可在我的“拼游草稿”中查看编辑！");
                        } catch (Exception e) {
                            ViseLog.e(e);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                        RxToast.error(e.getMessage());
                    }

                    @Override
                    public void onNext(String text) {
                    }
                });
    }

    private void uplpadPoi2() {
        subsLoading = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                cutMap(subscriber);
            }
        })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())//生产事件在io
                .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        b = false;
                        try {
                            for (int i = 0; i < selectpoiItems.size(); i++) {
                                Map<String, RequestBody> map = new HashMap<>();
                                map.put("userId", RequestBody.create(MediaType.parse("text/plan"), C.user.getUserId()));
                                map.put("index", RequestBody.create(MediaType.parse("text/plan"), String.valueOf(i)));
                                map.put("pingyouId", RequestBody.create(MediaType.parse("text/plan"), UUID));
                                map.put("lat", RequestBody.create(MediaType.parse("text/plan"), String.valueOf(selectpoiItems.get(i).getPoiItem().getLatLonPoint().getLatitude())));
                                map.put("lon", RequestBody.create(MediaType.parse("text/plan"), String.valueOf(selectpoiItems.get(i).getPoiItem().getLatLonPoint().getLongitude())));
                                map.put("distance", RequestBody.create(MediaType.parse("text/plan"), String.valueOf(RxDataTool.getRoundUp(distance, 2))));
                                map.put("provence", RequestBody.create(MediaType.parse("text/plan"), selectpoiItems.get(i).getPoiItem().getProvinceName()));
                                map.put("city", RequestBody.create(MediaType.parse("text/plan"), selectpoiItems.get(i).getPoiItem().getCityName()));
                                map.put("zone", RequestBody.create(MediaType.parse("text/plan"), selectpoiItems.get(i).getPoiItem().getAdName()));
                                map.put("keyword", RequestBody.create(MediaType.parse("text/plan"), selectpoiItems.get(i).getPoiItem().getTitle()));
                                map.put("address", RequestBody.create(MediaType.parse("text/plan"), selectpoiItems.get(i).getPoiItem().getSnippet()));
                                map.put("gdImage", RequestBody.create(MediaType.parse("text/plan"), ""));
                                map.put("viewPointContentId", RequestBody.create(MediaType.parse("text/plan"), ""));
                                map.put("haveViewPoint", RequestBody.create(MediaType.parse("text/plan"), String.valueOf(0)));
                                File file = new File(Environment.getExternalStorageDirectory() + "/" + UUID + ".jpeg");
                                map.put("file" + 0 + "\"; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                                XSnowHelper.publishPoi(map, PublishPingYouActivity.this);
                            }
                        } catch (Exception e) {
                            ViseLog.e(e);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        RxToast.error(e.getMessage());
                    }

                    @Override
                    public void onNext(String text) {
                    }
                });
    }

    /**
     */
    protected void cutMap(Subscriber<? super String> subscriber) {
        try {
            aMap.getMapScreenShot(PublishPingYouActivity.this);
            while (!b) {
                subscriber.onNext("");
            }
            subscriber.onCompleted();
            b = false;
        } catch (Exception e) {
            e.printStackTrace();
            subscriber.onError(e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                selectpoiItems = data.getExtras().getParcelableArrayList("SELECTPOINTS");
                refreshView();
                break;
        }
    }

    private void refreshView() {
        selectap.refresh(selectpoiItems);
        rcSelect.scrollToPosition(selectpoiItems.size() - 1);
        searchap.notifyDataSetChanged();
        if (selectpoiItems.size() > 0) {
            imgYulan.setVisibility(View.VISIBLE);
            imgNext.setVisibility(View.VISIBLE);
            rlSelect.setVisibility(View.VISIBLE);
            pathPoints.clear();
            for (int i = 0; i < selectpoiItems.size(); i++) {
                pathPoints.add(new LatLng(selectpoiItems.get(i).getPoiItem().getLatLonPoint().getLatitude(),
                        selectpoiItems.get(i).getPoiItem().getLatLonPoint().getLongitude()));
            }
            addPolylineInPlayGround();
        } else {
            imgYulan.setVisibility(View.GONE);
            imgNext.setVisibility(View.GONE);
            rlSelect.setVisibility(View.GONE);
        }
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        currentPage = 0;
        query = new PoiSearch.Query(etSearch.getText().toString(), "", "合肥");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
        showLoading("加载中");
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(LatLonPoint lp) {
        currentPage = 0;
        query = new PoiSearch.Query("", "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        if (lp != null) {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(lp, 1000, true));//
            // 设置搜索区域为以lp点为圆心，其周围5000米范围
            poiSearch.searchPOIAsyn();// 异步搜索
        }
        showLoading("加载中");
    }

    @Override
    public void onPoiSearched(PoiResult result, int rcode) {
        if (rcode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    poiItems.clear();
                    for (int i = 0; i < poiResult.getPois().size(); i++) {
                        poiItems.add(new MyPoiItem(false, poiResult.getPois().get(i)));// 取得第一页的poiitem数据，页数从数字0开始
                    }
                    List<SuggestionCity> suggestionCities = poiResult.getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                    if (poiItems != null && poiItems.size() > 0) {
                        searchap.refresh(poiItems);
                        slidingdrawer.toggle();
                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                    }
                }
            } else {
                RxToast.info("暂无数据");
            }
        }
        dismissLoading();
        etSearch.setText("");
    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
                    + cities.get(i).getCityCode() + "城市编码:"
                    + cities.get(i).getAdCode() + "\n";
        }
        RxToast.info(infomation);
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString().trim();
        if (!RxDataTool.isEmpty(newText)) {
            InputtipsQuery inputquery = new InputtipsQuery(newText, "合肥");
            Inputtips inputTips = new Inputtips(this, inputquery);
            inputTips.setInputtipsListener(this);
            inputTips.requestInputtipsAsyn();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onMapClick(final LatLng latLng) {
        if (marker != null) {
            marker.remove();
        }
        marker = aMap.addMarker(new MarkerOptions().position(latLng));
        doSearchQuery(new LatLonPoint(latLng.latitude, latLng.longitude));
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == 1000) {// 正确返回
            List<String> listString = new ArrayList<String>();
            for (int i = 0; i < tipList.size(); i++) {
                listString.add(tipList.get(i).getName());
            }
            ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
                    getApplicationContext(),
                    R.layout.ietm_route_inputs, listString);
            etSearch.setAdapter(aAdapter);
            aAdapter.notifyDataSetChanged();
        } else {
            RxToast.error("错误代码" + String.valueOf(rCode));
        }
    }

    float distance = 0;

    private void addPolylineInPlayGround() {
        aMap.clear();
        po = new PolylineOptions()
//                .color(getResources().getColor(R.color.red))
                .setCustomTexture(BitmapDescriptorFactory.fromResource(R.drawable.normal_zuji_deep))
                //虚线
                .setDottedLine(true).geodesic(true)
                .addAll(pathPoints)
                .useGradient(true)
                .width(40);
        aMap.addPolyline(po);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(pathPoints.get(pathPoints.size() - 1)));
        marker = aMap.addMarker(new MarkerOptions()
                .position(pathPoints.get(0))
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.normal_qidian)))
                .draggable(true));
        if (pathPoints.size() > 1) {
            distance = 0;
            for (int i = 1; i < pathPoints.size(); i++) {
                distance += (AMapUtils.calculateLineDistance(pathPoints.get(i - 1), pathPoints.get(i))) / 1000;
            }
        }
    }

    public void move() {
        LatLngBounds bounds = new LatLngBounds(pathPoints.get(0), pathPoints.get(pathPoints.size() - 2));
        for (int i = 0; i < pathPoints.size(); i++) {
            bounds.including(pathPoints.get(i));
        }
        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 12));

        LatLng drivePoint = pathPoints.get(0);
        Pair<Integer, LatLng> pair = PointsUtil.calShortestDistancePoint(pathPoints, drivePoint);
        pathPoints.set(pair.first, drivePoint);
        List<LatLng> subList = pathPoints.subList(pair.first, pathPoints.size());

        if (smoothMarker != null) {
            smoothMarker.destroy();
        }
        smoothMarker = new SmoothMarker(aMap);
        smoothMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.drawable.normal_flag_deep));
        smoothMarker.setPoints(subList);
        smoothMarker.setTotalDuration(10000);
        smoothMarker.startSmoothMove();
        smoothMarker.setMoveListener(new SmoothMarker.SmoothMarkerMoveListener() {

            @Override
            public void move(double distance) {
            }

            @Override
            public void stop() {
            }

            @Override
            public void end() {
                aMap.animateCamera(CameraUpdateFactory.changeLatLng(pathPoints.get(pathPoints.size() - 1)));
            }
        });
    }

    @Override
    public void loading(String msg) {

    }

    @Override
    public void loadSuccess(Object object) {
        ViseLog.setTag("88888").i(object.toString());
    }

    @Override
    public void loadFail(Object object) {

    }

    @Override
    public void onMapScreenShot(Bitmap bitmap) {

    }

    private boolean b = false;

    @Override
    public void onMapScreenShot(Bitmap bitmap, int arg1) {
        if (null == bitmap) {
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(
                    Environment.getExternalStorageDirectory() + "/" + UUID + ".jpeg");
            b = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            try {
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringBuffer buffer = new StringBuffer();
            if (b)
                buffer.append("截屏成功 ");
            else {
                buffer.append("截屏失败 ");
            }
            if (arg1 != 0)
                buffer.append("地图渲染完成，截屏无网格");
            else {
                buffer.append("地图未渲染完成，截屏有网格");
            }
            ViseLog.i(buffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Subscribe()
    public void refresh(IEvent event) {
        if (event != null && event instanceof PublishEvent) {
            if (((PublishEvent) event).getMsg().equals("OK")) {
                finish();
            }
        }
    }
}