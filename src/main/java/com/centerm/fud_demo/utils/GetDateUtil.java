package com.centerm.fud_demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetDateUtil {
    public static String getDate(int back)
    {
        SimpleDateFormat predf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        String predate = predf.format(new Date(d.getTime() - (long)24 * 60 * 60 * 1000 * back));
        return predate;
    }
}
