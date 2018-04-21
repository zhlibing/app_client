package com.zlb.memo.eventmodel;

import com.vise.xsnow.event.IEvent;

/**
 * @Description: 自定义事件，只需实现IEvent接口就行，内容自行定义
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2017-01-20 10:11
 */
public class PublishEvent implements IEvent {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PublishEvent(String msg) {
        this.msg = msg;
    }
}
