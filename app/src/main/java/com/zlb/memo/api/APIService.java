package com.zlb.memo.api;

import com.zlb.memo.bean.BasePage;
import com.zlb.memo.bean.PublishBase;
import com.zlb.memo.bean.PublishPoi;
import com.zlb.memo.bean.PublishPoiDraft;
import com.zlb.memo.bean.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @Description: 自定义Retrofit接口服务
 */
public interface APIService {
    @GET("getAuthor")
    Observable<String> getAuthor();

    @POST("postJsonAuthor")
    Observable<ResultBase<String>> postJsonAuthor(@Body RequestBody body);

    @POST("app/waichu/add")
    Observable<ResultBase> addTripApply(@Body User params);

    @POST
    Observable<ResultBase<String>> addYongyao(@Url String url, @Body RequestBody body);

    @POST("/api/account/register")
    Observable<ResultBase<String>> register(@Body RequestBody body);

    @Multipart
    @POST("addAnalysis")
    Observable<ResultBase> addAnalysis(@PartMap HashMap<String, RequestBody> bodyMap);

    /**
     * ****************************************分界线*****************************************************************************
     */
    @POST("/api/account/login")
    Observable<ResultBase<User>> login(@QueryMap Map<String, String> map);

    @POST("/api/account/register")
    Observable<ResultBase<String>> register(@QueryMap Map<String, String> map);

    @POST("/api/homeLoad/homeLoad")
    Observable<ResultBase<BasePage<PublishBase>>> homeLoadRequestServer(@QueryMap Map<String, String> map);

    @Multipart
    @POST("/api/publishDaren/upload")
    Observable<ResultBase> publishDaren(@PartMap HashMap<String, RequestBody> bodyMap);

    @Multipart
    @POST("/api/publishLvxingzhe/upload")
    Observable<ResultBase> publishLvxingzhe(@PartMap HashMap<String, RequestBody> bodyMap);

    @Multipart
    @POST("/api/publishShanghu/upload")
    Observable<ResultBase> publishShanghu(@PartMap HashMap<String, RequestBody> bodyMap);

    @POST("/api/publishDaren/getAllPublishedDaren")
    Observable<ResultBase<BasePage<PublishBase>>> getAllPublishedDaren(@QueryMap Map<String, String> map);

    @POST("/api/publishBozhu/upload")
    Observable<ResultBase<PublishBase>> publishBozhu(@QueryMap Map<String, String> map);

    @POST("/api/publishBozhu/getAllPublishedBozhu")
    Observable<ResultBase<BasePage<PublishBase>>> getAllPublishedBozhu(@QueryMap Map<String, String> map);

    @POST("/api/publishDaoyou/upload")
    Observable<ResultBase<PublishBase>> publishDaoyou(@QueryMap Map<String, String> map);

    @POST("/api/publishDaoyou/getAllDaoyou")
    Observable<ResultBase<BasePage<PublishBase>>> getAllDaoyou(@QueryMap Map<String, String> map);

    @POST("/api/publishPoi/getUserPublishedPoiDraft")
    Observable<ResultBase<BasePage<PublishPoiDraft>>> getUserPublishedDraft(@QueryMap Map<String, String> map);

    @POST("/api/publishPoi/deleteUserPublishedPoiDraft")
    Observable<ResultBase<BasePage<PublishPoiDraft>>> deleteUserPublishedPoiDraft(@QueryMap Map<String, String> map);

    @Multipart
    @POST("/api/publishPoi/save")
    Observable<ResultBase> publishPoi(@PartMap HashMap<String, RequestBody> bodyMap);

    @Multipart
    @POST("/api/publishPingyou/save")
    Observable<ResultBase<PublishBase>> publishPingyou(@PartMap HashMap<String, RequestBody> bodyMap);

    @Multipart
    @POST("/api/publishYouhuiquan/save")
    Observable<ResultBase<PublishBase>> publishYouhuiquan(@PartMap HashMap<String, RequestBody> bodyMap);

    @Multipart
    @POST("/api/uploadOnlyMuilt/upload")
    Observable<ResultBase<List<String>>> uploadOnlyMuilt(@PartMap HashMap<String, RequestBody> bodyMap);

}
