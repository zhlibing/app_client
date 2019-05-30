package com.zlb.memo.overall;

import android.os.Environment;

import com.amap.api.maps.model.LatLng;
import com.zlb.memo.bean.User;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class C {
    public static String WEBURL = "";
    public static String BaseUrl = "http://10.5.140.122:8080";
    public static String BaseImgUrl = "http://10.5.140.122:8080/upload";
    // 默认存放图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH = Environment.getExternalStorageDirectory() + File.separator + "CircleDemo" + File.separator + "Images"
            + File.separator;
    public static List<String> GuideTags = Arrays.asList(
            "活跃度"
            , "订单值"
            , "信用"
            , "满意度"
            , "响应度"
            , "专业值"
            , "带客率"
    );
    public static int home_state = 0;
    public static String uploadUrl = "";
    public static String SELECT_POI_ITEMS = "SELECT_POI_ITEMS";
    public static StringBuffer content = new StringBuffer();
    public static LatLng latLng = new LatLng(31.82057, 117.227308);
    public static int screenWidth = 0;
    public static int screenHeight = 0;
    public static User user;
}
