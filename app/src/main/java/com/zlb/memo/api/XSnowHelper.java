package com.zlb.memo.api;

import com.vise.log.ViseLog;
import com.vise.netexpand.request.ApiPostRequest;
import com.vise.xsnow.common.GsonUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.http.callback.UCallback;
import com.vise.xsnow.http.core.ApiTransformer;
import com.vise.xsnow.http.mode.CacheMode;
import com.vise.xsnow.http.mode.CacheResult;
import com.vise.xsnow.http.subscriber.ApiCallbackSubscriber;
import com.zlb.memo.bean.BasePage;
import com.zlb.memo.bean.PublishBase;
import com.zlb.memo.bean.PublishPoi;
import com.zlb.memo.bean.PublishPoiDraft;
import com.zlb.memo.bean.User;
import com.zlb.memo.listener.IDataRequestListener;
import com.zlb.memo.mvvm.viewResult.GetAllDaoyouResultView;
import com.zlb.memo.mvvm.viewResult.GetAllPublishedBozhuResultView;
import com.zlb.memo.mvvm.viewResult.GetAllPublishedDarenResultView;
import com.zlb.memo.mvvm.viewResult.GetUserPublishedDraftResultView;
import com.zlb.memo.mvvm.viewResult.HomeLoadResultView;
import com.zlb.memo.mvvm.viewResult.LoginResultView;
import com.zlb.memo.mvvm.viewResult.PublishBozhuResultView;
import com.zlb.memo.mvvm.viewResult.PublishDaoyouResultView;
import com.zlb.memo.mvvm.viewResult.PublishDarenResultView;
import com.zlb.memo.mvvm.viewResult.PublishLvxingzheResultView;
import com.zlb.memo.mvvm.viewResult.PublishPingyouResultView;
import com.zlb.memo.mvvm.viewResult.PublishShanghuResultView;
import com.zlb.memo.mvvm.viewResult.PublishYouhuiquanResultView;
import com.zlb.memo.mvvm.viewResult.RegistResultView;
import com.zlb.memo.overall.C;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/12/14.
 */

public class XSnowHelper {

    private void upload() {
        ViseHttp.UPLOAD("v1/web/cms/skinStrategy/addOrUpdateSkinStrategy", new UCallback() {
            @Override
            public void onProgress(long currentLength, long totalLength, float percent) {
            }

            @Override
            public void onFail(int errCode, String errMsg) {
                ViseLog.i("upload errorCode:" + errCode + ",errorMsg:" + errMsg);
            }
        }).addParam("strategyId", "41")
                .addParam("title", "初秋美白养成计划")
                .addParam("tagIds", "95,96,208")
                .addParam("content", "夏天晒黑了？初秋正是美白的好时机，快快行动起来。")
                .addParam("status", "1")
                .addFile("androidPicFile", new File(""))
                .baseUrl("https://200.200.200.50/")
                .request(new ACallback<Object>() {
                    @Override
                    public void onSuccess(Object data) {
                        ViseLog.i("upload success:" + data);
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.i("upload errorCode:" + errCode + ",errorMsg:" + errMsg);
                    }
                });
    }

    private void request_post_4(Object object) {
        ViseHttp.BASE(new ApiPostRequest("postUrlAuthor")
                .setLocalCache(true)
                .cacheMode(CacheMode.CACHE_AND_REMOTE)
                .tag("request_post_4")
                .addUrlParam("appId", "10001")
                .addUrlParam("appType", "Android")
                //表单形式，同时存在，表单优先，一般不要写同时存在
                .addForm("author_account", "xiaoyaoyou1212")
                .addForm("author_github", "https://github.com/xiaoyaoyou1212")
                .addForm("author_csdn", "http://blog.csdn.net/xiaoyaoyou1212")
                .addForm("author_websit", "http://www.huwei.tech/")
                //json形式
                .setJson(GsonUtil.gson().toJson(object)))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ViseLog.i("request onSuccess!");
                        if (data == null) {
                            return;
                        }
                        ViseLog.i(data);
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                    }
                });
    }

    private void request_retrofit_1() {
        ViseHttp.RETROFIT()
                .create(APIService.class)
                .getAuthor()
                .compose(ApiTransformer.<String>norTransformer())
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<String>() {
                    @Override
                    public void onSuccess(String authorModel) {
                        ViseLog.i("request onSuccess!");
                        if (authorModel == null) {
                            return;
                        }
                        ViseLog.i(authorModel.toString());
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                    }
                }));
    }

    public static void loginRequestServer(Object object, final LoginResultView view) {
        view.Loading("登录中");
        ViseHttp.RETROFIT()
                .create(APIService.class)
                .login((Map<String, String>) object)
                .compose(ApiTransformer.<ResultBase<User>>norTransformer())
                .compose(ViseHttp.getApiCache().<ResultBase<User>>transformer(CacheMode.ONLY_REMOTE, ResultBase.class))
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<CacheResult<ResultBase<User>>>() {
                    @Override
                    public void onSuccess(CacheResult<ResultBase<User>> cacheResult) {
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.getCacheData().code.equals("1")) {
                            if (cacheResult.isCache()) {
                                ViseLog.i("From Cache:\n" + cacheResult.getCacheData().toString());
                                view.LoginSucess(cacheResult.getCacheData().data);
                                return;
                            } else {
                                ViseLog.i("From Remote:\n" + cacheResult.getCacheData().toString());
                                view.LoginSucess(cacheResult.getCacheData().data);
                                return;
                            }
                        } else {
                            view.Fail(cacheResult.getCacheData().message);
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                        view.Fail(errCode + "****" + errMsg);
                    }
                }));
    }

    public static void RegistRequestServer(Object object, final RegistResultView view) {
        view.Loading("注册中");
        ViseHttp.RETROFIT()
                .create(APIService.class)
                .register((Map<String, String>) object)
                .compose(ApiTransformer.<ResultBase<String>>norTransformer())
                .compose(ViseHttp.getApiCache().<ResultBase<String>>transformer(CacheMode.ONLY_REMOTE, ResultBase.class))
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<CacheResult<ResultBase<String>>>() {
                    @Override
                    public void onSuccess(CacheResult<ResultBase<String>> cacheResult) {
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        ViseLog.i("From Cache:\n" + cacheResult.getCacheData().toString());
                        view.RegistSucess(new User(cacheResult.getCacheData().message, "", ""));
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                        view.Fail(errCode + "****" + errMsg);
                    }
                }));
    }

    public static void PublishDarenRequestServer(Object object, final PublishDarenResultView view) {
        view.Loading("发布中");
        ViseHttp.RETROFIT()
                .create(APIService.class)
                .publishDaren((HashMap<String, RequestBody>) object)
                .compose(ApiTransformer.<ResultBase>norTransformer())
                .compose(ViseHttp.getApiCache().<ResultBase>transformer(CacheMode.ONLY_REMOTE, ResultBase.class))
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<CacheResult<ResultBase>>() {
                    @Override
                    public void onSuccess(CacheResult<ResultBase> cacheResult) {
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.getCacheData().code.equals("1")) {
                            view.PublishDarenSucess(cacheResult.getCacheData());
                            return;
                        } else {
                            view.Fail(cacheResult.getCacheData().message);
                            return;
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        view.Fail(errCode + "****" + errMsg);
                    }
                }));
    }

    public static void PublishLvxingzheRequestServer(Object object, final PublishLvxingzheResultView view) {
        view.Loading("发布中");
        ViseHttp.RETROFIT()
                .create(APIService.class)
                .publishLvxingzhe((HashMap<String, RequestBody>) object)
                .compose(ApiTransformer.<ResultBase>norTransformer())
                .compose(ViseHttp.getApiCache().<ResultBase>transformer(CacheMode.ONLY_REMOTE, ResultBase.class))
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<CacheResult<ResultBase>>() {
                    @Override
                    public void onSuccess(CacheResult<ResultBase> cacheResult) {
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.getCacheData().code.equals("1")) {
                            view.PublishLvxingzheSucess(cacheResult.getCacheData());
                            return;
                        } else {
                            view.Fail(cacheResult.getCacheData().message);
                            return;
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        view.Fail(errCode + "****" + errMsg);
                    }
                }));
    }

    public static void PublishShanghuRequestServer(Object object, final PublishShanghuResultView view) {
        view.Loading("发布中");
        ViseHttp.RETROFIT()
                .create(APIService.class)
                .publishShanghu((HashMap<String, RequestBody>) object)
                .compose(ApiTransformer.<ResultBase>norTransformer())
                .compose(ViseHttp.getApiCache().<ResultBase>transformer(CacheMode.ONLY_REMOTE, ResultBase.class))
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<CacheResult<ResultBase>>() {
                    @Override
                    public void onSuccess(CacheResult<ResultBase> cacheResult) {
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.getCacheData().code.equals("1")) {
                            view.PublishShanghuSucess(cacheResult.getCacheData());
                            return;
                        } else {
                            view.Fail(cacheResult.getCacheData().message);
                            return;
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        view.Fail(errCode + "****" + errMsg);
                    }
                }));
    }

    public static void PublishBozhuRequestServer(Object object, final PublishBozhuResultView view) {
        view.Loading("发布中");
        ViseHttp.RETROFIT()
                .create(APIService.class)
                .publishBozhu((HashMap<String, String>) object)
                .compose(ApiTransformer.<ResultBase<PublishBase>>norTransformer())
                .compose(ViseHttp.getApiCache().<ResultBase<PublishBase>>transformer(CacheMode.ONLY_REMOTE, ResultBase.class))
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<CacheResult<ResultBase<PublishBase>>>() {
                    @Override
                    public void onSuccess(CacheResult<ResultBase<PublishBase>> cacheResult) {
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.getCacheData().code.equals("1")) {
                            view.PublishBozhuSucess(cacheResult.getCacheData().data);
                            return;
                        } else {
                            view.Fail(cacheResult.getCacheData().message);
                            return;
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        view.Fail(errCode + "****" + errMsg);
                    }
                }));
    }

    public static void PublishDaoyouRequestServer(Object object, final PublishDaoyouResultView view) {
        view.Loading("发布中");
        ViseHttp.RETROFIT()
                .create(APIService.class)
                .publishDaoyou((HashMap<String, String>) object)
                .compose(ApiTransformer.<ResultBase<PublishBase>>norTransformer())
                .compose(ViseHttp.getApiCache().<ResultBase<PublishBase>>transformer(CacheMode.ONLY_REMOTE, ResultBase.class))
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<CacheResult<ResultBase<PublishBase>>>() {
                    @Override
                    public void onSuccess(CacheResult<ResultBase<PublishBase>> cacheResult) {
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.getCacheData().code.equals("1")) {
                            view.PublishDaoyouSucess(cacheResult.getCacheData().data);
                            return;
                        } else {
                            view.Fail(cacheResult.getCacheData().message);
                            return;
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        view.Fail(errCode + "****" + errMsg);
                    }
                }));
    }

    public static void GetAllDaoyouRequestServer(Object object, final GetAllDaoyouResultView view) {
        view.Loading("努力加载中");
        ViseHttp.RETROFIT()
                .create(APIService.class)
                .getAllDaoyou((HashMap<String, String>) object)
                .compose(ApiTransformer.<ResultBase<BasePage<PublishBase>>>norTransformer())
                .compose(ViseHttp.getApiCache().<ResultBase<BasePage<PublishBase>>>transformer(CacheMode.ONLY_REMOTE, ResultBase.class))
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<CacheResult<ResultBase<BasePage<PublishBase>>>>() {
                    @Override
                    public void onSuccess(CacheResult<ResultBase<BasePage<PublishBase>>> cacheResult) {
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.getCacheData().code.equals("1")) {
                            view.GetAllDaoyouSucess(cacheResult.getCacheData().data);
                            return;
                        } else {
                            view.Fail(cacheResult.getCacheData().message);
                            return;
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        view.Fail(errCode + "****" + errMsg);
                    }
                }));
    }

    public static void DeleteUserPublishedDraftRequestServer(Object object, final GetUserPublishedDraftResultView view) {
        view.Loading("删除中");
        ViseHttp.RETROFIT()
                .create(APIService.class)
                .deleteUserPublishedPoiDraft((HashMap<String, String>) object)
                .compose(ApiTransformer.<ResultBase<BasePage<PublishPoiDraft>>>norTransformer())
                .compose(ViseHttp.getApiCache().<ResultBase<BasePage<PublishPoiDraft>>>transformer(CacheMode.ONLY_REMOTE, ResultBase.class))
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<CacheResult<ResultBase<BasePage<PublishPoiDraft>>>>() {
                    @Override
                    public void onSuccess(CacheResult<ResultBase<BasePage<PublishPoiDraft>>> cacheResult) {
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.getCacheData().code.equals("1")) {
                            view.DeleteUserPublishedDraftSucess(cacheResult.getCacheData());
                            return;
                        } else {
                            view.Fail(cacheResult.getCacheData().message);
                            return;
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        view.Fail(errCode + "****" + errMsg);
                    }
                }));
    }

    public static void GetUserPublishedDraftRequestServer(Object object, final GetUserPublishedDraftResultView view) {
        view.Loading("努力加载中");
        ViseHttp.RETROFIT()
                .create(APIService.class)
                .getUserPublishedDraft((HashMap<String, String>) object)
                .compose(ApiTransformer.<ResultBase<BasePage<PublishPoiDraft>>>norTransformer())
                .compose(ViseHttp.getApiCache().<ResultBase<BasePage<PublishPoiDraft>>>transformer(CacheMode.ONLY_REMOTE, ResultBase.class))
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<CacheResult<ResultBase<BasePage<PublishPoiDraft>>>>() {
                    @Override
                    public void onSuccess(CacheResult<ResultBase<BasePage<PublishPoiDraft>>> cacheResult) {
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.getCacheData().code.equals("1")) {
                            view.GetUserPublishedDraftSucess(cacheResult.getCacheData().data);
                            return;
                        } else {
                            view.Fail(cacheResult.getCacheData().message);
                            return;
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        view.Fail(errCode + "****" + errMsg);
                    }
                }));
    }

    public static void GetAllPublishedBozhuRequestServer(Object object, final GetAllPublishedBozhuResultView view) {
        view.Loading("努力加载中");
        ViseHttp.RETROFIT()
                .create(APIService.class)
                .getAllPublishedBozhu((HashMap<String, String>) object)
                .compose(ApiTransformer.<ResultBase<BasePage<PublishBase>>>norTransformer())
                .compose(ViseHttp.getApiCache().<ResultBase<BasePage<PublishBase>>>transformer(CacheMode.ONLY_REMOTE, ResultBase.class))
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<CacheResult<ResultBase<BasePage<PublishBase>>>>() {
                    @Override
                    public void onSuccess(CacheResult<ResultBase<BasePage<PublishBase>>> cacheResult) {
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.getCacheData().code.equals("1")) {
                            view.GetAllPublishedBozhuSucess(cacheResult.getCacheData().data);
                            return;
                        } else {
                            view.Fail(cacheResult.getCacheData().message);
                            return;
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        view.Fail(errCode + "****" + errMsg);
                    }
                }));
    }

    public static void GetAllPublishedDarenRequestServer(Object object, final GetAllPublishedDarenResultView view) {
        view.Loading("努力加载中");
        ViseHttp.RETROFIT()
                .create(APIService.class)
                .getAllPublishedDaren((HashMap<String, String>) object)
                .compose(ApiTransformer.<ResultBase<BasePage<PublishBase>>>norTransformer())
                .compose(ViseHttp.getApiCache().<ResultBase<BasePage<PublishBase>>>transformer(CacheMode.ONLY_REMOTE, ResultBase.class))
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<CacheResult<ResultBase<BasePage<PublishBase>>>>() {
                    @Override
                    public void onSuccess(CacheResult<ResultBase<BasePage<PublishBase>>> cacheResult) {
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.getCacheData().code.equals("1")) {
                            view.GetAllPublishedDarenSucess(cacheResult.getCacheData().data);
                            return;
                        } else {
                            view.Fail(cacheResult.getCacheData().message);
                            return;
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        view.Fail(errCode + "****" + errMsg);
                    }
                }));
    }

    public static void HomeLoadRequestServer(Object object, final HomeLoadResultView view) {
        view.Loading("加载中");
        ViseHttp.RETROFIT()
                .create(APIService.class)
                .homeLoadRequestServer((Map<String, String>) object)
                .compose(ApiTransformer.<ResultBase<BasePage<PublishBase>>>norTransformer())
                .compose(ViseHttp.getApiCache().<ResultBase<BasePage<PublishBase>>>transformer(CacheMode.ONLY_REMOTE, ResultBase.class))
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<CacheResult<ResultBase<BasePage<PublishBase>>>>() {
                    @Override
                    public void onSuccess(CacheResult<ResultBase<BasePage<PublishBase>>> cacheResult) {
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.getCacheData().code.equals("1")) {
                            view.HomeLoadSucess(cacheResult.getCacheData().data);
                            return;
                        } else {
                            view.Fail(cacheResult.getCacheData().message);
                            return;
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        view.Fail(errCode + "****" + errMsg);
                    }
                }));
    }

    public static void uploadOnlyMuilt(Object object, final IDataRequestListener listener) {
        ViseHttp.RETROFIT()
                .create(APIService.class)
                .uploadOnlyMuilt((HashMap<String, RequestBody>) object)
                .compose(ApiTransformer.<ResultBase<List<String>>>norTransformer())
                .compose(ViseHttp.getApiCache().<ResultBase<List<String>>>transformer(CacheMode.ONLY_REMOTE, ResultBase.class))
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<CacheResult<ResultBase<List<String>>>>() {
                    @Override
                    public void onSuccess(CacheResult<ResultBase<List<String>>> cacheResult) {
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.getCacheData().code.equals("1")) {
                            listener.loadSuccess(cacheResult.getCacheData().data);
                        } else {
                            return;
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.e(errCode + "****" + errMsg);
                    }
                }));
        return;
    }

    public static void publishPoi(Object object, final IDataRequestListener listener) {
        ViseHttp.RETROFIT()
                .create(APIService.class)
                .publishPoi((HashMap<String, RequestBody>) object)
                .compose(ApiTransformer.<ResultBase>norTransformer())
                .compose(ViseHttp.getApiCache().<ResultBase>transformer(CacheMode.ONLY_REMOTE, ResultBase.class))
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<CacheResult<ResultBase>>() {
                    @Override
                    public void onSuccess(CacheResult<ResultBase> cacheResult) {
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.getCacheData().code.equals("1")) {
                            listener.loadSuccess(cacheResult.getCacheData());
                        } else {
                            return;
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.e(errCode + "****" + errMsg);
                    }
                }));
        return;
    }

    public static void PublishPingyouRequestServer(Object object, final PublishPingyouResultView view) {
        view.Loading("发布中");
        ViseHttp.RETROFIT()
                .create(APIService.class)
                .publishPingyou((HashMap<String, RequestBody>) object)
                .compose(ApiTransformer.<ResultBase<PublishBase>>norTransformer())
                .compose(ViseHttp.getApiCache().<ResultBase<PublishBase>>transformer(CacheMode.ONLY_REMOTE, ResultBase.class))
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<CacheResult<ResultBase<PublishBase>>>() {
                    @Override
                    public void onSuccess(CacheResult<ResultBase<PublishBase>> cacheResult) {
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.getCacheData().code.equals("1")) {
                            view.PublishPingyouSucess(cacheResult.getCacheData().data);
                            return;
                        } else {
                            view.Fail(cacheResult.getCacheData().message);
                            return;
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        view.Fail(errCode + "****" + errMsg);
                    }
                }));
        return;
    }

    public static void PublishYouhuiquanRequestServer(Object object, final PublishYouhuiquanResultView view) {
        view.Loading("发布中");
        ViseHttp.RETROFIT()
                .create(APIService.class)
                .publishYouhuiquan((HashMap<String, RequestBody>) object)
                .compose(ApiTransformer.<ResultBase<PublishBase>>norTransformer())
                .compose(ViseHttp.getApiCache().<ResultBase<PublishBase>>transformer(CacheMode.ONLY_REMOTE, ResultBase.class))
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<CacheResult<ResultBase<PublishBase>>>() {
                    @Override
                    public void onSuccess(CacheResult<ResultBase<PublishBase>> cacheResult) {
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.getCacheData().code.equals("1")) {
                            view.PublishYouhuiquanSucess(cacheResult.getCacheData().data);
                            return;
                        } else {
                            view.Fail(cacheResult.getCacheData().message);
                            return;
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        view.Fail(errCode + "****" + errMsg);
                    }
                }));
        return;
    }
}
