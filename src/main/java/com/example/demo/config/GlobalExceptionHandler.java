package com.example.demo.config;


import com.example.demo.constant.MapKeyConsts;
import com.example.demo.exception.BaseException;
import com.example.demo.util.GetNowUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局捕获异常
 * 1.捕获返回json格式
 * 2.捕获返回页面
 * @ClassName GlobalExceptionHandler
 * @Description TODO
 * @Author msi
 * @Date 2019/7/28 21:51
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.example.demo")
public class GlobalExceptionHandler extends MemberVariable {



    /**
     * 捕获全局自定义异常
     * @Description TODO
     * @param e
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public Map<String, Object> customErrorDispose(BaseException e){
        // 将错误记录在日志中。
        Map<String, Object> errorResultMap = new HashMap<>();
        errorResultMap.put(MapKeyConsts.CODE, e.getCode());
        errorResultMap.put(MapKeyConsts.MESSAGE, e.getMessage());
        errorResultMap.put(MapKeyConsts.DETAILED, e.getMessage() + "\n" + GetNowUtil.getDateTime());

        // 打印错误日志
        log.error("错误代码({}),错误信息({})", e.getCode(), e.getMessage());

        return errorResultMap;
    }

    /**
     * 捕获意料之外的异常Exception
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Map<String, Object> otherErrorDispose(Exception e){
        // 将错误记录在日志中。
        Map<String, Object> errorResultMap = new HashMap<>();
        errorResultMap.put(MapKeyConsts.CODE, 500);
        errorResultMap.put(MapKeyConsts.DETAILED, e.getMessage() + "\n" + GetNowUtil.getDateTime());

        // 打印错误日志
        log.error("错误代码({}),错误信息({})", 500, e.getMessage());

        return errorResultMap;
    }
}
