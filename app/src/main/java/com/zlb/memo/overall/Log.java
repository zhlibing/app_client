package com.zlb.memo.overall;

import com.vise.log.ViseLog;
import com.vise.log.inner.LogcatTree;

/**
 * Created by Administrator on 2017/11/21.
 */

public class Log {
    public static void initLog() {
        ViseLog.getLogConfig()
                .configAllowLog(true)//是否输出日志
                .configShowBorders(false);//是否排版显示
        ViseLog.plant(new LogcatTree());//添加打印日志信息到Logcat的树
    }
}
