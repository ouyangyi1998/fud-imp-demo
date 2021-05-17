package com.centerm.fud_demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取之前的时间
 * @author ouyangyi
 */
public class GetDateUtil {
    /**
     * 获取设定的之前的时间
     * @param back 回到的天数
     * @return 时间节点
     */
    public static String getDate(int back)
    {
        SimpleDateFormat predf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        return predf.format(new Date(d.getTime() - (long)24 * 60 * 60 * 1000 * back));
    }
}
