package com.zlb.memo.mvvm.viewRequest;

import java.util.Map;

/**
 */
public interface GetUserPublishedDraftRequestView extends BaseRequestView {
    void GetUserPublishedDraft(Map<String, String> map);
    void DeleteUserPublishedDraft(Map<String, String> map);
}
