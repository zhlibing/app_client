package com.zlb.memo;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

//import com.yiw.qupai.QPManager;

import com.vise.xsnow.loader.LoaderManager;
import com.vondear.rxtools.RxTool;
import com.zlb.memo.db.DbHelper;
import com.zlb.memo.loader.FrescoLoader;
import com.zlb.memo.overall.Log;
import com.zlb.memo.overall.Net;

public class App extends MultiDexApplication {
    static App app;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        mContext = getApplicationContext();
        RxTool.init(this);
        Log.initLog();
        new Net(this).initNet();
        DbHelper.getInstance().init(this);
        LoaderManager.setLoader(new FrescoLoader());//外部定制图片加载库Fresco
        LoaderManager.getLoader().init(this);
        MultiDex.install(this);

        //LeakCanary.install(this);
//        QPManager.getInstance(getApplicationContext()).initRecord();
    }

    public static App getInstance() {
        return app;
    }

    public static Context getContext() {
        return mContext;
    }

}
