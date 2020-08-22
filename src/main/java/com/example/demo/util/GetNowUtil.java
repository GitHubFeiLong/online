package com.example.demo.util;

import java.time.LocalDateTime;

/**
 * 类描述：
 * 根据java8的LocaDateTime 获取当前时间以“yyyy-MM-dd HH:mm:ss”格式
 * @ClassName GetNowUtil
 * @Description TODO
 * @Author msi
 * @Date 2020/6/11 19:32
 * @Version 1.0
 */
public class GetNowUtil {

    /**
     * 获取当前时间返回格式
     * 2020-05-23 23:40:44.120
     * @return
     */
    public static String getDateTime(){
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.toLocalDate() + " " + localDateTime.toLocalTime();
    }
}
