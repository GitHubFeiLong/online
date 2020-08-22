package com.example.demo.config;

import com.example.demo.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 类描述：
 * SpringMVC的拦截器
 * https://blog.csdn.net/zhangpower1993/article/details/89016503
 * @ClassName WebMvcConfig
 * @Description TODO  WebMvcConfigurationSupport类不能放行静态资源，而WebMvcConfigurer可以配置不拦截静态资源
 * @Author msi
 * @Date 2020/6/10 19:34
 * @Version 1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final String[] notLoginInterceptPaths = {
            "/bootstrap-3.3.7-dist/**"
            , "/css/**", "/js/**"
            , "/image/**"
            , "/config/**"
            , "/system/**"
    };

    /**
     * 拦截器配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(notLoginInterceptPaths);
    }

    /**
     * 配置静态资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

}

