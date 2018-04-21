package com.zlb.memo.bean;

import java.io.Serializable;

/**
 */
public class PublishPoi implements Serializable {

    private String userId = "";
    private String pingyouId = "";
    private String indexId = "";
    private String poiId = "";
    private String lat = "";
    private String lon = "";
    private String distance = "";
    private String provence = "";
    private String city = "";
    private String zone = "";
    private String keyword = "";
    private String address = "";
    private String gdImage = "";
    private String gdCutImage = "";
    private String viewPointContentId = "";
    private String haveViewPoint = "";
    private String isDraft = "";
    private String updateDate = "";
    private String creationDate = "";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPingyouId() {
        return pingyouId;
    }

    public void setPingyouId(String pingyouId) {
        this.pingyouId = pingyouId;
    }

    public String getIndexId() {
        return indexId;
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }

    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getProvence() {
        return provence;
    }

    public void setProvence(String provence) {
        this.provence = provence;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGdImage() {
        return gdImage;
    }

    public void setGdImage(String gdImage) {
        this.gdImage = gdImage;
    }

    public String getGdCutImage() {
        return gdCutImage;
    }

    public void setGdCutImage(String gdCutImage) {
        this.gdCutImage = gdCutImage;
    }

    public String getViewPointContentId() {
        return viewPointContentId;
    }

    public void setViewPointContentId(String viewPointContentId) {
        this.viewPointContentId = viewPointContentId;
    }

    public String getHaveViewPoint() {
        return haveViewPoint;
    }

    public void setHaveViewPoint(String haveViewPoint) {
        this.haveViewPoint = haveViewPoint;
    }

    public String getIsDraft() {
        return isDraft;
    }

    public void setIsDraft(String isDraft) {
        this.isDraft = isDraft;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
