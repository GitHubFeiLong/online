package com.example.demo.config;

import com.example.demo.util.RedisUtil;
import lombok.Getter;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName MemberVariable
 * @Author msi
 * @Date 2020/6/8 13:10
 * @Version 1.0
 */
@Getter
@Component
public class MemberVariable {
    /**
     * 返回前端的状态码：5个零表示全部成功
     */
    public static final String CODE = "00000";

    /**
     * 请求对象request
     */
    @Autowired
    public HttpServletRequest httpServletRequest;

    /**
     * 响应对象
     */
    @Autowired
    public HttpServletResponse httpServletResponse;

    /**
     * session
     */
    @Autowired
    public HttpSession httpSession;



    /**
     * redis工具类
     */
    @Autowired
    public RedisUtil redisUtil;





}
