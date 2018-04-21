package com.zlb.memo.bean;

import java.io.Serializable;
import java.util.List;

public class PublishBase implements Serializable {
    private String tags;
    private String contentDetails;
    private String contentId;
    private List<PublishImage> images;
    private List<PublishBozhu> bozhuList;
    private List<PublishPoi> poiList;
    private String creationDate;
    private String userId;
    private String type;
    private User user;
    private String imagesSize;
    private String firstImageUrl;
    private String title;
    private String name;
    private String personNumber;
    private String pingyouId;
    private String isFree;
    private String price;
    private String coverImage;
    private String contactNumber;
    private String contactWay;
    private String attachIntroduce;
    private String time;
    private String haveOutsideLink;
    private String outsideLinkUrl;
    private String consumption;
    private String discount;
    private String isMyUserHaveDiscount;
    private String location;
    private String youhuiquanId;
    private String careName;
    private String cardType;
    private String useLocation;
    private String useTime;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

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

    public List<PublishImage> getImages() {
        return images;
    }

    public void setImages(List<PublishImage> images) {
        this.images = images;
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

    public List<PublishBozhu> getBozhuList() {
        return bozhuList;
    }

    public void setBozhuList(List<PublishBozhu> bozhuList) {
        this.bozhuList = bozhuList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }

    public String getPingyouId() {
        return pingyouId;
    }

    public void setPingyouId(String pingyouId) {
        this.pingyouId = pingyouId;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getAttachIntroduce() {
        return attachIntroduce;
    }

    public void setAttachIntroduce(String attachIntroduce) {
        this.attachIntroduce = attachIntroduce;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<PublishPoi> getPoiList() {
        return poiList;
    }

    public void setPoiList(List<PublishPoi> poiList) {
        this.poiList = poiList;
    }

    public String getHaveOutsideLink() {
        return haveOutsideLink;
    }

    public void setHaveOutsideLink(String haveOutsideLink) {
        this.haveOutsideLink = haveOutsideLink;
    }

    public String getOutsideLinkUrl() {
        return outsideLinkUrl;
    }

    public void setOutsideLinkUrl(String outsideLinkUrl) {
        this.outsideLinkUrl = outsideLinkUrl;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getIsMyUserHaveDiscount() {
        return isMyUserHaveDiscount;
    }

    public void setIsMyUserHaveDiscount(String isMyUserHaveDiscount) {
        this.isMyUserHaveDiscount = isMyUserHaveDiscount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getYouhuiquanId() {
        return youhuiquanId;
    }

    public void setYouhuiquanId(String youhuiquanId) {
        this.youhuiquanId = youhuiquanId;
    }

    public String getCareName() {
        return careName;
    }

    public void setCareName(String careName) {
        this.careName = careName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getUseLocation() {
        return useLocation;
    }

    public void setUseLocation(String useLocation) {
        this.useLocation = useLocation;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }
}
