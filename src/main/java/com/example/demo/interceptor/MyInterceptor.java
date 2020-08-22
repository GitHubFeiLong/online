package com.example.demo.interceptor;


import com.example.demo.config.MemberVariable;
import com.example.demo.dao.OnLineDao;
import com.example.demo.entry.dto.DWZDTO;
import com.example.demo.service.impl.OnLineServiceImpl;
import com.example.demo.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 类描述：
 * 拦截请求
 * @ClassName MyInterceptor
 * @Description TODO
 * @Author msi
 * @Date 2020/6/10 19:34
 * @Version 1.0
 */
@Slf4j
public class MyInterceptor implements HandlerInterceptor {

    /**
     * 拦截方法之前执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("执行preHandle方法：{}", request.getRequestURI());

        // 短网址步骤：1 从redis中获取所有数据
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        MemberVariable memberVariable = (MemberVariable) factory.getBean("memberVariable");
        OnLineDao onLineDao = (OnLineDao) factory.getBean("onLineDao");
        List<DWZDTO> list = (List<DWZDTO>)memberVariable.redisUtil.get(OnLineServiceImpl.redisKey);
        if (list == null) {
            list = onLineDao.listDWZ();
            memberVariable.redisUtil.set(OnLineServiceImpl.redisKey, list, 300);
        }

        // 短网址步骤：2 获取指定短网址的 对象，判断时间时候满足
        DWZDTO dwzdto = list.stream().filter(p -> request.getRequestURL().toString().equals(p.getDwz())).findFirst().orElse(null);

        if (!Objects.isNull(dwzdto)) {

            // 判断时间是否未超时
            Long minute = dwzdto.getMinute();
            LocalDateTime localDateTime = DateUtil.convertToLocalDateTime(dwzdto.getCreateTime());
            localDateTime = localDateTime.plusMinutes(minute);

            LocalDateTime now = LocalDateTime.now();

            if (localDateTime.isAfter(now)) {
                response.sendRedirect(dwzdto.getCwz());
                return false;
            } else {
//                response.setCharacterEncoding("UTF-8");
//                response.setContentType("text/html;charset=UTF-8");
//                PrintWriter writer = response.getWriter();
//                writer.println("超时了");
//                writer.close();
                return true;
            }

        }

        // 如果不是映射到方法直接通过
       /* if (!(handler instanceof HandlerMethod)) {
            return true;
        }*/

        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
//        log.info("执行postHandle方法-->02");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
//        log.info("执行afterCompletion方法-->03");
    }
}

