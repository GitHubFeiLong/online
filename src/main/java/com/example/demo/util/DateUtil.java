package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 类描述：
 * 使用Date 和 Calendar 时间相关的API封装
 * @ClassName DateUtil
 * @Description TODO
 * @Author msi
 * @Date 2020/6/15 21:05
 * @Version 1.0
 */
@Slf4j
@Component
public class DateUtil {




    /**
     * 将字符串参数 dateStr 转换成 LocalDateTime对象
     * @param dateStr
     * @return
     */
    public static LocalDateTime convertToLocalDateTime(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
        return localDateTime;
    }

    /**
     * 将字符串参数 dateStr 转换成 LocalDate对象
     * @param dateStr
     * @return
     */
    public static LocalDate convertToLocalDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateStr, formatter);
        return localDate;
    }

}
