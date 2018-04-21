package com.zlb.memo.bean;

import java.io.Serializable;
import java.util.List;

/**
 */
public class PublishPoiDraft implements Serializable {
    private String pingyouId = "";
    private List<PublishPoi> poiList;

    public String getPingyouId() {
        return pingyouId;
    }

    public void setPingyouId(String pingyouId) {
        this.pingyouId = pingyouId;
    }

    public List<PublishPoi> getPoiList() {
        return poiList;
    }

    public void setPoiList(List<PublishPoi> poiList) {
        this.poiList = poiList;
    }
}
