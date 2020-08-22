package com.example.demo.aop;

import com.example.demo.annotation.RepeatSubmitAnnotation;
import com.example.demo.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类描述：
 * 使用AOP防止用户重复提交表单
 * @ClassName RepeatSubmitAOP
 * @Description TODO
 * @Author msi
 * @Date 2020/6/10 19:17
 * @Version 1.0
 */
@Slf4j
@Aspect
@Component
public class RepeatSubmitAOP {

	/**
	 * 使用redis来实现防止表单重复提交操作
	 */
	@Autowired
	private RedisUtil redisUtil;


	/**
	 * 定义切点：使用了注解@RepeatSubmitAnnotation的所有方法和类
	 */
	@Pointcut(value = "@annotation(com.example.demo.annotation.RepeatSubmitAnnotation)")
	public void repeatSubmit(){}


	/**
	 * 使用环绕通知可以阻止程序继续执行
	 * @param pjp
	 * @throws Throwable
	 */
	@Around("repeatSubmit()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

		//获取request和response对象
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		HttpServletResponse response = attributes.getResponse();

		// 获取注解的参数
		MethodSignature signature = (MethodSignature)pjp.getSignature();
		RepeatSubmitAnnotation repeatSubmitAnnotation = signature.getMethod().getAnnotation(RepeatSubmitAnnotation.class);
		int time = repeatSubmitAnnotation.value();

		Object ret = null;
		// 创建redis的key ： jd:all:repeat_submit:uri:sessionID
		String redisKey = "jd:all:repeat_submit:" + request.getRequestURI() + ":" + request.getSession().getId();

		// 从redis中获取key对应的值
		Object redisValue = redisUtil.get(redisKey);

		// redis中没有指定key，符合条件则继续执行，否则终止方法的执行
		if (ObjectUtils.isEmpty(redisValue)) {
			// 将key存在redis中，指定时间
			redisUtil.set(redisKey,1, time);

			// 执行方法
			ret =  pjp.proceed();
		} else {
			log.info("防止了 {} 重复提交",request.getRequestURI());
		}
		return ret;

	}
}
