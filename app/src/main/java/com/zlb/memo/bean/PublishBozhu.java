package com.zlb.memo.bean;

import java.io.Serializable;

public class PublishBozhu implements Serializable {
    private String contentDetails;
    private String contentId;
    private String creationDate;
    private String userId;
    private String type;
    private String imagesSize;
    private String firstImageUrl;
    private String title;

    public String getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(String contentDetails) {
        this.contentDetails = contentDetails;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImagesSize() {
        return imagesSize;
    }

    public void setImagesSize(String imagesSize) {
        this.imagesSize = imagesSize;
    }

    public String getFirstImageUrl() {
        return firstImageUrl;
    }

    public void setFirstImageUrl(String firstImageUrl) {
        this.firstImageUrl = firstImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
